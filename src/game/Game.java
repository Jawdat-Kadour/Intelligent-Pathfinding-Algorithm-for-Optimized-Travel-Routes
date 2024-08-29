package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Station Hamak = new Station("hamak", (float) 0.1, (float) 0.1);
        Station Somaryah = new Station("somaryah", (float) 0.5, (float) 0.5);
        Station Bridge = new Station("bridge", (float) 0.8, (float) 0.5);
        Station AlZabadani = new Station("zabadani", (float) 0.5, (float) 0.5);
        Station Qara = new Station("qara", (float) 0.7, (float) 0.1);
        Station Tal = new Station("tal", (float) 0.4, (float) 0.3);
        Station Nabek = new Station("nabek", (float) 0.3, (float) 0.3);
        Station Karag = new Station("karag", (float) 0.5, (float) 0.4);
        Station Deir = new Station("deir", (float) 0.7, (float) 0.0);
        Station qastal = new Station("qastal", (float) 0.3, (float) 0.2);
        Station tadmur = new Station("tadmur", (float) 0.3, (float) 0.4);
        Station russia = new Station("russia", (float) 345, (float) 34);

        Map map2 = new Map();

        map2.addStation(Hamak);
        map2.addStation(Somaryah);
        map2.addStation(Bridge);
        map2.addStation(AlZabadani);
        map2.addStation(Qara);
        map2.addStation(Tal);
        map2.addStation(Nabek);
        map2.addStation(Karag);
        map2.addStation(Deir);
        map2.addStation(qastal);
        map2.addStation(tadmur);
        map2.addStation(russia);

        map2.addRoad(Hamak, Somaryah, 2, true, "h", 60, 80);
        map2.addRoad(Somaryah, AlZabadani, 3, true, "h", 60, 80);
        map2.addRoad(Hamak, Bridge, 3, true, "h", 60, 80);

        map2.addRoad(Bridge, Somaryah, 2, true, "h", 60, 80);

        map2.addRoad(Bridge, AlZabadani, 2, true, "h", 60, 80);

        map2.addRoad(Tal, Karag, 1, true, "h", 60, 80);
        map2.addRoad(Qara, Karag, 6, true, "h", 60, 80);
        map2.addRoad(Qara, Tal, 5, true, "h", 60, 80);

        map2.addRoad(Nabek, Karag, 10, true, "h", 60, 80);
        map2.addRoad(Nabek, Tal, 9, true, "h", 60, 80);
        map2.addRoad(Karag, Hamak, 2, true, "h", 60, 80);

        map2.addRoad(Deir, Karag, 1, true, "h", 60, 80);

        map2.addRoad(Qara, Nabek, 2, true, "h", 60, 80);

        map2.addRoad(Qara, qastal, 3, true, "h", 60, 80);
        map2.addRoad(qastal, Karag, 3, true, "h", 60, 80);
        map2.addRoad(qastal, Somaryah, 5, true, "h", 60, 80);
        map2.addRoad(Qara, Deir, 5, true, "h", 60, 80);

        map2.addRoad(Deir, qastal, 5, false, "h", 60, 80);

        // map2.addRoad(Hamak,AlZabadani , 2,true,"h",60,80);

        // map2.addRoad(Hamak, Somaryah, 1, true, "h", 60, 80);
        // map2.addRoad(Somaryah, AlZabadani, 3, true, "h", 60, 80);
        // map2.addRoad(Hamak, Bridge, 2, true, "h", 60, 80);
        // map2.addRoad(Bridge, Somaryah, 2, true, "h", 60, 80);
        // map2.addRoad(Somaryah, AlZabadani, 4, true, "h", 60, 80);
        // map2.addRoad(Bridge, AlZabadani, 8, true, "h", 30, 80);

        // Map map = new Map();

        // map.addStation(A);
        // map.addStation(B);
        // map.addStation(C);
        // map.addStation(D);
        // map.addStation(E);
        // map.addStation(F);
        // map.addStation(G);
        // map.addStation(H);

        // map.addRoad(A, B, 1, true, "h", 60, 80);
        // map.addRoad(B, C, 2, true, "h", 60, 80);
        // map.addRoad(A, C, 2, true, "h", 60, 80);
        // map.addRoad(C, D, 3, true, "h", 60, 80);
        // map.addRoad(D, E, 4, true, "h", 60, 80);
        // map.addRoad(E, F, 5, true, "h", 60, 80);
        // map.addRoad(F, G, 6, true, "h", 60, 80);
        // map.addRoad(G, H, 7, true, "h", 60, 80);
        // map.addRoad(A, D, 4, true, "h", 60, 80);
        // map.addRoad(B, E, 5, true, "h", 60, 80);
        // map.addRoad(C, F, 6, true, "h", 60, 80);
        // map.addRoad(D, G, 7, true, "h", 60, 80);
        // map.addRoad(E, H, 8, true, "h", 60, 80);

        List<Station> stations = new ArrayList<>();
        List<Road> roads = new ArrayList<>();

        roads = map2.getroads();
        stations = map2.getStations();

        AStar aStar = new AStar();
        Scanner sc = new Scanner(System.in);

        int option = 0;
        if (option != 3) {
            System.out.println("Welcome to the game! Here are your options:");
            System.out.println("1. Print map");
            System.out.println("2. Travel to another station");
            System.out.println("3. Quit game");

            System.out.print("Enter option number: ");
            option = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    map2.printMap();
                    break;
                case 2:
                    System.out.print("Enter your starting point: ");
                    String input = sc.nextLine();
                    Station src = null;
                    for (Station station : stations) {
                        if (station.getName().equalsIgnoreCase(input)) {
                            System.out.println("Matching station found: " + station.getName());
                            src = station;
                        }
                    }
                    if (src == null) {
                        System.out.println("No matching station found.");
                        break;
                    }

                    System.out.print("Enter your destination: ");
                    String destination = sc.nextLine();
                    Station dst = null;
                    for (Station station2 : stations) {
                        if (station2.getName().equalsIgnoreCase(destination)) {
                            System.out.println("Matching station found: " + station2.getName());
                            dst = station2;
                        }
                    }
                    if (dst == null) {
                        System.out.println("No matching station found.");
                        break;
                    }

                    System.out.print("How much money you have: ");
                    int mon = sc.nextInt();
                    Traveler joe = new Traveler(src, dst, 100, mon);

                    System.out.println("How would you like to get to the station? ");
                    System.out.println("1. Fastest way to get");
                    System.out.println("2. Cheapest way to get");
                    System.out.println("3. Most comfortable");
                    System.out.println("4. Best of the best way to get");
                    System.out.println("");
                    System.out.print("Enter option number: ");
                    int travelOption = sc.nextInt();

                    System.out.println("Solution: ");
                    aStar.AStar(map2, src, dst, joe, travelOption);
                    System.out.println("Game Finished");
                    long end = System.currentTimeMillis();
                    long result = 0;
                    result = end - start;
                    System.out.println("Total execution time in milliseconds:" + result);
                    long secs = result / 1000;
                    System.out.println("Total execution time in seconds:" + secs);

                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
// public Station getStationByName(String name) {
// for (Station station : stations) {
// if (station.getName().equalsIgnoreCase(name)) {
// System.out.println("Matching station found: " + station.getName());
// return station;
// }
// }
// return null;
// }

// System.out.println("Welcome to the game! Here are your options:");
// System.out.println("1. Print map");
// System.out.println("2. Travel to another station");
// System.out.println("3. Quit game");

// // Read user input and perform actions based on input
// Scanner sc = new Scanner(System.in);
// while (true) {
// System.out.print("Enter option number: ");
// int option = sc.nextInt();
// if (option == 1) {
// map2.printMap3();
// // MapVisualization mapv = new MapVisualization(400, 400, stations, roads);
// // JFrame frame = new JFrame("Map Visualization");
// // frame.add(mapv);
// // frame.pack();
// // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// // frame.setLocationRelativeTo(null);
// // frame.setVisible(true);
// } else if (option == 2) {
// AStar a = new AStar();
// Station src = null;
// Station dst = null;
// System.out.print("Enter your starting point: ");
// String input = sc.nextLine();
// for (Station station : stations) {
// if (station.getName().equalsIgnoreCase(input)) {
// System.out.println("Matching station found: " + station.getName());
// src = station;
// }
// }

// System.out.print("Enter your destination: ");
// String destination = sc.nextLine();
// for (Station station : stations) {
// if (station.getName().equalsIgnoreCase(destination)) {
// System.out.println("Matching station found: " + station.getName());
// dst = station;
// }
// }
// System.out.print("How much money you have: ");
// int mon = sc.nextInt();
// // Traveler joe = new Traveler(Hamak, AlZabadani, 100, 6000);
// Traveler joe = new Traveler(src, dst, 100, mon);

// System.out.println("How would you like to get to the station? ");
// System.out.println("1. Fastest way to get");
// System.out.println("2. Cheapest way to get");
// System.out.println("3. Most comfortable");
// System.out.print("4. Least cost possible");
// System.out.print("5. Best of the best way to get");
// System.out.print("Enter option number: ");
// int option2 = sc.nextInt();
// System.out.println("solution: " + a.AStar(map2, src, dst, joe, option2));

// } else if (option == 3) {
// break;
// } else {
// System.out.println("Invalid option. Please try again.");
// }
// }

// sc.close();
// System.out.println("Thank you for playing! Goodbye.");
// }

// }