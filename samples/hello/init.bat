@echo off
title EasyJF
color a
rem by WilliamRaym 16:46 2006-6-15
rem by WilliamRaym 15:18 2007-8-6
rem by WilliamRaym 13:13 2007-12-19
if "%OS%"=="Windows_NT" SETLOCAL
:main
@rem copy the easyjweb jars to the target dir
@copy "..\..\lib\easyjweb-core-*.jar" "src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\..\lib\required\*.jar" "src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\..\bin\lib\build\*.jar" "bin\lib\build\" >nul 2>nul
@copy "..\..\bin\*.bat" "bin\" >nul 2>nul
goto eof
:eof
@echo 成功完成。
@rem pause
if "%OS%"=="Windows_NT" ENDLOCAL
