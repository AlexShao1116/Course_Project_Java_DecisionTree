# Course_Project_Java_DecisionTree 


## 🎓 Learning Objectives
This project focuses on applying graph theory and algorithmic techniques using Java and JavaFX to solve pathfinding problems in a simulated post-apocalyptic world. The key objectives include:

### 🛠 Project Setup
For optimal setup:
- **Intellij Users:** Ensure JavaFX is configured properly. For Mac, switch to Liberica distribution for media support.
- **Eclipse Users:** Manually install the JavaFX library.

## 🌍 Project Description
In this project, I am tasked with developing an application that navigates through zombie-infested territories safely. Using various pathfinding algorithms, the app will guide users from one safe house to another while collecting necessary resources, avoiding zombies, and overcoming geographical obstacles.

### 🖥 GUI Components
- **Menu:** Includes controls for map navigation and adjustments to visual outputs.
- **Main Map Display:** Depicts different geographic and zombified areas.
- **Commanding Panel and Console Panel:** Allows for command inputs and displays system messages.

### 🎯 Algorithms and Techniques
#### Level 0: Data Initialization
- **Objective:** Initialize map data using subclasses that represent different types of terrain and hazards.
  
#### Level 1: Graph Traversal
- **Breadth-First Search (BFS):** Implemented to explore the nearest nodes first, this technique is crucial for finding the shortest path in an unweighted graph.
- **Depth-First Search (DFS):** Used for exploring all paths through one branching before backtracking, which helps in situations where all possible paths need to be examined.

#### Level 2: Weighted Graphs
- **Dijkstra’s Algorithm:** This is utilized to find the shortest path from a single source node to all other nodes in a weighted graph. It is particularly effective in scenarios where each movement has a cost associated with distance, time, or safety.
- **Implementation:** A graph is constructed where nodes represent points of interest (e.g., safe houses, resource pickups) and edges represent possible paths with weights reflecting the cost of traversal.

#### Level 3: Priority Queuing
- **Priority Queue (Min-Heap):** Implemented to manage the nodes during the execution of Dijkstra’s algorithm, ensuring that the node with the lowest path cost is processed next.
- **Application:** This structure is instrumental in efficiently handling dynamically changing priorities, which occur as new paths are found and evaluated.

#### Level 4: Pathfinding Services
- **Path Calculation:** Develop functions to compute paths based on criteria like shortest distance, fastest time, and safest route using the algorithms mentioned above.
- **Waypoint Navigation:** Extend the pathfinding service to include waypoints, requiring paths to visit specific locations en route to the final destination.

### ✨ Detailed Tasks
- **Enhance Graph Traversal:** Implement and test BFS and DFS to ensure all reachable areas can be navigated.
- **Optimize Pathfinding:** Use Dijkstra’s algorithm to calculate optimal paths considering various costs such as distance, time, and safety from zombies.
- **Implement Complex Scenarios:** Integrate waypoint navigation and multi-criteria optimization for more realistic and challenging navigation tasks.

## 🔍 Testing
Comprehensive testing will be conducted using the provided GUI, simulating paths under different scenarios to validate the effectiveness and accuracy of the algorithms. Testing scenarios include:
- Basic pathfinding validation.
- Waypoint route testing.
- Performance testing under different graph sizes and complexities.

