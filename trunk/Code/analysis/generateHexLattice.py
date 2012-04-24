#!/usr/bin/env python
import sys,numpy
#Generate a Honeycomb Lattice a la Graphene
#numx, numy refer to copies of 4-atom unit cells
numx=8
numy=6
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

#positions of most extreme atoms
xmax = (d0/2)+((numx-1)-numx/2)*d0
xmin = -(numx/2)*d0
ymax = (d0/2)+(d0+a0)*((numy-1)-numy/2)
ymin = -(d0/2)+(d0+a0)*(-numy/2)

#Now, take those positions and calculate the cell boundaries such that we
#have a perfect lattice with PBC
xmax+= d0/4
xmin-= d0/4
ymax+= a0/2
ymin-= a0/2

outfile=open(filename,"w")
outfile.write("#npart %d\n"%numpoints)
outfile.write("#nvert 3\n")
outfile.write("#x %g %g\n"%(xmin,xmax))
outfile.write("#y %g %g\n"%(ymin,ymax))
for atom in range(numpoints):
  #print "%g %g"%(xpoints[atom],ypoints[atom])
  outfile.write("%g %g\n"%(xpoints[atom],ypoints[atom]))
print "Output written to %s" % filename
