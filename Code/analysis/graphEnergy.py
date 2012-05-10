#!/usr/bin/env python
import sys,numpy
import matplotlib.pyplot as plt
import re
#Plot a given lattice configuration
filename=sys.argv[1]
title=sys.argv[2]
energies=numpy.loadtxt(filename)
plt.plot(energies)
plt.xlabel("Step")
plt.ylabel("Energy")
plt.title(title)
plt.savefig("energy.png")
