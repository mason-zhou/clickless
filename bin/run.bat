@echo off
echo.
echo [信息] 使用Jar命令运行Web工程。
echo.

cd %~dp0
cd ../clickless-admin-server/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% clickless-admin-server.jar

cd bin
pause