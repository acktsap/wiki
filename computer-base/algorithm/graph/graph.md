# Graph

- [Graph](#graph)
  - [What isGraph](#what-isgraph)
  - [Type of Graph](#type-of-graph)
    - [directed graph](#directed-graph)
    - [undirected graph](#undirected-graph)
    - [weighted graph](#weighted-graph)
    - [single graph](#single-graph)
    - [multigraph](#multigraph)
    - [unrooted graph](#unrooted-graph)
    - [biparite graph](#biparite-graph)
    - [DAG (Directed Acyclic Graph)](#dag-directed-acyclic-graph)

## What isGraph

Represents relation among objects by G(V, E); V : vertices, E : edges

## Type of Graph

### directed graph

O ------> O

### undirected graph

O ------- O

### weighted graph

O ---15---> O

### single graph

One edge

O ------- O

### multigraph

Multiple edges

O ====== O

### unrooted graph

Looks like a tree

### biparite graph

Edges exist among other groups

O	O	O	// group 1
| / | / |
O	O	O	// group 2

### DAG (Directed Acyclic Graph)

- Cannot go back(no cycle)
- If no direction, there may be a cycle...


 o Path of Graph

    Permutation of connected edges
    ex) (1, 2), (2, 3), (3, 4), (4, 5) -> 1-2-3-4-5

  - Simple path
        Path which passes each vertices at most once
        Most of path in present age means the simple path
        ex) 2-3-4-2-5 : simple path X


 o Implementation of the graph

  - adjacency list
    vector< list<int> > adjacents;	// each vertex stores its adjacent edges

    struct Edge {
        int vertex;
        int weight;
    };
    vector< list<Edge> > adjacents;	// with weight

    pros : multi-graph representable, O(|V|+|E|) in space complexity;
    cons : need exhausive search of adjacents edges to finding out
           whether two vertices are connected or not;

  - adjacency matrix
    // adjacent[i][j] == true -> ∃ edge between vertex i and j;
    vector< vector<bool> > adjacents;

    // adjacent[i][j] stands for its weight, -1 means no edge;
    vector< vector<int> > adjacents;	// with weight

    pros : O(1) in finding out whether two vertices are connected or not;
    cons : O(|V|^2) in space complexity;

  - What to use
    If |E| << |V|^2 (sparse graph), use adjacency list
    If |E| ∝ |V|^2 (dense graph), use adjacency matrix

    ∴ The important thing is the time complexity!! speed!!

  - Tip
    if a start number of vertex is 1, make matrix whose size is # of vertex + 1
    eg. if vertex : 1, 2, 3 -> vector<bool> matrix(4, false);


 o Graph & real world problems[feel it]

  - Analysis of stability of railway network
    station : vertex, rail : edge
    -> which station is hub?

  - Analysis of a social network
    human : vertex, friends : edge
    -> How many times does it takes to get to obama by step-by-step?

  - Analysis of a computer network
    router : vertex, connection : edge
    -> Which path is the optimal path?

  - Draw with one path
    A drawing consists of vertex and edge
    -> Can we draw it just by a one brush?

  - FX Transaction
    1 dollar -> eur -> jpy -> more than 1 dollor : arbitrage
    How can we detect the arbitrage?
    -> currency :vertex, exchange : weighted edge
       if ∃ arbitrage,
       ∏ weight(e) > 1 for any cycle C(e ∈ C) (⇔ ∑ log(weight(e)) < 0)
       Just finding out the existence of cycle whose sum of log of weights is negative;

  - To do list
    put on -> going out -> washing -> detergent -> put on
    ...??? What the fucking dependency!!!
    How can we solve the problem?;
    -> To do : vertex, dependency : edge;

  - 15-puzzle
    * * * *		We can move the tile(numbered by 1 ~ 15);
    *   * *		Can we find the optimal way to get to the initial state?
    * * * *		-> current state : vertex, can be moved in a movement : edge
    * * * *		-> shortest path problem!!;

  - Question
    Does everything can be represented in graph?
