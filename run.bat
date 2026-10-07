@echo off
title Book Exchange Portal
echo ========================================
echo    Book Exchange Portal - Starting...
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot
set MVN=%USERPROFILE%\.maven\maven-3.9.15\bin\mvn.cmd

echo [1/2] Building project...
echo.

call "%MVN%" jetty:run -f "%~dp0pom.xml"

echo.
echo Server stopped.
pause
