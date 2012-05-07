JJ=javac 
CP=../common:.
JUNIT=/usr/share/java/junit4.jar

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
TestACO.class : 
	touch TestACO.java

run: ACOMain.class
	java -cp $(CP) ACOMain 

test: Ant.class TestACO.java
	$(JJ) -cp $(CP):$(JUNIT) TestACO.java
	java -cp $(CP):$(JUNIT) junit.textui.TestRunner TestACO
  
clean:
	rm *.class
