package us.duoproject;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final String COM_PORT = "COM4";
    private static final int SENSOR_COUNT = 4;

    private static final int SIZE_X = 1200;
    private static final int SIZE_Y = 600;

    private static final AtomicBoolean DO_STOP = new AtomicBoolean(false);
    private static final AtomicBoolean DO_CALIBRATE = new AtomicBoolean(false);
    private static final AtomicInteger TOTAL_LENGTH = new AtomicInteger(0);

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {
        SerialPort port = SerialPort.getCommPort(COM_PORT);
        while (!port.openPort()) { } // continuously attempt to open port

        try {
            String name = "btspp://202103110461:1;authenticate=true";
            StreamConnection streamConnection = (StreamConnection) Connector.open(name);
            OutputStream outputStream = streamConnection.openOutputStream();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (DO_STOP.get()) {
                            outputStream.write(new byte[] { 'h', 'e', 'l', 'l', 'o' });
                            DO_STOP.set(false);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }, 1000);

            MissionControlPanel panel = new MissionControlPanel(SIZE_X, SIZE_Y, SENSOR_COUNT);
            JButton button = new JButton(new AbstractAction("Calibrate") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    DO_CALIBRATE.set(true);
                }
            });
            button.setLocation(10, 10);
            panel.add(button);

            SerialPortJsonReader reader = new SerialPortJsonReader.Builder(SerialPort.LISTENING_EVENT_DATA_RECEIVED)
                    .listener(data -> onJson(panel, data))
                    .delimiters("<", ">")
                    .build();
            port.addDataListener(reader);

            display(panel);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void onJson(MissionControlPanel panel, JsonObject object) {
        DO_STOP.set(false);
        int trainToSensor;

        int distance = object.get("distance").getAsInt();
        if (DO_CALIBRATE.get()) {
            TOTAL_LENGTH.set(distance);
            DO_CALIBRATE.set(false);
        }

        float progress = (float) MathUtil.clampedMap(distance, TOTAL_LENGTH.get(), 0, 0, 1.0F);
        trainToSensor = (int) MathUtil.clampedMap(progress, 0, 1.0F, 0, SENSOR_COUNT + 1);
        panel.setProgress(progress);

        panel.clearBoard();
        panel.revalidate();
        panel.repaint();

        JsonArray sensors = object.get("sensors").getAsJsonArray();
        for (int i = 0; i < sensors.getAsJsonArray().size(); i++) {
            int sensor = sensors.getAsJsonArray().get(i).getAsInt();
            panel.triggerBeacon(sensor, true);

            if (trainToSensor == sensor - 1) {
                DO_STOP.set(true);
            }
        }

        System.out.println(DO_STOP.get());
    }

    public static void display(MissionControlPanel panel) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(SIZE_X, SIZE_Y);
        frame.setVisible(true);
    }
}
