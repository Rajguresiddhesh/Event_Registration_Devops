@REM Maven Wrapper Script for College Event Registration Platform
@REM Student: 23102A0003 Siddhesh Rajgure
@echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

set PORTABLE_MVN=%DIRNAME%..\tools\apache-maven-3.9.6\bin\mvn.cmd

if exist "%PORTABLE_MVN%" (
    call "%PORTABLE_MVN%" %*
    exit /b %ERRORLEVEL%
)

where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    call mvn %*
    exit /b %ERRORLEVEL%
)

echo [ERROR] Maven not found! Please ensure Apache Maven or Java is available.
exit /b 1
