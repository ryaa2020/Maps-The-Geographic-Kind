# Maps-The-Geographic-Kind


## Objectives

* Gain experience with graph algorithms.
* Work on a real-world problem.

## Description

My project, maps, utilizes edge weighted graphs to read a file containing location descriptions on a map and connect them by shortest distance or shortest time. Runs depth first search to find strong connections between vertices and Dijkstra’s algorithm to find the shortest path between a singular vertex with the rest of the vertices.

Most of us have lived somewhere where we need to drive or ride from one place to another on streets and freeways to get around. Take, for example, Southern California. Given that this is a heavily populated area, we also have to contend with traffic. If we attempt to drive on a local freeway during rush hour, we often experience traffic jams and long delays, requiring us to find alternative routes or simply put up with the traffic and wait.

Fortunately, technology offers at least some assistance. With ubiquitous wireless Internet connections, powerful devices embedded into cars and available in a mobile form, we have easy access to information that can help. Aside from providing the obvious ability to download traffic reports and maps on demand, these devices have gone a step further; given up-to-the-minute traffic information and with a little computing power, your device can actively aid you in finding the best way to get from one place to another, optimized not only for distance, but also for the shortest driving time given the current traffic conditions. Further, if all cars used such a system, as drivers were diverted around the scene of an accident, traffic conditions would change, and the advice offered by drivers' in-car or mobile devices would also change, optimally routing cars around a traffic jam by sending different cars down different alternate paths. This way, even the alternatives might flow as quickly as possible. (And, taking things a step further, Google has made a lot of recent progress on self-driving cars, which can optimize the traffic problem even further.)

Many of these features are already available on smartphones and in-car systems. And while there are a lot of different kinds of technology you need in order to build a system like this, the core of the problem is actually one that's familiar to us. For this project, I wrote a simplified version of an important piece of such a system: given a map of streets and freeways, along with a snapshot of the current traffic between points on the map, my program will be capable of finding the shortest distance or fastest route to get from one location on the map to another.

My program's main job is to discover the shortest distance or driving time between two locations. There's
no reason we couldn't think of locations as being any particular point on a street map (for example, any
valid street address, or even any valid GPS coordinate). For simplicity, though, we'll think of them as points
on the map in which decisions would need to be made, such as:

* The intersection of two or more streets.
* A point of a freeway at which there is an entrance and/or an exit.
