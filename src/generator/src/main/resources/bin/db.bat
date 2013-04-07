@echo off
title EasyJF
color a
rem by easyjf williamraym 17:12 2007-7-30
if "%OS%"=="Windows_NT" SETLOCAL
:init
if "%JAVA_HOME%"=="" goto nojava
goto main
goto eof

:main
"%JAVA_HOME%\bin\java.exe" -cp ../lib/derbynet-10.2.2.0.jar;../lib/derby-10.2.2.0.jar;../lib/derbyclient-10.2.2.0.jar;../lib/jetty-util-6.1.4.jar;../lib/jetty-6.1.4.jar;../lib/org.mortbay.jmx-5.1.9.jar;../lib/servlet-api-2.5-6.1.4.jar;../src/main/webapp/WEB-INF/classes com.easyjf.server.DerbyServer ../database
goto eof

:nojava
echo 在您的操作系统上没有安装JAVA运行环境，请先设置JAVA_HOME环境变量或安装JDK
goto eof

:eof
@pause

if "%OS%"=="Windows_NT" ENDLOCAL
