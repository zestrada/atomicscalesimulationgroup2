JJ=javac
CP=../common:.

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
run: GAmain.class
	java -cp $(CP) GAmain
  
runVerbose: GAmain.class
	java -verbose -cp $(CP) GAmain

clean:
	rm *.class
