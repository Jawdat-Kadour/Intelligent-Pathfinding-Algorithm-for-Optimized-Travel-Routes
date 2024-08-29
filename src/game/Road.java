
package game;

import java.util.ArrayList;

public class Road {

    // The source station of the edge
    private final Station source;

    // The destination station of the edge
    private final Station destination;

    // The distance between the two stations
    private final float distance;

    // Possibility of passing a transport
    private final boolean passingTransport;

    // Bus line name
    private final String busLineName;

    // Bus speed
    private final float busSpeed;

    // Taxi speed
    private final float taxiSpeed;

    // The weight of the edge
    // private final int weight;

    private double busCost;

    private double taxiCost;

    public double getTaxiCost() {
        return taxiCost;
    }

    public double getBusCost() {
        return busCost;
    }

    public void setBusCost() {
        this.busCost = 400;
    }

    public void setTaxiCost() {
        this.taxiCost = 1000 * getDistance();
    }

    // Constructor
    public Road(Station source, Station destination, float distance, boolean passingTransport,
            String busLineName, float busSpeed, float taxiSpeed) {
        this.source = source;
        this.destination = destination;
        // this.weight = weight;
        this.distance = distance;
        this.passingTransport = passingTransport;
        this.busLineName = busLineName;
        this.busSpeed = busSpeed;
        this.taxiSpeed = taxiSpeed;
    }

    // Get Taxi Speed
    public float getTaxiSpeed() {
        return taxiSpeed;
    }

    // Get Bus Speed
    public float getBusSpeed() {
        return busSpeed;
    }

    // Get Bus line name
    public String getBusLineName() {
        return busLineName;
    }

    // Get Possibility of passing a transport
    public boolean getPassingTransport() {
        return passingTransport;
    }

    // Get the distance
    public float getDistance() {
        return distance;
    }

    // Get the source stations of the edge
    public Station getSource() {
        return source;
    }

    // Get the destination stations of the edge
    public Station getDestination() {
        return destination;
    }

    // Get the weight of the edge
    // public int getWeight() {
    // return weight;
    // }

    public double getTravelTime(TravelMode travelMode) {
        double speed = 0;
        float waitingTime = 0;
        if (travelMode == TravelMode.BUS) {
            speed = this.busSpeed;
            waitingTime = this.source.getExTimeBus();
        } else if (travelMode == TravelMode.TAXI) {
            speed = this.taxiSpeed;
            waitingTime = this.source.getExTimeTaxi();
        } else if (travelMode == TravelMode.WALK) {
            speed = 5.5;
            waitingTime = 0;
        }
        // Calculate the time it takes to travel the distance at the specified speed
        double travelTime = this.distance / speed;
        // Return the total time it takes to travel from the source to the destination,
        // including the waiting time
        return waitingTime + travelTime;
    }

    public double getTravelTimeH(TravelMode travelMode) {
        double speed = 0;
        float waitingTime = 0;
        if (travelMode == TravelMode.BUS) {
            speed = this.busSpeed + 20;
            // waitingTime = this.source.getExTimeBus();
        } else if (travelMode == TravelMode.TAXI) {
            speed = this.taxiSpeed + 20;
            // waitingTime = this.source.getExTimeTaxi();
        } else if (travelMode == TravelMode.WALK) {
            speed = 5.5 + 2;
            waitingTime = 0;
        }
        // Calculate the time it takes to travel the distance at the specified speed
        double travelTime = this.distance / speed;
        // Return the total time it takes to travel from the source to the destination,
        // including the waiting time
        return waitingTime + travelTime;
    }

    public double moneyCost(TravelMode travelMode) {
        double mCost = 0;
        if (travelMode == TravelMode.BUS) {
            this.setBusCost();
            mCost = this.getBusCost();
            // System.out.println("bus " + mCost);
        } else if (travelMode == TravelMode.TAXI) {
            this.setTaxiCost();
            mCost = this.getTaxiCost();
            // System.out.println("taxi " + mCost);
        } else {
            // traveling by foot is zero
            return 0;
        }
        return mCost;
    }

