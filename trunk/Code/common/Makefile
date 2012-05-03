JJ=javac 
CP=/usr/share/java/junit4.jar:.

TestCommon.class: Cell.class Surface.class

Cell.class : Cell.java
	$(JJ) -cp $(CP) Cell.java

Surface.class : Cell.class Surface.java
	$(JJ) -cp $(CP) Surface.java

TestCommon.class : TestCommon.java
	$(JJ) -cp $(CP) TestCommon.java

all:
	TestCommon.class

test: TestCommon.class
	java -cp $(CP) junit.textui.TestRunner TestCommon

clean:
	rm *.class
