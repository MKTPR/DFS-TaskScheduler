# project-1-team-7-project-1
The program visualisation only works for java 8 environments

![GUI](https://github.com/SoftEng306-2020/project-1-team-7-project-1/blob/master/wiki/Images/118656213_822952881576263_2620327502899988565_n.png)
## Team Members
  - Blake Hattingh
  - Donghyun Lee
  - Gurpreet Singh
  - Jason Ko
  - Maric Kim
  
## Build Instructions
1. Download or Clone this Repository.
2. Import the project into IDE - Build with Maven
3. Run from the IDE, or run the JAR file in the release with the instructions below.


### Run Instructions

To be implemented in Java (compatible with Java 1.8).
The images folder (included in the zip) and the input .dot file needs to be in the same folder as the .jar file.

From command line,
  - `java -jar scheduler.jar INPUT.dot P [OPTION]`
    - INPUT.dot: a task graph with integer weights in dot format
    - P: number of processors to schedule the INPUT graph on
    - OPTION:
      - -`p N`: user N cores for execution in parallel (default is sequential)
      - `-v`: visualise the search
      - `-o OUTPUT` - file is named OUTPUT(default is INPUT-output.dot) 
- Example: `java -jar scheduler.jar digraph.dot 2 -p 1 -v -o digraphOutput`
