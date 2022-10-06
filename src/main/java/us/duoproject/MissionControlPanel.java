package us.duoproject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MissionControlPanel extends JPanel {

    private static final int TRACK_SIZE = 30;
    private static final int BEACON_SIZE = 20;

    private final List<Marker> BEACONS = new ArrayList<>();
    private final List<Marker> TRACKS = new ArrayList<>();

    private final int sizeX;
    private final int sizeY;
    private final int beaconCount;

    public MissionControlPanel(int sizeX, int sizeY, int beaconCount) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.beaconCount = beaconCount;

        for (int i = 1; i <= beaconCount; i++) {
            BEACONS.add(new Marker(i, deltaX(i, BEACON_SIZE), deltaY(3), BEACON_SIZE));
            TRACKS.add(new Marker(i, deltaX(i, TRACK_SIZE), deltaY(4), TRACK_SIZE));
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(new Color(200, 200, 200));
        graphics.fillRect(0, deltaY(3), sizeX, BEACON_SIZE);
        for (int i = 0; i < beaconCount; i++) {
            BEACONS.get(i).paint(graphics);
        }

        graphics.setColor(new Color(150, 150, 150));
        graphics.fillRect(0, deltaY(4) + TRACK_SIZE / 4, sizeX, TRACK_SIZE / 2);
        for (int i = 0; i < beaconCount; i++) {
            TRACKS.get(i).paint(graphics);
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

    public void triggerBeacon(int id, boolean triggered) {
        BEACONS.stream().filter(beacon -> beacon.getId() == id).findFirst().ifPresent(beacon -> beacon.setTriggered(triggered));
    }

    public void triggerTrack(int id, boolean triggered) {
        TRACKS.stream().filter(beacon -> beacon.getId() == id).findFirst().ifPresent(beacon -> beacon.setTriggered(triggered));
    }

    public void clearBoard() {
        for (int i = 1; i <= beaconCount; i++) {
            triggerBeacon(i, false);
            triggerTrack(i, false);
        }
    }
}
