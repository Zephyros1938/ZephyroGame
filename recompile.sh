find . -type f -name "*.class" -delete

ls -aR . | grep .class

ignore=$(javac Main.java)

java Main