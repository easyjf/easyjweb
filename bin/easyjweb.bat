@echo off
title EasyJF
color a
rem by WilliamRaym 11:54 2007-12-18
if "%OS%"=="Windows_NT" SETLOCAL
:init
if "%JAVA_HOME%"=="" goto nojava
goto main
goto eof

:main
if not exist ../src/core/src/main/java/com/easyjf/web/easyjf-web.xml (
@set ROOT=0
@call setenv.bat
) else (
@set ROOT=1

)

if "%1"=="war" goto war
if "%1"=="run" goto run
if "%1"=="crud" goto crud
if "%1"=="project" goto project

if "%1"=="" goto usage
goto eof


:usage
@echo 使用方法：
@echo easyjweb war							——打包当前工程成一个war包
@echo easyjweb run							——用Maven直接运行本工程
@echo easyjweb crud org.easyjf.domain.Example				——根据一个类来生成它的相关应用
@echo easyjweb crud  org.easyjf.domain.Example ../src/main/java/org/easyjf/domain/Example.java	——根据一个JAVA源文件来生成它的相关应用
@echo 以上两个crud只是crud的几种灵活使用方法，它的宗旨就是，给一个JAVA类，它会根据JAVA类生成相应的其它文件。
@echo.
@echo --------------------------------------
@echo 当目录为easyjweb的根目录时
@echo easyjweb project d:\workspace\myapp				——在d:\workspace\下生成一个名为myapp的简单MVC应用
@echo easyjweb project d:\workspace\myapp -ejs			——在d:\workspace\下生成一个名为myapp的应用
@echo 且项目为（Easyjweb + JPA + Spring）的结构
@echo easyjweb project d:\workspace\myapp -ejs -maven			——在d:\workspace\下生成一个名为myapp的托管于Maven的应用
@echo easyjweb project d:\workspace\myapp -ejs	-extjs		——生成基于EJS(EasyJWeb+JPA+Spring)构架及界面基于ExtJS的应用项目
@echo easyjweb project d:\workspace\myapp -ssh	-extjs		——生成基于SSH1(Struts1.X+Hibernate+Spring)构架及界面基于ExtJS的应用项目
@echo easyjweb project d:\workspace\myapp -ssh2	-extjs		——生成基于SSH2(Struts2.X+Hibernate+Spring)构架及界面基于ExtJS的应用项目
goto eof

:crud
@if "1"=="%ROOT%" @goto isEASYJWEBPROJECT
if "%2"=="" goto usage
@echo start crud %2
title 生成%2 CRUD
if not "%3"=="" @javac %3 -d ../src/main/webapp/WEB-INF/classes/ -encoding UTF-8
java com.easyjf.generator.Generator %2
goto eof

:project
if "%2"=="" goto usage
if "0"=="%ROOT%" goto notEASYJWEBPROJECT
rem ../src/core/src/main/java/com/easyjf/web/easyjf-web.xml goto notEASYJWEBPROJECT
if "%4"=="-maven" goto projectEJSMAVEN
if "%4"=="maven" goto projectEJSMAVEN
if "%4"=="-mvn" goto projectEJSMAVEN
if "%4"=="mvn" goto projectEJSMAVEN

if "%4"=="-extjs" goto projectEJSEXTJS
if "%4"=="extjs" goto projectEJSEXTJS


if "%3"=="-extjs" goto projectEXTJS
if "%3"=="extjs" goto projectEXTJS


if "%3"=="ejs" goto projectEJS
if "%3"=="-ejs" goto projectEJS

if "%3"=="ssh" goto projectSSHEXTJS
if "%3"=="-ssh" goto projectSSHEXTJS

if "%3"=="ssh2" goto projectSSH2EXTJS
if "%3"=="-ssh2" goto projectSSH2EXTJS

@echo 开始生成MINI项目%2
title 生成%2 MINI Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\mini" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\easyjweb-core-*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof

