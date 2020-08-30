# Parallel Scheduling

Used tools:
* Java threads

Our scheduler provides parallel scheduling option using threads which significantly enhances the speed of scheduling. 
This option can be enabled using `-p [number of threads]` argument in the command line. For example `graph.dot 3 -p 5` represents we will be using five threads in parallel to schedule graph.dot file into three processors.

## GUI

In the GUI of our program, the bottom left section shows if the scheduling is done sequentially or in parallel. The number of threads is represented on the bottom right side.

![Graph](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbaZm49%2FbtqHuh1JBjq%2FCtPKYoL97LppElXjHpSnkK%2Fimg.png)

----------------------------------------------

# Implementation

Below are the snippets of codes that is used to perform multi-threading
```java
            //Create worker thread. This code will only run if isParallel > 2
            for (int i = 0; i < isParallel; i++) {
                Thread run = new Thread(() -> {
                    int f = Integer.parseInt(Thread.currentThread().getName());
                        for (int j = start.get(f); j<end.get(f);j++) {

                            String top = Topologies.get(j);
                            ArrayList<String> _currentPath = new ArrayList<>(nodesList.size());
                            MakeTreeThreading tree = new MakeTreeThreading(nodesList, processorList, _numOfProcessors, _upperBound);
                            tree.makeTree(top, _nodeNumber, _currentPath);
                            /**
                             * Constantly update _upperBound with the newly found upperbound
                             * if it is lower than the current optimal.
                             */
                            if (_upperBound > tree.get_upperBound()) {
                                _upperBound = tree.get_upperBound();
                                //Comment out later
                                //System.out.println(_upperBound);
                                optimalProcessorList = new ArrayList<>(tree.get_processorList());
                                //Update Visualization Panel
                                if (isVisualise){
                                    TableView _TV = TableView.getInstance();
                                    _TV.changeData(optimalProcessorList,_upperBound);
                                    page.updateBestTime(_upperBound);
                                };
                            }
                            if (isVisualise) {
                                page.increaseTopSearched();
                            }
                        }
                });
                //sets name for individual threads so they can be used for indexing
                run.setName(i+"");
                threads.add(run);
                run.start();
            }
        }

```

```java
// waits for all threads to finish
        for (Thread i: threads){
            while( i.isAlive()){
                int k=0;
                k++;
            }
        }

```
