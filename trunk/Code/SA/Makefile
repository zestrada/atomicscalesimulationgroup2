JJ=javac
CP=../common:.

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	$(JJ) -cp $(CP) $<
  
run: SAmain.class
	java -cp $(CP) SAmain
  
clean:
	rm *.class
