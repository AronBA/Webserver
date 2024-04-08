# Webserver

Small Webserver Implementation. Do not use, I'm pretty sure it has a lot of security issues.
Use the Standalone version to use it out of the box or use the Core version to use it as Dependency in other Projects.

## Building locally
To build locally use this command. It will generate the standalone and the core version in their modules
```shell
git clone https://github.com/AronBA/Webserver.git
cd Webserver
mvn clean install
```

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

## Features
- ### Static Content Hosting  
Every file put into the root dir will be hosted on the webserver.

- ### Multithreaded implementation
blazingly fast and 100% thread safe !!11!!

- ### Overridable Request mappings
if used as a dependency the request handler can be easily overwritten

- ### Super easy to configure
It ain't much but it's honest work.

- ### 0% Test coverage
If it works it works

- ### hot reload
change your html and watch it reload in real time (only in dev mode).
Sometimes it crashes but don't worry it's very safe 😃
  
