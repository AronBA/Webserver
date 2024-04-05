# Webserver

Small Webserver Implementation

## usage

config with:
```yml
listen: 8080
root: C:\Develop\Folder\HttpServer\var\www\html\
index: index.html
error_logs: /var/log/error.log
error_page: error.html

```

start with:
```java
java -jar webserver.jar -f my/path/to/my/epic/config/file

```

## (planned) Features

- static content hosting
- easy to override request mappings
- multi threaded implementation
- no thread safety
- no perfomance
- no security
- setup script?
- config reader?
- standalone and dependency
  
