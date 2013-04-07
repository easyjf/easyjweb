@echo off
title EasyJF 代码自动生成引擎
color a
rem by easyjf williamraym 17:11 2007-7-30
if "%OS%"=="Windows_NT" SETLOCAL
:init
if "%JAVA_HOME%"=="" goto nojava
goto main
goto eof

:main
@echo 正在启动服务...
@start db.bat
@start web.bat


goto eof

:nojava
echo 在您的操作系统上没有安装JAVA运行环境，请先设置JAVA_HOME环境变量或安装JDK
goto eof

:eof
@rem pause

if "%OS%"=="Windows_NT" ENDLOCAL
