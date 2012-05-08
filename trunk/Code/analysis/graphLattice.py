#!/usr/bin/env python
import sys,numpy
import matplotlib.pyplot as plt
import re
#Plot a given lattice configuration
floatStr=r"[-+]?[0-9]*\.?[0-9]+"
xre = re.compile(r"#x\s+(%s)\s+(%s)"%(floatStr,floatStr))
yre = re.compile(r"#y\s+(%s)\s+(%s)"%(floatStr,floatStr))
xlim=numpy.empty((2))
ylim=numpy.empty((2))
for line in open(sys.argv[1]):
  m = xre.match(line)
  if m>0:
    xlim[0]=float(m.group(1))
    xlim[1]=float(m.group(2))
  m = yre.match(line)
  if m>0:
    ylim[0]=float(m.group(1))
    ylim[1]=float(m.group(2))

print xlim
print ylim
filename=sys.argv[1]
points=numpy.loadtxt(filename)
points = points.transpose()
plt.plot(points[0],points[1],marker="o",ls="")
plt.xlim(xlim)
plt.ylim(ylim)
plt.show()