    public double healthCost(TravelMode travelMode, Traveler joe) {
        double hCost = 0;
        if (travelMode == TravelMode.BUS) {
            hCost = 5 * this.getDistance();
            // joe.setHealth(hCost);
        } else if (travelMode == TravelMode.TAXI) {
            hCost = -5 * this.getDistance();
            // joe.setHealth(hCost);
        } else {
            // traveling by foot is zero
            hCost = 10 * this.getDistance();
            // joe.setHealth(hCost);
        }
        return hCost;
    }

    public double getGCost(TravelMode travelMode, Traveler joe) {
        double cost = this.healthCost(travelMode, joe) + this.moneyCost(travelMode) + this.getTravelTime(travelMode);
        return cost;
    }

    public double getHCost(TravelMode travelMode, Traveler joe) {
        double cost = this.healthCost(travelMode, joe) + this.moneyCost(travelMode) + this.getTravelTimeH(travelMode);
        return cost;
    }

    public TravelMode leastRealPathCost(Traveler joe) {
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        // least cost possible without reaching health to 0 and without reaching
        // moneyCost more than the traveler has;
        if (this.getTaxiCost() <= this.getBusCost()
                && (this.getTravelTime(TravelMode.TAXI) < this.getTravelTime(TravelMode.WALK))) {

            return TravelMode.TAXI;
        } else if (this.getBusCost() < this.getTaxiCost()
                && (this.healthCost(TravelMode.BUS, joe) < this.healthCost(TravelMode.TAXI, joe) ||
                        this.getTravelTime(TravelMode.BUS) < this.getTravelTime(TravelMode.WALK))) {

            return TravelMode.BUS;
        } else

            return TravelMode.WALK;
    }

    public TravelMode leastHPathCost(Traveler joe) {
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        // least cost possible without reaching health to 0 and without reaching
        // moneyCost more than the traveler has;
        if (this.getTaxiCost() <= this.getBusCost()
                && (this.healthCost(TravelMode.TAXI, joe) < this.healthCost(TravelMode.BUS, joe) ||
                        this.getTravelTimeH(TravelMode.TAXI) < this.getTravelTimeH(TravelMode.WALK))) {

            return TravelMode.TAXI;
        } else if (this.getBusCost() < this.getTaxiCost()
                && (this.healthCost(TravelMode.BUS, joe) < this.healthCost(TravelMode.TAXI, joe) ||
                        this.getTravelTimeH(TravelMode.BUS) < this.getTravelTimeH(TravelMode.WALK))) {

            return TravelMode.BUS;
        } else

            return TravelMode.WALK;
    }

