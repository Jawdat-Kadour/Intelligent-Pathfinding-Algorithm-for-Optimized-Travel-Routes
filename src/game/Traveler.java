/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author mayaa
 */
public 
class Traveler {
    // The current station the traveler is at
    private Station currentStation;

    // The mode of transportation the traveler is using
    private TravelMode travelMode;

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    // The destination station the traveler is trying to reach
    private Station destination;

    // The current health of the traveler
    private double health;

    // The current amount of money the traveler has
    private double money;

    // Constructor
    public Traveler(Station currentStation, Station destination, double health, double money) {
        this.currentStation = currentStation;
        // this.travelMode = travelMode;
        this.destination = destination;
        this.health = health;
        this.money = money;
    }

    // Move the traveler to a different station
    public void moveTo(Station station) {
        this.currentStation = station;
    }

    // Change the mode of transportation the traveler is using

    // Check if the traveler has reached their destination
    public boolean hasReachedDestination() {
        return this.currentStation.equals(this.destination);
    }

    // Get the current health of the traveler
    public double getHealth() {
        return health;
    
   }
    

// Get the current amount of money the traveler has
    public double getMoney() {
        return money;
    }

    // Set the current amount of money the traveler has
    public void setMoney(double money) {
        
       if (money < 0) {
            this.money = 0;
       }
       else
           this.money = money;
    }

    public void setHealth(double hCost) {
        if (hCost < 0) {
            this.health = 0;
        } else if (hCost > 100) {
            this.health = 100;
        } else {
            this.health = hCost;
        }
    }
    enum TravelMode {
    BUS, TAXI, WALK
}

}