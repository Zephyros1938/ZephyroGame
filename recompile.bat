@echo off

rem Delete all .class files in the main directory and lib folder
for /r %%f in (*.class) do del "%%f"

for /r %%f in (*.java) do javac "%%f"

rem Compile all Java files, setting the classpath to include lib
javac Main.java -cp "./"

rem Run the program, setting the classpath and native library path to lib
java Main
