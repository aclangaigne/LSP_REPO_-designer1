@echo off
setlocal
if not exist out mkdir out
javac -d out src\org\howard\edu\lsp\assignment2\ETLPipeline.java
if %errorlevel% neq 0 exit /b %errorlevel%
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline
endlocal
