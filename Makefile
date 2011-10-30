JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Point.java \
        Shape.java \
				Node.java \
				Graph.java \
        TrapezoidalMap.java \
				TrapezoidLine.java \
				TrapezoidFace.java \
				TrapezoidPanel.java \
        TrapezoidalMapDriver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