:projectEJS
if "%3"=="" goto usage
@echo 开始生成Easyjweb JPA Spring Project项目 %2
title 生成%2 Easyjweb JPA Spring Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\ejs" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@copy "..\lib\jpa\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\spring\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\other\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul


@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
rem @del "%2\src\main\webapp\WEB-INF\lib\servlet-api-2.5-6.1.4.jar" /q >nul 2>nul
rem @del "%2\pom.xml" /q
@rd "%2\4maven" /S /Q
@del "%2\ejs.launch" /q
@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof

:projectEJSMAVEN
if "%3"=="" goto usage
if "%4"=="" goto usage
@echo 开始生成Easyjweb JPA Spring Project项目，（使用MAVEN管理项目） %2
title 生成%2 Easyjweb JPA Spring Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\ejs" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem echo move "%2\4maven\.classpath" "%2\.classpath" /Y
@xcopy "%2\4maven\.classpath" "%2\.classpath" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem del "%2\4maven\.classpath" /q
@rd "%2\4maven" /S /Q
@rem copy the easyjweb jars to target dir
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul

@copy "*.bat" "%2\bin\" >nul 2>nul
@del  "%2\src\main\webapp\WEB-INF\lib\*.*" /q >nul 2>nul
@echo 成功完成，正在打开目标目录
@rem cd /d "%2"
@rem mvn eclipse:eclipse
@explorer "%2"
goto eof


:projectEJSEXTJS
if "%3"=="" goto usage
if "%3"=="-ssh2" goto projectSSH2EXTJS
if "%3"=="ssh2" goto projectSSH2EXTJS
if "%3"=="-ssh" goto projectSSHEXTJS
if "%3"=="ssh" goto projectSSHEXTJS
@echo 开始生成Easyjweb JPA Spring Project项目 %2
title 生成%2 Easyjweb JPA Spring Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\ejs" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@copy "..\lib\jpa\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\spring\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\other\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul


@xcopy "..\lib\extjs\ext-3.2" "%2\src\main\webapp\plugins\extjs\ext-3.2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
@copy "templates\extjs\*.*" "%2\templates\" >nul 2>nul

@xcopy "templates\extjs\easyjf\*.*" "%2\src\main\webapp\plugins\extjs\easyjf\" /E /C /F /I /Q /R /K /Y>nul 2>nul
rem @del "%2\src\main\webapp\WEB-INF\lib\servlet-api-2.5-6.1.4.jar" /q >nul 2>nul
rem @del "%2\pom.xml" /q

@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof


:projectSSH2EXTJS
if "%3"=="" goto usage
@echo 开始生成Easyjweb JPA Spring Project项目 %2
title 生成%2 Easyjweb JPA Spring Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\ejs" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@copy "..\lib\jpa\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\spring\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\other\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\struts2\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@xcopy "..\lib\extjs\ext-3.2" "%2\src\main\webapp\plugins\extjs\ext-3.2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
@copy "templates\extjs\*.*" "%2\templates\" >nul 2>nul
@xcopy "templates\extjs\easyjf\*.*" "%2\src\main\webapp\plugins\extjs\easyjf\" /E /C /F /I /Q /R /K /Y>nul 2>nul

@del "%2\src\main\webapp\WEB-INF\easyjf-web.xml" >nul 2>nul
@del "%2\src\main\webapp\WEB-INF\mvc.xml" >nul 2>nul

@xcopy "templates\struts2\*.*" "%2\" /E /C /F /I /Q /R /K /Y>nul 2>nul
rem @del "%2\src\main\webapp\WEB-INF\lib\servlet-api-2.5-6.1.4.jar" /q >nul 2>nul
rem @del "%2\pom.xml" /q

@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof

:projectSSHEXTJS
if "%3"=="" goto usage
@echo 开始生成Easyjweb JPA Spring Project项目 %2
title 生成%2 Easyjweb JPA Spring Project
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\ejs" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@copy "..\lib\jpa\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\spring\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\other\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\struts1\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul

