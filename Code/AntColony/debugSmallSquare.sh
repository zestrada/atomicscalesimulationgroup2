#!/bin/bash
alpha=1.0
beta=1.0
evap=0.95
jdb -classpath .:../common ACOMain ../analysis/smallsquare.dat "$alpha $beta $evap"
