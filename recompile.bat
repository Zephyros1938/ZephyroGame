@echo off

for /r %%f in (*.class) do del "%%f"

dir /s /b *.class

javac Main.java 

java Main