@xcopy "..\lib\extjs\ext-3.2" "%2\src\main\webapp\plugins\extjs\ext-3.2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
@copy "templates\extjs\*.*" "%2\templates\" >nul 2>nul
@xcopy "templates\extjs\easyjf\*.*" "%2\src\main\webapp\plugins\extjs\easyjf\" /E /C /F /I /Q /R /K /Y>nul 2>nul

@del "%2\src\main\webapp\WEB-INF\easyjf-web.xml" >nul 2>nul
@del "%2\src\main\webapp\WEB-INF\mvc.xml" >nul 2>nul

@xcopy "templates\struts1\*.*" "%2\" /E /C /F /I /Q /R /K /Y>nul 2>nul
rem @del "%2\src\main\webapp\WEB-INF\lib\servlet-api-2.5-6.1.4.jar" /q >nul 2>nul
rem @del "%2\pom.xml" /q

@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof


:projectEXTJS
@echo 开始生成EXTJS项目%2
title 生成%2 MINI ProjectEXTJS
if exist "%2" @echo %2已存在了，为了安全起见，请先删除本目录或指定一个不存在的目录！
if exist "%2" goto eof
if not exist "%2" md "%2"
@xcopy "templates\mini" "%2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@rem copy the easyjweb jars to target dir
@copy "..\lib\easyjweb-core-*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@copy "..\lib\required\*.jar" "%2\src\main\webapp\WEB-INF\lib\" >nul 2>nul
@xcopy "..\lib\extjs\ext-3.2" "%2\src\main\webapp\plugins\extjs\ext-3.2" /E /C /F /I /Q /R /K /Y >nul 2>nul
@copy "lib\build\*.jar" "%2\bin\lib\build\" >nul 2>nul
@copy "*.bat" "%2\bin\" >nul 2>nul
@echo 成功完成，正在打开目标目录
@explorer "%2"
goto eof




:war
@if "1"=="%ROOT%" @goto isEASYJWEBPROJECT
@call build war
goto eof

:run
@if "1"=="%ROOT%" @goto isEASYJWEBPROJECT
@echo 开始运行本项目
cd ..
@mvn jetty:run
goto eof


:nojava
@echo 在您的操作系统上没有安装JAVA运行环境，请先设置JAVA_HOME环境变量或安装JDK
goto eof

:isEASYJWEBPROJECT
@echo 本项目是EasyJWeb项目，不能在此执行当前命令！
@echo.
@echo 此处可以执行的命令有：
@echo easyjweb project d:\workspace\myapp				——在d:\workspace\下生成一个名为myapp的简单MVC应用
@echo easyjweb project d:\workspace\myapp -ejs			——在d:\workspace\下生成一个名为myapp的应用
@echo 且项目为（Easyjweb + JPA + Spring）的结构
@echo easyjweb project d:\workspace\myapp -ejs -maven			——在d:\workspace\下生成一个名为myapp的托管于Maven的应用
goto eof

:notEASYJWEBPROJECT
@echo 本项目不是EasyJWeb项目，请在Easyjweb项目下执行本操作！
@echo.
@echo 此处可以执行的命令有：
@echo easyjweb war							——打包当前工程成一个war包
@echo easyjweb run							——用Maven直接运行本工程
@echo easyjweb crud org.easyjf.domain.Example				——根据一个类来生成它的相关应用
@echo easyjweb crud  org.easyjf.domain.Example ../src/main/java/org/easyjf/domain/Example.java	——根据一个JAVA源文件来生成它的相关应用
@echo 以上两个crud只是crud的几种灵活使用方法，它的宗旨就是，给一个JAVA类，它会根据JAVA类生成相应的其它文件。

:eof
@rem pause

if "%OS%"=="Windows_NT" ENDLOCAL
