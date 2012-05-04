JJ=javac 
CP=../common:.

Ant.class : Ant.java ../common/Surface.class ../common/Cell.class
	$(JJ) -cp $(CP) Ant.java

ACOMain.class: ACOMain.java Ant.class
  $(JJ) -cp $(CP) ACOMain.java

all:
	ACOMain.class

run: ACOMain.class
	java -cp $(CP) ACOMain 

clean:
	rm *.class
