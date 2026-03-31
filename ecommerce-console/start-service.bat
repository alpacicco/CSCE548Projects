@echo off
REM Quick Start Script for CSCE 548 Project 2
REM This script helps you run the REST API service and test it

echo ======================================================
echo   CSCE 548 Project 2 - Quick Start
echo ======================================================
echo.

echo Checking prerequisites...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo [OK] Java is installed
echo.

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo [OK] Maven is installed
echo.

echo ======================================================
echo   Step 1: Building the project
echo ======================================================
echo.

call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Build failed
    echo Please check the error messages above
    pause
    exit /b 1
)

echo.
echo [OK] Build successful!
echo.

echo ======================================================
echo   Step 2: Starting the REST API Service
echo ======================================================
echo.
echo The service will start on http://localhost:8080
echo.
echo IMPORTANT: Keep this window open while testing
echo You will run the tester in a DIFFERENT terminal window
echo.
echo Press Ctrl+C to stop the service when done
echo.

pause

echo Starting service...
echo.

call mvn spring-boot:run
