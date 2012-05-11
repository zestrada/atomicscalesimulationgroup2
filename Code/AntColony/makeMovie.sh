#!/bin/bash
for i in `seq 0 9`
do
    sed -e "s/XXX/$i/g" < view.tcl > tmp.tcl
    vmd surface.xyz < tmp.tcl
done