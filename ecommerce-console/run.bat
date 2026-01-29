@echo off
echo Starting E-Commerce Application...
cd /d "%~dp0"
mvn exec:java -Dexec.cleanupDaemonThreads=false
pause
