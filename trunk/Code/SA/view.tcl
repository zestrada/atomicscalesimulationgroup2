mol delrep 0 top
mol representation VDW 0.250
mol color Name
mol material BrushedMetal
mol addrep top

set fp [open "connection.dat" r]; list
set file_data [split [read $fp] "]"]; list
set x 0
foreach line $file_data {
    set s [split $line ","]; list
    set val [ string map { "[" "" } $s ]
    set y 0
    set tmp1 [atomselect top "index $x"]
    set com1 [measure center $tmp1]
    for {set i 1} { $i < [llength $val] } {incr i} {
	set tmps [string trim [lindex $val $i]]
	if [string equal $tmps "true"] {  
	    set tmp2 [atomselect top "index $y"]
	    set com2 [measure center $tmp2]
	    set dist [vecdist $com1 $com2]
	    if [expr $dist < 8] {
		puts [concat $x " " $y " " $dist]
		draw line $com1 $com2
	    }
	    $tmp2 delete
	} 
	incr y
    }
    $tmp1 delete
    incr x
}
axes location off
render PostScript output.ps