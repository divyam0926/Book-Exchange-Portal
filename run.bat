@echo off
title Book Exchange Portal
echo ========================================
echo    Book Exchange Portal - Starting...
echo ========================================
echo.

set "JAVA_HOME=%USERPROFILE%\.jdk\jdk-25.0.4.1+1"
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo JDK 25 was not found at "%JAVA_HOME%".
    exit /b 1
)

set MVN=%USERPROFILE%\.maven\maven-3.9.15\bin\mvn.cmd

echo [1/2] Building project...
echo.

call "%MVN%" jetty:run -f "%~dp0pom.xml"

echo.
echo Server stopped.
pause
