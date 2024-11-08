@echo off

for /r %%f in (*.class) do del "%%f"

dir /s /b *.class

javac Main.java >nul 2>&1

java Main
