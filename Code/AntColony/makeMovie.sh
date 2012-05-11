#!/bin/bash
for i in `seq 0 230`
do
    sed -e "s/XXX/$i/g" < view.tcl > tmp1.tcl
    vmd surface.xyz < tmp1.tcl
done
