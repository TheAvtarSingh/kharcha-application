@echo off

REM Check if Podman is installed
podman --version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo Podman is not installed. Checking for Chocolatey...
    choco --version >nul 2>&1
    IF %ERRORLEVEL% NEQ 0 (
        echo Chocolatey is not installed. Please install it from https://chocolatey.org/install
        exit /b
    )
    echo Installing Podman via Chocolatey...
    choco install podman -y
)

REM Run MySQL container with Podman
podman run --name mysql-container -e MYSQL_ROOT_PASSWORD=Avtar12345 -e MYSQL_DATABASE=kharcha_transactions -e MYSQL_USER=avtar -e MYSQL_PASSWORD=Avtar12345 -p 3307:3306 -d docker.io/library/mysql:8.0

REM Wait for the MySQL container to be up and running
echo Waiting for MySQL container to be ready...
:waitForContainer
podman ps | findstr mysql-container >nul
IF %ERRORLEVEL% NEQ 0 (
    timeout /t 5 >nul
    goto waitForContainer
)

REM Check if local port 3307 is up
echo Checking if port 3307 is up...
:waitForPort
netstat -an | findstr ":3307" | findstr "LISTENING" >nul
IF %ERRORLEVEL% NEQ 0 (
    timeout /t 5 >nul
    goto waitForPort
)

start cmd /k "mvn spring-boot:run -pl kharcha-eureka"

REM Wait for 20 seconds before starting services
echo Waiting for 20 seconds before starting services...
timeout /t 10 >nul

REM Start services
start cmd /k "mvn spring-boot:run -pl kharcha-gateway"
start cmd /k "mvn spring-boot:run -pl kharcha-transactions"
start cmd /k "mvn spring-boot:run -pl kharcha-accounts"
start cmd /k "mvn spring-boot:run -pl kharcha-user"