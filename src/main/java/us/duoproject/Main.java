package us.duoproject;

import javax.swing.*;

public class Main {

    private static final int SIZE_X = 1200;
    private static final int SIZE_Y = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new MissionControlPanel(SIZE_X, SIZE_Y, 3));
        frame.setSize(SIZE_X, SIZE_Y);
        frame.setVisible(true);
    }
}
