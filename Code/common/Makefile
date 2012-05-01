JJ=javac

TestCommon.class : TSPCommon.class

TSPCommon.class : TSPCommon.java
	$(JJ) TSPCommon.java

TestCommon.class : TestCommon.java
	$(JJ) TestCommon.java


all:
	TestCommon.class

clean:
	rm *.class
