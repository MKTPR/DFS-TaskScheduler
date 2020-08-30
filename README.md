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

## Run Instructions
From command line,
  - java -jar scheduler.jar INPUT.dot P [OPTION]
    - INPUT.dot: a task graph with integer weights in dot format
    - P: number of processors to schedule the INPUT graph on
    - Optional:
      - -p N: user N cores for execution in parallel (default is sequential)
      - -v: visualise the search: only works for java 8 environment
