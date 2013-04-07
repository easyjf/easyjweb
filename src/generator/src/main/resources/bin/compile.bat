@echo off
title EasyJF
color a
rem by easyjf williamraym 15:22 2007-7-31
if "%OS%"=="Windows_NT" SETLOCAL
:init
if "%JAVA_HOME%"=="" goto nojava
goto main
goto eof

:main
@echo 正在编译 %1
"%JAVA_HOME%\bin\javac.exe" -cp ../src/main/webapp/WEB-INF/lib/jaxen-1.1-beta-4.jar;../src/main/webapp/WEB-INF/lib/easydbo-0.9.1.jar;../src/main/webapp/WEB-INF/lib/persistence-api-1.0.jar;../src/main/webapp/WEB-INF/lib/dom4j-1.6.1.jar;../src/main/webapp/WEB-INF/lib/log4j-1.2.14.jar;../src/main/webapp/WEB-INF/lib/commons-collections-1.2.jar;../lib/velocity-1.4.jar;../lib/derbynet-10.2.2.0.jar;../lib/derby-10.2.2.0.jar;../lib/derbyclient-10.2.2.0.jar;../lib/jetty-util-6.1.4.jar;../lib/jetty-6.1.4.jar;../lib/org.mortbay.jmx-5.1.9.jar;../lib/servlet-api-2.5-6.1.4.jar;../src/main/java;../lib/;../src/main/webapp/WEB-INF/lib;../src/main/webapp/WEB-INF/classes -source 1.5 -target 1.5 -encoding UTF-8 -d ../src/main/webapp/WEB-INF/classes %1
goto eof

:nojava
echo 在您的操作系统上没有安装JAVA运行环境，请先设置JAVA_HOME环境变量或安装JDK
goto eof

:eof
rem @exit

if "%OS%"=="Windows_NT" ENDLOCAL
