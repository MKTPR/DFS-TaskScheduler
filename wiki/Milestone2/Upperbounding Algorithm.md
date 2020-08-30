# Greedy Algorithm
**Available node**: Node list with no incoming edge or every incoming nodes are scheduled

**Scheduled node**: Node list with nodes that are already scheduled to a processor


If there is an available node that need to be scheduled, schedule earliest finishing 

----------------------------------------------

- MakeNodeArrayOfNoIncomingEdge
  - Input: ArrayList<Node>
  - Output: ArrayList<Node>

- Find earliest finishing time
  - Input: Node, Processor
  - Output: int

- Find earliest finishing node and processor
  - Input: ArrayList<Node>
  - Output: Node, Processor

- Schedule node to processor
  - Input: Node, Processor

-----------------------------------------------------
```javascript
While (node list is not empty) {
- find available node list
- find earliest finishing node and processor out of available node list
- schedule earliest finishing node to a processor and add the node to a scheduled node 
 list and remove the node from a node list
}
```
------------------------------

input (constructor): nodesList, edgesList, processorList
