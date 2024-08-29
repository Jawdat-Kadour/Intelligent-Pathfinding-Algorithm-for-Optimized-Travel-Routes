package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    // A* algorithm to find the shortest path from the start node to the goal node
    public ArrayList<Station> AStar(Map graph, Station start, Station goal, Traveler T, int option) {
        // Initialize the priority queue with the start node
        PriorityQueue<Station> queue = new PriorityQueue<>(new Comparator<Station>() {

            @Override
            public int compare(Station n1, Station n2) {
                if (n1.getWeight() < n2.getWeight()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        int cnt = 0;

        queue.add(start);

        HashMap<Station, Double> fValues = new HashMap<>();
        fValues.put(start, 0.0);

        while (!queue.isEmpty()) {

            // Remove the node with the lowest f-value from the queue
            Station current = queue.poll();
            cnt++;
            // If the current node is the goal, return the path
            if (current.equals(goal)) {
                System.out.println("Number of stations that are processed: " + cnt);
                return path(current, T, graph, option, goal);
            }
            // Otherwise, add the neighbors of the current node to the queue
            for (Station neighbor : graph.getNeighbors(current)) {

                Road road = graph.getRoad(current, neighbor);

                double pathCost = heuristicCost(T, road, option, graph, goal)
                        + hCost(T, road);
                current.setWeight(pathCost);

                double fValue = pathCost;
                // If the neighbor has not been visited or has a lower f-value than the current
                // value
                if (!fValues.containsKey(neighbor) || fValue > fValues.get(neighbor)) {
                    // Update the f-value of the neighbor
                    fValues.put(neighbor, fValue);
                    // Add the neighbor to the queue
                    queue.add(neighbor);
                    // Update the parent of the neighbor to the current node
                    neighbor.setParent(current);
                }

            }

        }

        // If the queue is empty and the goal has not been reached, return an empty path
        return new ArrayList<>();
    }

    // Return the path from the start node to the goal node

    private static double heuristicCost(Traveler T, Road r, int option, Map g, Station goal) {

        if (option == 1) {
            return r.getGCost(r.getFastestTransportationMethod(T, goal, g), T);

        } else if (option == 2) {
            return r.getGCost(r.getCheapestTransportationMethod(T, goal), T);
        } else if (option == 3) {
            return r.getGCost(r.getMostComfortableTransportationMethod(T, g, goal), T);
        } else if (option == 4) {
            return r.getGCost(r.getBestTransportationMethod(T, goal), T);

        } else {
            return r.getGCost(r.leastRealPathCost(T), T);

        }

    }

    private static double hCost(Traveler T, Road r) {

        return r.getHCost(r.leastHPathCost(T), T);

    }

    // public List<Station> findBestCollection(List<Station> stations,Map graph2,
    // Station source, Station destination , int option , Station node) {

    // stations = this.path(node, null, , option);
    // // Initialize the minimum cost to a large value
    // double minCost = Double.MAX_VALUE;
    // double healthCost = Double.MAX_VALUE;
    // double moneyCost = Double.MAX_VALUE;
    // double timeCost = Double.MAX_VALUE;
    // // Initialize the minimum path to an empty list
    // List<Station> minPath = List.of();

    // // Try all possible travel modes
    // for(Station station : stations)
    // {
    // for (TravelMode mode : TravelMode.values()) {
    // // Find the path using the current travel mode

    // // Calculate the cost of the path
    // double cost = getPathCost(path, mode);
    // // If the cost is lower than the current minimum, update the minimum cost and
    // path
    // if (cost < minCost) {
    // minCost = cost;
    // minPath = path;
    // }
    // }return minPath;
    // }
    private static ArrayList<Station> path(Station node, Traveler T, Map graph, int option, Station goal) {
        ArrayList<Station> path = new ArrayList<>();
        // Follow the parents of the nodes from the goal node back to the start node
        while (node != null) {
            path.add(node);

            node = node.getParent();
        }
        Collections.reverse(path);

        for (int i = 0; i < path.size() - 1; i++) {
            Road road = graph.getRoad(path.get(i), path.get(i + 1));
            System.out.println("**************************");
            System.out.println("Health left: " + T.getHealth());
            System.out.println("Money left: " + T.getMoney());
            System.out.println("From: " + path.get(i).getName());
            System.out.println("To: " + path.get(i + 1).getName());
            // System.out.println(road.getSource().getName());
            // System.out.println(road.getDestination().getName());
            if ((T.getHealth() < road.healthCost(TravelMode.BUS, T)
                    && T.getHealth() < road.healthCost(TravelMode.WALK, T))
                    || (T.getMoney() < road.moneyCost(TravelMode.TAXI)
                            && T.getMoney() < road.moneyCost(TravelMode.BUS))
            // || T.getHealth() - road.healthCost(TravelMode.BUS, T) == 0
            // || T.getHealth() - road.healthCost(TravelMode.WALK, T) == 0
            ) {
                System.out.println("You can not Select this Option Please Select another one!!!!!!");
                System.out.println("Ther is no path");
                return new ArrayList<>();

            }
            if (option == 5) {
                if (road.leastRealPathCost(T) == TravelMode.TAXI) {
                    System.out.println("Travel Mode taxi");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.TAXI) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.TAXI, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.TAXI));
                }

                else if (road.leastRealPathCost(T) == TravelMode.BUS) {
                    System.out.println("Travel Mode bus");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.BUS) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.BUS, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.BUS));
                }

                else {
                    System.out.println("Travel Mode walk");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.WALK) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.WALK, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.WALK));
                }
            }

            if (option == 1) {
                if (road.getFastestTransportationMethod(T, goal, graph) == TravelMode.TAXI) {
                    System.out.println("Travel Mode taxi");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.TAXI) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.TAXI, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.TAXI));
                }

                else if (road.getFastestTransportationMethod(T, goal, graph) == TravelMode.BUS) {
                    System.out.println("Travel Mode bus");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.BUS) * 60 + "  minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.BUS, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.BUS));
                }

                else {
                    System.out.println("Travel Mode walk");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.WALK) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.WALK, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.WALK));
                }
            }

            if (option == 2) {
                if (road.getCheapestTransportationMethod(T, goal) == TravelMode.TAXI) {
                    System.out.println("Travel Mode taxi");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.TAXI) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.TAXI, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.TAXI));
                }

                else if (road.getCheapestTransportationMethod(T, goal) == TravelMode.BUS) {
                    System.out.println("Travel Mode bus");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.BUS) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.BUS, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.BUS));
                }

                else {
                    System.out.println("Travel Mode walk");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.WALK) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.WALK, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.WALK));
                }
            }

            if (option == 3) {
                if (road.getMostComfortableTransportationMethod1(T, path, graph, goal) == TravelMode.TAXI) {
                    System.out.println("Travel Mode taxi 2q");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.TAXI) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.TAXI, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.TAXI));
                }

                else if (road.getMostComfortableTransportationMethod1(T, path, graph, goal) == TravelMode.BUS) {
                    System.out.println("Travel Mode bus");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.BUS) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.BUS, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.BUS));
                }

                else {
                    System.out.println("Travel Mode walk");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.WALK) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.WALK, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.WALK));
                }
            }

            if (option == 4) {
                if (road.getBestTransportationMethod(T, goal) == TravelMode.TAXI) {
                    System.out.println("Travel Mode TAXI");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.TAXI) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.TAXI, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.TAXI));
                }

                else if (road.getBestTransportationMethod(T, goal) == TravelMode.BUS) {
                    System.out.println("Travel Mode bus");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.BUS) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.BUS, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.BUS));
                }

                else {
                    System.out.println("Travel Mode walk");
                    System.out.println("Time Cost " + (float) road.getTravelTime(TravelMode.WALK) * 60 + " minutes");
                    T.setHealth(T.getHealth() - road.healthCost(TravelMode.WALK, T));

                    T.setMoney(T.getMoney() - road.moneyCost(TravelMode.WALK));
                }
            }

        }
        // if (T.getHealth() == 0 && T.getMoney() == 0) {
        // System.out.println("You can not Select this Option Please Select another
        // one!!!!!!");
        // System.out.println("There is no path");
        // return new ArrayList<>();
        // }
        System.out.println("Health Left: " + T.getHealth());
        System.out.println("Money Left: " + T.getMoney());
        System.out.println("**************************");
        printPath(path);
        return path;
    }

    private static void printPath(ArrayList<Station> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i).getName());
            if (i < path.size() - 1) {
                sb.append(" ---> ");
            }
        }
        System.out.println(sb.toString());
    }

    // Calculate the estimated cost to reach the goal from the given node
    // private static double heuristic(Station node, Station goal) {
    // // Initialize the estimated cost to 0
    // double cost = 0;
    // // Calculate the distance between the two nodes
    // float distance = 34;
    // // getDistance(node, goal);
    // // Calculate the expected waiting time for the bus or taxi at each station
    // float exTimeBus = node.getExTimeBus();
    // float exTimeTaxi = node.getExTimeTaxi();
    // // Calculate the cost of taking the bus or taxi between the two nodes
    // double busCost = 400;
    // double taxiCost = 1000 * distance;
    // // Choose the transportation option with the lowest cost
    // if (busCost < taxiCost) {
    // cost += exTimeBus + busCost;
    // } else {
    // cost += exTimeTaxi + taxiCost;
    // }
    // // Return the estimated cost
    // return cost;
    // }

}