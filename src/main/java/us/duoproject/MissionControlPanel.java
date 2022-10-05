package us.duoproject;

import javax.swing.*;
import java.awt.*;

public class MissionControlPanel extends JPanel {

    private static final int TRACK_SIZE = 30;
    private static final int BEACON_SIZE = 20;

    private final int sizeX;
    private final int sizeY;
    private final int beaconCount;

    public MissionControlPanel(int sizeX, int sizeY, int beaconCount) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.beaconCount = beaconCount;
        getWidth();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(new Color(200, 200, 200));
        graphics.fillRect(0, deltaY(3), sizeX, BEACON_SIZE);
        graphics.setColor(new Color(150, 200, 60));
        for (int i = 1; i <= beaconCount; i++) {
            graphics.fillRoundRect(deltaX(i, BEACON_SIZE), deltaY(3), BEACON_SIZE, BEACON_SIZE, BEACON_SIZE, BEACON_SIZE);
        }

        graphics.setColor(new Color(150, 150, 150));
        graphics.fillRect(0, deltaY(4) + TRACK_SIZE / 4, sizeX, TRACK_SIZE / 2);
        graphics.setColor(new Color(40, 160, 255));
        for (int i = 1; i <= beaconCount; i++) {
            graphics.fillRoundRect(deltaX(i, TRACK_SIZE), deltaY(4), TRACK_SIZE, TRACK_SIZE, TRACK_SIZE, TRACK_SIZE);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(sizeX, sizeY);
    }

    private int deltaY(int input) {
        return sizeY / 8 * input;
    }

    private int deltaX(int input, int size) {
        return (sizeX / (beaconCount + 1) * input) - size / 2;
    }
}
