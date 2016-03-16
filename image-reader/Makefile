all: compile jar

compile:
	rm -rf bin
	mkdir bin
	javac -source 1.8 -sourcepath src -d bin `find . -name "*.java" | grep -v tests`

jar:
	cd bin; jar cfe ../image-reader.jar fat12.App `find . -name "*.class"`
