# Graph Visualization

Used tools:
* GraphStream
* Java Swing

The `-v` option in the command line triggers graphical user interface when the program runs. As shown below, the input/output graph is represented with a visual component which consist of node(task) names, and edge(transmission) weight. This feature enables the user visually see their input `[name].dot` file with appropriate nodes and edges. The graph is freely movable using click-and-drag with mouse pointer. Fiddling around this feature adds some entertainment while the user waits for the program to finish.

![Graph](https://blog.kakaocdn.net/dn/bqmKL5/btqHwgIrJ45/hTrH3gf1xbsMPkpSuwaKk1/img.png)

## GraphStream

GraphStream is a library that handles the GUI of dynamics aspects of graphs. It provides very useful functionality such as modelling GUI version of graph. Our team utilized this library to provide the Graph Visualization functionality to our program. 

![Logo](https://upload.wikimedia.org/wikipedia/en/2/2e/GraphStream_Logo.png)


----------------------------------------------

# Implementation

Graph Visualization utilizes `CreateGraph` Class in the project.
The main methods that serves the purpose of the class are following:

`parseInputToGraph()' parses node list and edge list into objects that can be understood by GraphStream library.

`setGraphVisual()` utilizes GraphStream's stylesheet, `org.graphstream.ui.renderer`, to make the graph aesthetic.

`produceJPanel()` enable the graph to be mounted into separate GUI application (Java swing). This method returns a JPanel consists of the graph.
