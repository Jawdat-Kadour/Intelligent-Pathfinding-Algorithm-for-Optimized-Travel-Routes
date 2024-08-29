package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;

public class Map {

    // A list of stations in the Map
    private final List<Station> stations;

    // A list of edges in the Map
    private static List<Road> roads;

    // Constructor
    public Map() {
        this.stations = new ArrayList<>();
        this.roads = new ArrayList<>();
    }

    // Add a station to the Map
    public void addStation(Station station) {
        stations.add(station);
    }

    // Add an edge to the Map
    public void addRoad(Station source, Station destination, float distance, boolean passingTransport,
            String busLineName, float busSpeed, float taxiSpeed) {
        Road road = new Road(source, destination, distance, passingTransport, busLineName, busSpeed, taxiSpeed);
        roads.add(road);
    }

    // Get the list of stations in the Map
    public List<Station> getStations() {
        return stations;
    }

    // Get the list of edges in the Map
    public List<Road> getroads() {
        return roads;
    }

    // Print the Map
    public void printMap() {
        for (Road road : roads) {
            System.out.println(road.getSource().getName() + " -> " + road.getDestination().getName() + " ("
                    + road.getDistance() + ")");
        }
    }

    public static ArrayList<Station> getNeighbors(Station current_station) {
        ArrayList<Station> neighbors = new ArrayList<>();
        for (Road road : roads) {
            if (road.getSource().getName().equals(current_station.getName())) {
                neighbors.add(road.getDestination());
            }
            // else if (road.getDestination().getName().equals(current_station.getName())) {
            // neighbors.add(road.getSource());
            // }
        }
        return neighbors;
    }

    public Road getRoad(Station source, Station neighbor) {
        for (Road road : getroads()) {
            if (source.equals(road.getSource()) && neighbor.equals(road.getDestination())) {
                return road;
            }
        }
        return null;
    }

}

// Class representing a station in the Map

// Class representing an edge in the Map

class MapVisualization extends JComponent {
    private final int width;
    private final int height;
    private final List<Station> stations;
    private final List<Road> roads;

    public MapVisualization(int width, int height, List<Station> stations, List<Road> roads) {
        this.width = width;
        this.height = height;
        this.stations = stations;
        this.roads = roads;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));

        int x = 50;
        int y = 50;
        int spacing = 50;

        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            g2d.fillOval(x - 5, y - 5, 10, 10);
            x += spacing;
        }

        for (Road road : roads) {
            Station source = road.getSource();
            Station destination = road.getDestination();
            int sourceIndex = stations.indexOf(source);
            int destinationIndex = stations.indexOf(destination);
            g2d.drawLine(50 + sourceIndex * spacing, 50, 50 + destinationIndex * spacing, 50);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void printMap3() {
        Set<Road> printedRoads = new HashSet<>();
        for (int i = 0; i < roads.size(); i++) {
            Road currentRoad = roads.get(i);
            if (!printedRoads.contains(currentRoad)) {
                System.out.print(
                        currentRoad.getSource().getName() + " ---> " + currentRoad.getDestination().getName() + " ("
                                + currentRoad.getDistance() + ") ");
                printedRoads.add(currentRoad);
            }
            if (i < roads.size() - 1) {
                Road nextRoad = roads.get(i + 1);
                if (!currentRoad.getDestination().equals(nextRoad.getSource())) {
                    System.out.println();
                }
            }
        }
    }

}