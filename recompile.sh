find . -type f -name "*.class" -delete

ls -aR . | grep .class

javac Main.java >/dev/null 2>&1

find -name "*.java" > ./sources.txt

java Main