    public TravelMode getFastestTransportationMethod(Traveler joe, Station goal, Map graph) {
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        // firstly we have to get each road fo the path assuming this is the right path
        // secondly we have to initialize a variable to sum the cost of the path
        // we put these statements the loop above to calculate the cost
        // depending on this variable we choose the current travel mode.
        // double totalCost = 0;
        // ArrayList<Road> pathRoads = null;
        // for (int i = 0 ; i< path.size() ; i++){
        // Road r = graph.getRoad(path.get(i), path.get(i+1));
        // pathRoads.add(r);
        // }
        // for(Road road: pathRoads)
        // {
        // for(TravelMode t: TravelMode.values()) }
        double taxiCo = this.getTravelTime(TravelMode.TAXI);
        double busCo = this.getTravelTime(TravelMode.BUS);
        double walkCo = this.getTravelTime(TravelMode.WALK);

        if (this.getTaxiCost() > joe.getMoney()) {
            taxiCo = Double.MAX_VALUE;
            // this.setTaxiCost(Integer.MAX_VALUE);
        }
        if (this.getBusCost() > joe.getMoney()) {
            busCo = Double.MAX_VALUE;
        }

        // Check if you have enough energy to walk the distance
        if (this.healthCost(TravelMode.WALK, joe) > joe.getHealth()) {
            walkCo = Double.MAX_VALUE;

        }

        // if (healthCost(TravelMode.WALK, joe) > joe.getHealth()) {
        // if (getTravelTime(TravelMode.BUS) > getTravelTime(TravelMode.TAXI)) {
        // if (joe.getMoney() > getTaxiCost())
        // return TravelMode.TAXI;
        // } else {
        // if (joe.getMoney() > getBusCost())
        // return TravelMode.BUS;
        // }

        if (!this.getDestination().equals(goal)) {
            if (joe.getMoney() >= this.getTaxiCost() + this.getBusCost() * 2) {
                // if it's not the last road
                if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                    // then we deciede if we have to take a taxi or not.
                    if (taxiCo < busCo && taxiCo < walkCo) {

                        return TravelMode.TAXI;
                    } else if (busCo < taxiCo && busCo < walkCo) {

                        return TravelMode.BUS;
                    } else {

                        return TravelMode.WALK;
                    }
                } else {
                    taxiCo *= 20;
                    if (taxiCo < busCo && taxiCo < walkCo) {

                        return TravelMode.TAXI;
                    } else if (busCo < taxiCo && busCo < walkCo) {

                        return TravelMode.BUS;
                    } else {

                        return TravelMode.WALK;
                    }
                }
            } else {
                // if (taxiCo < busCo && taxiCo < walkCo) {

                // return TravelMode.TAXI;
                // } else

                if (busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            }
        } else {
            if (taxiCo < busCo && taxiCo < walkCo) {

                return TravelMode.TAXI;
            } else if (busCo < taxiCo && busCo < walkCo) {

                return TravelMode.BUS;
            } else {

                return TravelMode.WALK;
            }
        }
    }

    // public TravelMode getBestTransportationMethod(Traveler joe, Station goal) {
    // if (!passingTransport) {
    // return TravelMode.WALK;
    // }
    // double taxiCo = this.healthCost(TravelMode.TAXI, joe);
    // double busCo = this.healthCost(TravelMode.BUS, joe);
    // double walkCo = this.healthCost(TravelMode.WALK, joe);
    // double taxitime = this.getTravelTime(TravelMode.TAXI);
    // double bustime = this.getTravelTime(TravelMode.BUS);
    // double walktime = this.getTravelTime(TravelMode.WALK);
    // if (this.getTaxiCost() > joe.getMoney()) {
    // taxiCo = Double.MAX_VALUE;
    // }
    // if (this.getBusCost() > joe.getMoney()) {
    // busCo = Double.MAX_VALUE;
    // }
    // if (busCo > joe.getHealth()) {
    // busCo = Double.MAX_VALUE;
    // }
    // if (walkCo > joe.getHealth()) {
    // walkCo = Double.MAX_VALUE;
    // }
    // if (this.getDestination().equals(goal)) {
    // if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
    // if (joe.getMoney() >= this.getTaxiCost()) {
    // return TravelMode.TAXI;
    // } else if (joe.getMoney() >= this.getBusCost() && joe.getHealth() >
    // healthCost(TravelMode.BUS, joe)) {
    // return TravelMode.BUS;
    // } else {
    // return TravelMode.WALK;
    // }
    // } else if (joe.getMoney() >= this.getBusCost() && joe.getHealth() >
    // healthCost(TravelMode.BUS, joe)) {
    // return TravelMode.BUS;
    // } else {
    // return TravelMode.WALK;
    // }
    // } else {
    // if (busCo < taxiCo && healthCost(TravelMode.BUS, joe) < joe.getHealth()
    // && this.getBusCost() <= joe.getMoney()) {
    // return TravelMode.BUS;
    // } else if (joe.getHealth() < healthCost(TravelMode.BUS, joe) &&
    // this.getTaxiCost() <= joe.getMoney()) {
    // return TravelMode.TAXI;
    // } else {
    // return TravelMode.WALK;
    // }
    // }

