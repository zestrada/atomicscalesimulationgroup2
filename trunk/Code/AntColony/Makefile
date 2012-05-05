JJ=javac 
CP=../common:.

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
run: ACOMain.class
	java -cp $(CP) ACOMain 
  
clean:
	rm *.class
