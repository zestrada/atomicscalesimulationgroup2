mol delrep 0 top
mol representation VDW 0.250
mol color Name
mol material BrushedMetal
mol addrep top
color Display Background white
display rendermode GLSL
display projection orthographic
set fp [open "connection2000000.dat" r]; list
set file_data [split [read $fp] "]"]; list
set x 0
#set vertex ""
set numPart 0
foreach line $file_data {
    set s [split $line ","]; list
    set val [ string map { "[" "" } $s ]
    set y 0
    set tmp1 [atomselect top "index $x"]
    set com1 [measure center $tmp1]
    set numPart [llength $val]
    for {set i 1} { $i < $numPart } {incr i} {
	set tmpVertexCount 0
	set tmps [string trim [lindex $val $i]]
	if [string equal $tmps "true"] {  
	    set tmp2 [atomselect top "index $y"]
	    set com2 [measure center $tmp2]
	    set dist [vecdist $com1 $com2]
	    if [expr $dist < 10000] {
		#puts [concat $x " " $y " " $dist]
		draw line $com1 $com2
		#incr tmpVertexCount
	    }
	    $tmp2 delete
	} 
	incr y
	#lappend $vertex $tmpVertexCount
    }
    $tmp1 delete
    incr x
}
#for {set i 0} { $i < $numPart } {incr i} {
#    puts [lindex $vertex $i]
#}
axes location off
render PostScript output.ps