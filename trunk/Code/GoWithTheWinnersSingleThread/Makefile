JJ=javac
CP=../common:../SA:.


sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
run: GWTWmain.class
	java -cp $(CP) GWTWmain
  
clean:
	rm *.class