    // }

    public TravelMode getBestTransportationMethod(Traveler joe, Station goal) {
        if (!passingTransport) {
            return TravelMode.WALK;
        }

        TransportationOption taxiOption = new TransportationOption(TravelMode.TAXI, this.getTaxiCost(),
                this.getTravelTime(TravelMode.TAXI), this.healthCost(TravelMode.TAXI, joe));
        TransportationOption busOption = new TransportationOption(TravelMode.BUS, this.getBusCost(),
                this.getTravelTime(TravelMode.BUS), this.healthCost(TravelMode.BUS, joe));
        TransportationOption walkOption = new TransportationOption(TravelMode.WALK, 0,
                this.getTravelTime(TravelMode.WALK), this.healthCost(TravelMode.WALK, joe));

        if (joe.getMoney() < taxiOption.cost) {
            taxiOption.cost = Double.MAX_VALUE;
        }
        if (joe.getMoney() < busOption.cost) {
            busOption.cost = Double.MAX_VALUE;
        }
        if (joe.getHealth() < busOption.healthImpact) {
            busOption.cost = Double.MAX_VALUE;
        }
        if (joe.getHealth() < walkOption.healthImpact) {
            walkOption.cost = Double.MAX_VALUE;
        }

        if (!this.getDestination().equals(goal)) {
            if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                if (taxiOption.isFeasible(joe) && joe.getMoney() >= getTaxiCost() + getBusCost() * 2) {
                    return taxiOption.mode;
                } else if (busOption.isFeasible(joe) && busOption.compareTo(walkOption) < 0) {
                    return busOption.mode;

                } else {
                    return walkOption.mode;
                }
                // } else if (busOption.isFeasible(joe)) {
                // return busOption.mode;
                // } else {
                // return walkOption.mode;
                // }
            } else {
                if (busOption.isFeasible(joe)) {
                    return busOption.mode;
                } else {
                    return walkOption.mode;
                }
            }
        } else {
            if (taxiOption.isFeasible(joe))
                return taxiOption.mode;
            else if (busOption.isFeasible(joe)) {
                return busOption.mode;
            } else
                return walkOption.mode;
        }
    }

    private class TransportationOption implements Comparable<TransportationOption> {
        TravelMode mode;
        double cost;
        double time;
        double healthImpact;

        public TransportationOption(TravelMode mode, double cost, double time, double healthImpact) {
            this.mode = mode;
            this.cost = cost;
            this.time = time;
            this.healthImpact = healthImpact;
        }

        public boolean isFeasible(Traveler joe) {
            return this.cost <= joe.getMoney() && this.healthImpact <= joe.getHealth();
        }

        @Override
        public int compareTo(TransportationOption other) {
            // if (this.cost < other.cost) {
            // return -1;
            // } else
            // if (this.cost > other.cost) {
            // return 1;
            // } else {
            if (this.time < other.time) {
                return -1;
            } else if (this.time > other.time) {
                return 1;
            } else {
                return 0;
            }
        }

    }

    // else {
    // if (joe.getHealth() > healthCost(TravelMode.WALK, joe))
    // return TravelMode.WALK;
    // else if (joe.getHealth() > healthCost(TravelMode.BUS, joe)) {
    // if (joe.getMoney() > busCo)
    // return TravelMode.BUS;
    // } else if (joe.getMoney() > taxiCo)
    // return TravelMode.TAXI;
    // else
    // return TravelMode.WALK;
    // }
    // +++++++++++++++++++++++++
    // else {
    // if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
    // // then we deciede if we have to take a taxi or not.
    // if (taxiCo < busCo && taxiCo < walkCo) {

    // return TravelMode.TAXI;
    // } else if (busCo < taxiCo && busCo < walkCo) {

    // return TravelMode.BUS;
    // } else {

    // return TravelMode.WALK;
    // }
    // } else {
    // taxiCo = Double.MAX_VALUE;
    // // if (taxiCo < busCo && taxiCo < walkCo) {

    // // return TravelMode.TAXI;
    // // } else
    // if (busCo < walkCo) {

    // return TravelMode.BUS;
    // } else {

    // return TravelMode.WALK;
    // }
    // }
    // }

    // firstly we have to get each road fo the path assuming this is the right path
    // secondly we have to initialize a variable to sum the cost of the path
    // we put these statements the loop above to calculate the cost
    // depending on this variable we choose the current travel mode.

    // for(Road road: pathRoads)
    // {
    // for(TravelMode t: TravelMode.values())

    // }

    // if (joe.getMoney() <= (this.getTaxiCost() + this.getBusCost() * 2))
    // if (!this.getDestination().equals(goal))
    // else {
    // // if (taxiCo < busCo && taxiCo < walkCo) {

    // // return TravelMode.TAXI;
    // // } else
    // if (busCo < taxiCo && busCo < walkCo) {

    // return TravelMode.BUS;
    // } else {

    // return TravelMode.WALK;
    // }
    // }

    // double taxiTime = this.getTravelTime(TravelMode.TAXI);
    // double busTime = this.getTravelTime(TravelMode.BUS);
    // double walkTime = this.getTravelTime(TravelMode.WALK);
    // double taxiCost = this.getTaxiCost();
    // double busCost = this.getBusCost();
    // double walkCost = 0;
    // double taxiHealthCost = this.healthCost(TravelMode.TAXI, joe);
    // double busHealthCost = this.healthCost(TravelMode.BUS, joe);
    // double walkHealthCost = this.healthCost(TravelMode.WALK, joe);

    // TravelMode t1 = this.getFastestTransportationMethod(joe, goal,graph);
    // TravelMode t2 = this.getMostComfortableTransportationMethod(joe, null, );
    // TravelMode t3 = this.getCheapestTransportationMethod(joe, goal);

    // double cos1 = this.getGCost(t1, joe);
    // double cos2 = this.getGCost(t2, joe);
    // double cos3 = this.getGCost(t3, joe);
    // if (joe.getMoney() <= (this.getTaxiCost() + this.getBusCost() * 2)) {
    // if (!this.getDestination().equals(goal))
    // // if it's not the last road
    // if (taxiTime < busTime) {
    // if (taxiTime < walkTime) {
    // return TravelMode.TAXI;
    // }

    // }

    // if (taxiCost > joe.getMoney()) {
    // taxiTime = Double.MAX_VALUE;
    // taxiCost = Double.MAX_VALUE;
    // }
    // if (busCost > joe.getMoney()) {
    // busTime = Double.MAX_VALUE;
    // busCost = Double.MAX_VALUE;
    // }
    // if (walkHealthCost > joe.getHealth()) {
    // walkTime = Double.MAX_VALUE;
    // walkHealthCost = Double.MAX_VALUE;
    // }
    // if (!this.getDestination().equals(goal)) {
    // if (joe.getHealth() <= 100 - 5 * this.getDistance())
    // {
    // if (cos1 < cos2)
    // if (cos1 < cos3)
    // return t1;
    // else
    // return t3;
    // else {
    // if (cos2 < cos3)
    // return t2;
    // else
    // return t3;
    //

    // choose transportation with highest health cost
    // if (taxiHealthCost > busHealthCost && taxiHealthCost > walkHealthCost) {
    // return TravelMode.TAXI;
    // } else if (busHealthCost > taxiHealthCost && busHealthCost > walkHealthCost)
    // {
    // return TravelMode.BUS;
    // } else {
    // return TravelMode.WALK;
    // }
    // } else {
    // // choose transportation with lowest cost
    // if (taxiCost < busCost && taxiCost < walkCost) {
    // return TravelMode.TAXI;
    // } else if (busCost < taxiCost && busCost < walkCost) {
    // return TravelMode.BUS;
    // } else {
    // return TravelMode.WALK;
    // }
    // }
    // } else {
    // // choose transportation with shortest time and lowest cost
    // if (taxiTime < busTime && taxiTime < walkTime && taxiCost < busCost &&
    // taxiCost < walkCost) {
    // return TravelMode.TAXI;
    // } else if (busTime < taxiTime && busTime < walkTime && busCost < taxiCost &&
    // busCost < walkCost) {
    // return TravelMode.BUS;
    // } else {
    // return TravelMode.WALK;
    // }

    // public TravelMode getBestTransportationMethod(Traveler joe) {
    // if (!passingTransport) {

    // return TravelMode.WALK;
    // }
    // double taxiCo = this.getGCost(TravelMode.TAXI, joe);
    // double busCo = this.getGCost(TravelMode.BUS, joe);
    // double walkCo = this.getGCost(TravelMode.WALK, joe);

    // if (this.getTaxiCost() > joe.getMoney()) {
    // taxiCo = Double.MAX_VALUE;
    // // this.setTaxiCost(Integer.MAX_VALUE);
    // }
    // if (this.getBusCost() > joe.getMoney()) {
    // busCo = Double.MAX_VALUE;
    // }

    // if (this.healthCost(TravelMode.BUS, joe) > joe.getHealth()) {
    // busCo = Double.MAX_VALUE;
    // }
    // if (this.healthCost(TravelMode.WALK, joe) > joe.getHealth()) {
    // walkCo = Double.MAX_VALUE;
    // }

    // if (taxiCo < busCo && taxiCo < walkCo) {

    // return TravelMode.TAXI;
    // } else if (busCo < taxiCo && busCo < walkCo) {

    // return TravelMode.BUS;
    // } else {

    // return TravelMode.WALK;
    // }
    // }

    public TravelMode getCheapestTransportationMethod(Traveler joe, Station goal) {
        // System.out.println("YOUR SO TIRED AND POOR, PLEASE ORDER YOUR M0LUKHIA ☺");
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        double taxiCo = this.getTaxiCost();
        double busCo = this.getBusCost();
        double walkCo = healthCost(TravelMode.WALK, joe);

        if (this.getTaxiCost() > joe.getMoney()) {
            taxiCo = Double.MAX_VALUE;
            // this.setTaxiCost(Integer.MAX_VALUE);
        }
        if (this.getBusCost() > joe.getMoney()) {
            busCo = Double.MAX_VALUE;
        }

        // Check if you have enough energy to walk the distance
        if (this.healthCost(TravelMode.WALK, joe) > joe.getHealth()) {
            walkCo = Double.MAX_VALUE;
        }
        // double walkCo = 0;

        // if it's not the last road

        // Check if you have to walk big distance
        // {walkCo+=busCo;
        // if (this.healthCost(TravelMode.WALK, joe) < joe.getHealth()) {

        // return TravelMode.WALK;
        // }
        if (!this.getDestination().equals(goal)) {
            if (this.healthCost(TravelMode.WALK, joe) >= 40)
                if (taxiCo <= busCo && this.getTaxiCost() < joe.getMoney())
                    return TravelMode.TAXI;
                else if (busCo < taxiCo && healthCost(TravelMode.BUS, joe) < joe.getHealth()
                        && this.getBusCost() < joe.getMoney())
                    return TravelMode.BUS;
                else if (joe.getMoney() > busCo)
                    return TravelMode.BUS;
                else if (joe.getMoney() > taxiCo)
                    return TravelMode.TAXI;
                else
                    return TravelMode.WALK;
        } else {
            if (joe.getHealth() > healthCost(TravelMode.WALK, joe))
                return TravelMode.WALK;
            else if (joe.getHealth() > healthCost(TravelMode.BUS, joe)) {
                if (joe.getMoney() > busCo)
                    return TravelMode.BUS;
            } else if (joe.getMoney() > taxiCo)
                return TravelMode.TAXI;
            else
                return TravelMode.WALK;
        }
        return null;
    }
    // if (this.healthCost(TravelMode.WALK, joe) < joe.getHealth()) {

    // return TravelMode.WALK;
    // }

    // if (taxiCo <= busCo && this.getTaxiCost() < joe.getMoney()) {

    // return TravelMode.TAXI;
    // } else if (busCo < taxiCo && healthCost(TravelMode.BUS, joe) <
    // joe.getHealth()
    // && this.getBusCost() < joe.getMoney()) {

    // return TravelMode.BUS;
    // } else {

    // return TravelMode.WALK;
    // // add rocket travel mode
    // }
    // }
    // return TravelMode.BUS;
    // else
    // return

    // public TravelMode getMostComfortableTransportationMethod(Traveler joe, Map
    // graph) {
    // // get the neighbors of the current node
    // Station source = this.getSource();
    // List<Station> parents;
    // if (source.getParent() != null) {
    // parents = (List<Station>) source.getParent();

    // // initialize the maximum possible health that can be reached at the
    // // destination
    // double maxHealthReachable = joe.getHealth();

    // // initialize the cost and health change for each transportation option
    // double taxiCost = this.getTaxiCost();
    // double busCost = this.getBusCost();
    // double walkCost = 0;
    // double taxiHealthChange = joe.getHealth() - this.healthCost(TravelMode.TAXI,
    // joe);
    // double busHealthChange = joe.getHealth() - this.healthCost(TravelMode.BUS,
    // joe);
    // double walkHealthChange = joe.getHealth() - this.healthCost(TravelMode.WALK,
    // joe);

    // // if the cost of a transportation option exceeds the money in the traveler's
    // // pocket,
    // // set the cost and health change to the maximum value
    // if (this.getTaxiCost() > joe.getMoney()) {
    // taxiCost = Double.MAX_VALUE;
    // taxiHealthChange = 0;
    // }
    // if (this.getBusCost() > joe.getMoney()) {
    // busCost = Double.MAX_VALUE;
    // busHealthChange = 0;
    // }
    // if (walkCost > joe.getHealth()) {
    // walkCost = Double.MAX_VALUE;
    // walkHealthChange = 0;
    // }

    // // check the maximum health that can be reached by taking each transportation
    // // option
    // for (Station neighbor : parents) {
    // Road r = graph.getRoad(source, neighbor);
    // for (TravelMode travelMode : TravelMode.values())
    // maxHealthReachable = Math.max(maxHealthReachable,
    // r.healthCost(travelMode, joe) + joe.getHealth());
    // }

    // // choose the transportation option with the maximum health change,
    // // while not exceeding the cost of the road in the traveler's pocket
    // if (taxiHealthChange >= busHealthChange && taxiHealthChange >=
    // walkHealthChange) {
    // return (taxiCost <= joe.getMoney()) ? TravelMode.TAXI : TravelMode.BUS;
    // } else if (busHealthChange >= taxiHealthChange && busHealthChange >=
    // walkHealthChange) {
    // return (busCost <= joe.getMoney()) ? TravelMode.BUS : TravelMode.WALK;
    // } else {
    // return (walkCost <= joe.getMoney()) ? TravelMode.WALK : TravelMode.WALK;
    // }
    // } else
    // return TravelMode.TAXI;
    // }

    public TravelMode getMostComfortableTransportationMethod(Traveler joe, Map graph,

            Station goal) {
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        // firstly we have to get each road fo the path assuming this is the right path
        // secondly we have to initialize a variable to sum the cost of the path
        // we put these statements the loop above to calculate the cost
        // depending on this variable we choose the current travel mode.

        // for(Road road: pathRoads)
        // {
        // for(TravelMode t: TravelMode.values())

        // }

        double taxiCo = this.healthCost(TravelMode.TAXI, joe);
        double busCo = this.healthCost(TravelMode.BUS, joe);
        double walkCo = this.healthCost(TravelMode.WALK, joe);

        if (this.getTaxiCost() > joe.getMoney()) {
            taxiCo = Double.MAX_VALUE;
            // this.setTaxiCost(Integer.MAX_VALUE);
        }
        if (this.getBusCost() > joe.getMoney()) {
            busCo = Double.MAX_VALUE;
        }

        if (busCo > joe.getHealth()) {
            busCo = Double.MAX_VALUE;
        }
        if (walkCo > joe.getHealth()) {
            walkCo = Double.MAX_VALUE;
        }
        if (joe.getMoney() <= (this.getTaxiCost() + this.getBusCost() * 2)) {
            if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                // then we deciede if we have to take a taxi or not.
                if (taxiCo < busCo && taxiCo < walkCo) {

                    return TravelMode.TAXI;
                } else if (busCo < taxiCo && busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            } else {
                // taxiCo = Double.MAX_VALUE;
                // if (taxiCo < busCo && taxiCo < walkCo) {

                // return TravelMode.TAXI;
                // } else
                if (busCo < taxiCo && busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            }
        } else {
            if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                if (taxiCo < busCo && taxiCo < walkCo) {

                    return TravelMode.TAXI;
                } else if (busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            } else {
                if (busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }

            }
        }

    }

    public TravelMode getMostComfortableTransportationMethod1(Traveler joe, ArrayList<Station> path, Map graph,

            Station goal) {
        if (!passingTransport) {

            return TravelMode.WALK;
        }
        // firstly we have to get each road fo the path assuming this is the right path
        // secondly we have to initialize a variable to sum the cost of the path
        // we put these statements the loop above to calculate the cost
        // depending on this variable we choose the current travel mode.
        double totaldistance = 0;
        // ArrayList<Road> pathRoads = null;
        for (int i = 0; i < path.size() - 1; i++) {
            Road r = graph.getRoad(path.get(i), path.get(i + 1));
            // pathRoads.add(r);
            totaldistance += r.getDistance();
        }

        // for(Road road: pathRoads)
        // {
        // for(TravelMode t: TravelMode.values())

        // }
        if (totaldistance * 1000 < joe.getMoney()) {
            return TravelMode.TAXI;
        }
        double taxiCo = this.healthCost(TravelMode.TAXI, joe);
        double busCo = this.healthCost(TravelMode.BUS, joe);
        double walkCo = this.healthCost(TravelMode.WALK, joe);

        if (this.getTaxiCost() > joe.getMoney()) {
            taxiCo = Double.MAX_VALUE;
            // this.setTaxiCost(Integer.MAX_VALUE);
        }
        if (this.getBusCost() > joe.getMoney()) {
            busCo = Double.MAX_VALUE;
        }

        if (busCo > joe.getHealth()) {
            busCo = Double.MAX_VALUE;
        }
        if (walkCo > joe.getHealth()) {
            walkCo = Double.MAX_VALUE;
        }
        if (joe.getMoney() <= (this.getTaxiCost() + this.getBusCost() * 2)) {
            if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                // then we deciede if we have to take a taxi or not.
                if (taxiCo < busCo && taxiCo < walkCo) {
                    return TravelMode.TAXI;
                } else if (busCo < taxiCo && busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            } else {
                // taxiCo = Double.MAX_VALUE;
                // if (taxiCo < busCo && taxiCo < walkCo) {

                // return TravelMode.TAXI;
                // } else
                if (busCo < taxiCo && busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            }
        } else {
            if (joe.getHealth() <= 100 - 5 * this.getDistance()) {
                if (taxiCo < busCo && taxiCo < walkCo) {

                    return TravelMode.TAXI;
                } else if (busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }
            } else {
                if (busCo < walkCo) {

                    return TravelMode.BUS;
                } else {

                    return TravelMode.WALK;
                }

            }
        }

    }

}

enum TravelMode {
    BUS, TAXI, WALK
}
