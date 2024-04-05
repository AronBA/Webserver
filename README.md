# Webserver

Small Webserver Implementation. Do not use, I'm pretty sure it has a lot of security issues 

## Usage

The config is written in config.yml which should, but doesn't need to, be in the same directory as the webserver.jar

```yml
listen: 8080
root: C:\Develop\Folder\HttpServer\var\www\html\
index: index.html
error_logs: /var/log/error.log
error_page: error.html
dev: false
```

start the server with:
```java
java -jar webserver.jar -f my/path/to/my/epic/config/file
```

## Features
### Static Content Hosting
every file put into the root dir will be hosted on the webserver.

### Multitheaded implementation
blazingly fast and 100% thread safe !!11!!

### Overridable Request mappings
if used as a dependency the requesthandler can be easly overwritten

### Super easy to configure
It ain't much but it's honest work.

### 0% Test coverage
If it works it works

### hot reload
change your html and watch it reload in real time (only in dev mode).
Sometimes it crashes but don't worry it's very safe 😃
### planned Features:
- standalone and dependency version
- tests
- 
  
