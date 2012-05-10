#!/usr/bin/env python
import sys,numpy
import matplotlib.pyplot as plt
import re
#plot energy vs. time block
filename=sys.argv[1]
#remember to use quotes around second argument if you want a title with spaces
title=sys.argv[2] 
energies=numpy.loadtxt(filename)
plt.plot(energies)
plt.xlabel("Step")
plt.ylabel("Energy")
plt.title(title)
plt.savefig("energy.png")
