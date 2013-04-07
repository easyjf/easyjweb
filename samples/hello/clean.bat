@echo off
title EasyJF
color a
rem by WilliamRaym 16:46 2006-6-15
rem by WilliamRaym 15:18 2007-8-6
rem by WilliamRaym 13:13 2007-12-19
if "%OS%"=="Windows_NT" SETLOCAL
:main
@del "src\main\webapp\WEB-INF\lib\*.*" /q
@del "bin\lib\build\*.*" /q
@del "bin\*.bat" /q
goto eof
:eof
@echo 成功完成。
@rem pause
if "%OS%"=="Windows_NT" ENDLOCAL
