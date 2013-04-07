@echo off
@title EasyJF 代码自动生成引擎
@color a
@rem by easyjf williamraym 22:31 2007-7-30
@rem if "%OS%"=="Windows_NT" SETLOCAL
:init
@set target=..\src\main\webapp\WEB-INF\classes
@if not exist "%target%" (
	@rem md "%target%"
)
@rem set lib=%cd%\..\lib
@set lib=%cd%\..\src\main\webapp\WEB-INF\lib
@if "%JAVA_HOME%"=="" goto nojava
@if not exist "%lib%\easydbo-0.9.1.jar" (
	@rem call build maven-jar
)
@goto setEnv
@goto end

:setEnv
@set CLASSPATH=%JAVA_HOME%\lib;.
@for /f "delims=" %%a in ('dir %lib% /b /a-d') do call :processLib %%a
@goto :process

@:processLib
@if "%CLASSPATH%"=="" set CLASSPATH=%lib%\%1& goto :end
@set CLASSPATH=%CLASSPATH%;%lib%\%1
goto :end

:process
@set CLASSPATH=%CLASSPATH%;%lib%\..\classes;
@rem echo %CLASSPATH%
goto end

:nojava
@echo 在您的操作系统上没有安装JAVA运行环境，请先设置JAVA_HOME环境变量或安装JDK
goto end


:end
@rem if "%OS%"=="Windows_NT" ENDLOCAL