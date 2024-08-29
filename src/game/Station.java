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
public class Station {

  // The name of the station
  private final String name;

  // The parent of the station
  private Station parent;

  private double weight;

  // The expected waiting time for the bus
  private final float exTimeBus;

  // The expected waiting time for the taxi
  private final float exTimeTaxi;

  // Constructor

  public Station(String name, float exTimeBus, float exTimeTaxi) {
    this.name = name;
    this.exTimeBus = exTimeBus;
    this.exTimeTaxi = exTimeTaxi;
  }

  // Set the parent node of this node
  public void setParent(Station parent) {
    this.parent = parent;
  }

  // Get the parent node of this node
  public Station getParent() {
    return parent;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  // Get the parent node of this node
  public double getWeight() {
    return weight;
  }

  // Get the name of the station
  public String getName() {
    return name;
  }

  // Get expected waiting time for the bus of the station
  public float getExTimeBus() {
    return exTimeBus;
  }

  // Get expected waiting time for the taxi of the station
  public float getExTimeTaxi() {
    return exTimeTaxi;
  }
}
