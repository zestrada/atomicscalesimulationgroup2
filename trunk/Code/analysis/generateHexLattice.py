#!/usr/bin/env python
import sys,numpy
import matplotlib.pyplot as plt
#Generate a Honeycomb Lattice a la Graphene
#numx, numy refer to copies of 4-atom unit cells
numx=5
numy=5
filename="honeycomb.dat"

#C-C bond length, lattice constant from graphene
a0=1.42
d0=2.46

#Use flattened 2D array
#This has a lot more hardcoded things than I would like, but it works
xpoints=numpy.zeros((numx*numy*4))
ypoints=numpy.zeros((numy*numx*4))
numpoints=0
for row in range(numx):
  for col in range(numy):
    #translate the whole thing by
    transx = (row-numx/2)*d0
    transy = (d0+a0)*(col-numy/2)
    xpoints[numpoints+0] = transx
    xpoints[numpoints+1] = transx
    xpoints[numpoints+2] = d0/2+transx
    xpoints[numpoints+3] = d0/2+transx
    ypoints[numpoints+0] = (d0/2) + transy
    ypoints[numpoints+1] = -(d0/2) + transy
    ypoints[numpoints+2] = (a0/2) + transy
    ypoints[numpoints+3] = (-a0/2) + transy
    numpoints+=4
outfile=open(filename,"w")
for atom in range(numpoints):
  print "%g %g"%(xpoints[atom],ypoints[atom])
  outfile.write("%g %g\n"%(xpoints[atom],ypoints[atom]))
plt.plot(xpoints,ypoints,marker="o",ls="")
print "Output written to %s" % filename
plt.show()
