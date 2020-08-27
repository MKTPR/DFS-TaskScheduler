**Graph**: program → G = (V, E, w, c). Must be DAG (should not be any dependency cycle.

**V**: set of tasks n

**n**: task node

**Edge**: dependency between the tasks (in order to complete C, A must be completed)

**w(n)**: execution time of task n (weight of node)

**c(eij)**: remote communication cost between tasks ni and nj. Only relevant when two tasks are executed in different processors. c = 0 if the tasks are performed by the same processor. (weight of edge)

**ts(n)**: start time of task n

**proc(n)**: processor of task n

----------------------------------------------

- MakeNodeArrayOfNoIncomingEdge
  - Input: ArrayList<Edge>
  - Output: ArrayList<Node>

- Find earliest finishing time
  - Input: ArrayList<Node>, ArrayList<Processor>, ...
  - Output: ArrayList<Node>
- Find node with largest incoming edge
  - Input: 
  - Output: ArrayList<Node>
- FindLeftmostNode
  - Input:
  - Output: Node

-----------------------------------------------------
Given:
- a DAG graph G = (V,E,w,c)
- i = ( 0 < i where i is the number of processors)

```javascript
While (V is not empty) {

              L = List all n that can be scheduled (which has no incoming edge) MakeNodeArrayOfNoIncomingEdge()
              for(each n in L){ Find earliest finishing time()
	      record earliest finishing time nf
}

if ( Within L, there is more than 1 nf that is earliest ){
              Select n with largest incoming edge Find node with largest incoming edge()

	      if(incoming edges are the same weight for all nf){
		    Select the leftmost n FindLeftmostNode()
	      }
	      }else{
		Select nf
              }
	      Add selected n to corresponding Pi then remove the n from G

}
```
------------------------------
output: arraylist<Processor>

input (constructor): nodesList, edgesList, processorList

nodes with no end nodes included in the edgelist

[][][][][]n

[][][][][][]e

