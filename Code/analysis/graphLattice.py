#!/usr/bin/env python
import sys,numpy
import matplotlib.pyplot as plt
#Plot a given lattice configuration

filename=sys.argv[1]
points=numpy.loadtxt(filename)
points = points.transpose()
plt.plot(points[0],points[1],marker="o",ls="")
plt.show()
