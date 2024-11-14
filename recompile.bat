@echo on

:: Get the directory of this batch file (the project directory)
set SCRIPT_DIR=.
set PROJECT_DIR=%SCRIPT_DIR%

:: Navigate to the project directory
cd /d "%PROJECT_DIR%"

:: Set paths for Java and the library folder
set JAVA_PATH=%JAVA_HOME%
set LIBRARY_PATH=%PROJECT_DIR%\lib
set JAR_DIR=%PROJECT_DIR%\jar

set JAR_FILE="%JAR_DIR%\Main.jar"

:: Ensure the JAR directory exists
if not exist "%JAR_DIR%" (
    mkdir "%JAR_DIR%"
)

:: Compile the Java files from lib
echo: 
echo Compiling "%PROJECT_DIR%\src\lib files..."
for /r "%PROJECT_DIR%\src\lib" %%f in (*.java) do (
    echo Compiling %%f...
    javac -d "%PROJECT_DIR%\bin" "%%f" -cp ".;%PROJECT_DIR%\src\lib;%PROJECT_DIR%\src\lib\math"
)
echo: 
echo Compiling "%PROJECT_DIR%\src\game files..."
for /r "%PROJECT_DIR%\src\game" %%f in (*.java) do (
    echo Compiling %%f...
    javac -d "%PROJECT_DIR%\bin" "%%f" -cp ".;%LIBRARY_PATH%\*;%PROJECT_DIR%\src\lib;%PROJECT_DIR%\src\lib\math;%PROJECT_DIR%\lib"
)

:: Create the JAR file (use jar tool instead of java -jar)
echo Creating JAR file...
jar cmf "MANIFEST.MF" "%JAR_FILE%" -C "%PROJECT_DIR%\bin" .

:: Run the application with the required options
echo Running application...
java -Djava.library.path=".\lib\" ^
-cp ".\lib\" ^
-jar "%JAR_FILE%" 
