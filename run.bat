@echo off

call gradlew --no-daemon clean jmhJar
if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%

set ARGS=-Xss64m -jar benchmarks.jar -foe true -f 2

%JDK_11%\bin\java %ARGS% -o result.txt
