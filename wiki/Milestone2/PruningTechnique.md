# Pruning Technique

Performing a good pruning technique is essential when it comes to speed of the algorithm.
Fortunately, our pruning technique was able to enhance the speed of the algorithm.

This is how our pruning technique works:

1. Run [Upperbounding Algorithm](https://github.com/SoftEng306-2020/project-1-team-7-project-1/wiki/Upperbounding-Algorithm) to find the upper-bound of the algorithm
2. Use DFS to tranvel through each topological order.
3. Before expanding every node, compare the current execution time with the upper-bound
4. Expand the graph only if the current execution time is less than the upper-bound. If the current execution time is larger, prune the current node and back trace to the parent node in DFS manner.




----------------------------------------------

# Implementation

```java
if (duration <= upperBound){
    expand children;
}else {
    back track to parent node;
    explore another child;
}
```
