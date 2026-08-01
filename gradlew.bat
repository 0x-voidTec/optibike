@rem Set local directory to where this script is located
@setlocal

@rem Resolve the location of this script
@set "SCRIPT_DIR=%~dp0"
@set "SCRIPT_NAME=%~nx0"

@rem If the script is called with a relative path, we need to resolve it to an absolute path
@if not "%SCRIPT_DIR:~-1%"=="\" set "SCRIPT_DIR=%SCRIPT_DIR%\"

@rem Set APP_HOME to the parent directory of this script
@set "APP_HOME=%SCRIPT_DIR%.."

@rem Resolve any "." or ".." in the path
@for %%i in ("%APP_HOME%") do set "APP_HOME=%%~fi"

@rem Set default JVM options
@set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
@if defined JAVA_HOME goto findJavaFromJavaHome

@set JAVA_EXE=java.exe
@%JAVA_EXE% -version >NUL 2>&1
@if %ERRORLEVEL% equ 0 goto execute

echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
goto fail

:findJavaFromJavaHome
@set JAVA_HOME=%JAVA_HOME:"=%
@set JAVA_EXE=%JAVA_HOME%/bin/java.exe

@if exist "%JAVA_EXE%" goto execute

echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

:fail
exit /b 1

:execute
@rem Setup the command line
@set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% -Dorg.gradle.appname="gradlew" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

@endlocal
