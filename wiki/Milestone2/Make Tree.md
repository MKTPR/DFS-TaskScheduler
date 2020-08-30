# MakeTreeThreading

## Constructor
Responsible for the DFS algorithm
  - Parameters:
    - Node List: ArrayList of Node, stores the information retrieved from the main class
    - Processor List: ArrayList of Processor, stores the information retrieved from the main class
    - Number of Processors: Number of Processors
    - Upper Bound: Stores the duration for the optimal solution for scheduling nodes

## Implementation
Depth First Search by looping through all node and processor's combination  with following the topological order, use the recursive call to make sure that loops through all the nodes in the node list. Update the optimal solution whenever new optimal path have been found.

