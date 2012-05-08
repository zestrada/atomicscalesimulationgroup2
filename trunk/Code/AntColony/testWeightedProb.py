#!/usr/bin/env python
import numpy

#Number of steps
N=100000
probs=numpy.array([0.10,0.30,0.30,0.10,0.10,0.05,0.01,0.01,0.01,0.01,0.01])
hist=numpy.zeros(probs.shape)
step=0
while(step<N):
  step+=1
  temp=numpy.random.random()
  prob=0.0
  for i in range(len(probs)):
    prob+=probs[i] 
    if(prob>=temp):
      hist[i]+=1.0
      break

print hist/float(N)
