Intelligent Pathfinding Algorithm for Optimized Travel Routes


### General Explanation of the Task

The project involves implementing an intelligent search algorithm to find the most efficient route for a traveler to reach their home under various constraints, such as minimizing time, cost, and energy. The algorithm is designed to handle different transportation options like walking, taking a taxi, or using a bus. The goal is to develop a system that can optimize the travel path based on the user's preferences and the available resources.

### Summary of the Tasks Mentioned in the File

- **Map Class**:
  - Create a class that stores stations (nodes) and roads (edges) in a graph structure.
  - Implement methods to add stations and roads, retrieve all stations and roads, and print the graph.
  - Develop a function to find neighboring stations and identify paths between two nodes.

- **Station Class**:
  - Define attributes such as station name, parent station, and costs associated with each station.
  - Implement methods to set and retrieve these attributes.

- **Road Class**:
  - Create attributes for road start, end, distance, transportation options, and their associated costs.
  - Develop methods to calculate costs for different transportation modes (bus, taxi, walking).
  - Implement a method to calculate total travel time based on selected transportation.

- **Traveler Class**:
  - Define attributes such as the traveler's current location, chosen mode of travel, health, money, and destination.
  - Implement methods to initialize and update these attributes.

- **AStar Class**:
  - Implement the A* algorithm for finding the optimal path considering multiple factors like cost, time, and effort.
  - Create methods to store and compare nodes in a priority queue based on the lowest cumulative cost.
  - Implement functions to evaluate the path, calculate heuristic values, and return the best route.

- **Transportation Methods**:
  - Develop different strategies for selecting transportation methods based on the desired outcome (fastest route, most comfortable, cheapest).
  - Implement conditions to handle insufficient funds or health and adapt the route accordingly.


### Solution 

This project implements an intelligent pathfinding algorithm using the A* (A-star) search technique. It is designed to determine the most efficient route between stations on a map, taking into account various factors such as time, cost, and comfort. The algorithm adapts based on the traveler's current resources (health and money) and offers multiple travel options, including walking, taking a bus, or hiring a taxi.

### Simple Description

- **A* Algorithm Implementation**:
  - The core of the project is an implementation of the A* algorithm, which finds the shortest path between a start station and a goal station on a map.
  - The algorithm uses a priority queue to explore the most promising paths based on the combined cost of reaching the current station and an estimated cost to reach the goal.

- **Heuristic Functions**:
  - The algorithm incorporates several heuristic functions to estimate the cost of different travel options. These include:
    - **Fastest Route**: Prioritizes speed by choosing the quickest travel mode.
    - **Cheapest Route**: Prioritizes saving money by selecting the least expensive travel mode.
    - **Most Comfortable Route**: Balances speed and cost to find the most comfortable travel experience.
    - **Best Route**: Considers a combination of factors (time, cost, comfort) to suggest the overall best option.

- **Dynamic Path Calculation**:
  - The algorithm dynamically adjusts the path based on the traveler's remaining health and money, ensuring that the traveler can reach the destination without exhausting their resources.

- **Path and Cost Tracking**:
  - The system tracks the path taken, the remaining health and money of the traveler, and prints the details of each step, including the mode of transportation used and the time/money/health costs associated with each segment.

This project demonstrates a practical application of the A* algorithm in solving real-world problems, with considerations for user constraints and multiple optimization criteria.