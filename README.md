# Webserver
Small Webserver Implementation. Do not use, I'm pretty sure it has a lot of security issues.
Use the Standalone version to use it out of the box or use the Core version to use it as Dependency in other Projects.

## Features
- static content hosting
- multithreaded implementation
- overrideable [request handlers](HttpServer-Core/README.md#requesthandlers--mappings)
- easy configuration
- logging
- hot reloading in developer mode (a bit buggy)

## Usage

The config is written in config.yml which should, but doesn't need to, be in the same directory as the webserver.jar  
An example for the structure is in the var directory in this project. Make sure to place the standalone-jar into this dir before trying.

```yml
listen: 8080
root: C:\Develop\Folder\HttpServer\var\www\html\
index: index.html
error_logs: \logs\
error_page: error.html
dev: false
```

start the server with:
```java
java -jar [YourJarName].jar -f my/path/to/my/epic/config/file
```

## Building locally
To build locally use this command. It will generate the standalone and the core version in their modules
```shell
git clone https://github.com/AronBA/Webserver.git
cd Webserver
mvn clean install
```




