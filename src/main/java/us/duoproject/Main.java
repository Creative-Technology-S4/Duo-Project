package us.duoproject;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.microedition.io.StreamConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final String COM_PORT = "/dev/cu.usbmodem2101";
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

//        try {
//            String name = "2";
//            StreamConnection streamConnection = (StreamConnection) Connector.open(name);

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
                    .listener(data -> onJson(null, panel, data))
                    .delimiters("<", ">")
                    .build();
            port.addDataListener(reader);

            display(panel);
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public static void onJson(StreamConnection streamConnection, MissionControlPanel panel, JsonObject object) {
        DO_STOP.set(false);
        int trainToSensor;

        int distance = object.get("distance").getAsInt();
        if (DO_CALIBRATE.get()) {
            TOTAL_LENGTH.set(distance);
            DO_CALIBRATE.set(false);
        }

        float progress = (float) MathUtil.clampedMap(distance, TOTAL_LENGTH.get(), 0, 0, 1.0F);
        panel.setProgress(progress);
        trainToSensor = (int) MathUtil.clampedMap(progress, 0, 1.0F, 0, SENSOR_COUNT + 1);

        JsonArray sensors = object.get("sensors").getAsJsonArray();
        panel.clearBoard(); // reset all markers
        panel.revalidate();
        panel.repaint();

        for (int i = 0; i < sensors.getAsJsonArray().size(); i++) {
            int sensor = sensors.getAsJsonArray().get(i).getAsInt();
            panel.triggerBeacon(sensor, true);

            if (trainToSensor == sensor - 1) {
                DO_STOP.set(true);
            }
        }

//        System.out.println(DO_STOP.get()); // TODO - logic
    }

    public static void display(MissionControlPanel panel) {
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(SIZE_X, SIZE_Y);
        frame.setVisible(true);
    }
}
