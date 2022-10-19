package us.duoproject;

import com.fazecast.jSerialComm.SerialPort;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.swing.*;

public class Main {

    private static final String COM_PORT = "dev/cu.usbmodem1101";
    private static final int BEACON_COUNT = 6;

    private static final int SIZE_X = 1200;
    private static final int SIZE_Y = 600;

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) {
        SerialPort port = SerialPort.getCommPort(COM_PORT);
        while (!port.openPort()) { } // continuously attempt to open port

        MissionControlPanel panel = new MissionControlPanel(SIZE_X, SIZE_Y, BEACON_COUNT);
        SerialPortJsonReader reader = new SerialPortJsonReader.Builder(SerialPort.LISTENING_EVENT_DATA_RECEIVED)
                .listener(data -> onJson(panel, data))
                .delimiters("<", ">")
                .build();
        port.addDataListener(reader);

        display(panel);
    }

    public static void onJson(MissionControlPanel panel, JsonObject object) {
        panel.clearBoard(); // reset all markers
        panel.revalidate();
        panel.repaint();

        for (JsonElement sensor : object.get("sensors").getAsJsonArray()) {
            panel.triggerBeacon(sensor.getAsInt(), true);
        }
    }

    public static void display(MissionControlPanel panel) {
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(SIZE_X, SIZE_Y);
        frame.setVisible(true);
    }
}
