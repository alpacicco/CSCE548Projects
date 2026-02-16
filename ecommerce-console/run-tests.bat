@echo off
REM Test Script for CSCE 548 Project 2
REM Run this AFTER starting the REST API service

echo ======================================================
echo   CSCE 548 Project 2 - Service Tester
echo ======================================================
echo.

echo IMPORTANT: Make sure the REST API service is running!
echo You should have started it with start-service.bat
echo.

pause

echo.
echo Running tests...
echo.

call mvn exec:java -Dexec.mainClass="com.ecommerce.client.ServiceTester"

echo.
echo ======================================================
echo   Testing Complete
echo ======================================================
echo.

pause
