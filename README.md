# Assignment 2 — Heap Data Structures
**Pair 4 — Student B: Max-Heap Implementation**

---

##  Overview
This repository contains the implementation of a **Max-Heap** data structure as part of Assignment 2.  
It includes:
- A baseline **MaxHeap** implementation with operations:
    - `insert(x)`
    - `extractMax()`
    - `increaseKey(index, newValue)`
    - `peekMax()`
- A **PerformanceTracker** that measures:
    - number of comparisons
    - number of swaps
    - array accesses
- A **BenchmarkRunner** CLI tool to run performance tests on various input sizes and distributions, exporting results to CSV.
- A **JUnit 5 test suite** to validate correctness and edge cases.

---

##  Repository Structure
```
├── src
│ ├── main
│ │ ├── java
│ │ │ ├── algorithms
│ │ │ │ └── MaxHeap.java
│ │ │ ├── metrics
│ │ │ │ └── PerformanceTracker.java
│ │ │ └── cli
│ │ │ └── BenchmarkRunner.java
│ └── test
│ └── java
│ └── algorithms
│ └── MaxHeapTest.java
├── docs
│ ├── analysis-report.pdf # Individual analysis report (Student B)
│ ├── comparison-summary.md # Joint summary with Student A
│ └── performance-plots
│ └── maxheap-results.csv # Benchmark output
├── pom.xml
└── README.md
```
## Run Tests
Right-click MaxHeapTest → Run 'MaxHeapTest'

## Run Benchmark
Right-click BenchmarkRunner → Run 'BenchmarkRunner'

Add program arguments (example):
~~~
--n 10000 --dist random --out docs/performance-plots/maxheap-results.csv
~~~
With Maven:
~~~
mvn exec:java -Dexec.args="--n 10000 --dist random --out docs/performance-plots/maxheap-results.csv"
~~~

## Benchmark Results

Results are stored in: docs/performance-plots/maxheap-results.csv

CSV format:
```
timestamp,n,insertNs,extractNs,comparisons,swaps,arrayAccesses
```