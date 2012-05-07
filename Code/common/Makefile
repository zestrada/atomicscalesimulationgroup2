JJ=javac 
CP=.
JUNIT=/usr/share/java/junit4.jar

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
TestCommon.class : 
	echo ""

test:
	$(JJ) -cp $(CP):$(JUNIT) TestCommon.java
	java -cp $(CP):$(JUNIT) junit.textui.TestRunner TestCommon
  
clean:
	rm *.class
