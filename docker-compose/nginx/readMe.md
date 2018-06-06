## 1. docker-compose

- step 1  copy default config file from docker contai
```
$ docker run --name tmp-nginx-container -d nginx
$ docker cp tmp-nginx-container:/etc/nginx/nginx.conf /host/path/nginx.conf
$ docker cp tmp-nginx-container:/etc/nginx/conf.d /host/path/conf.d
$ docker rm -f tmp-nginx-container  
```
- step 2
```yml
version: '2'

services:
  nginx:
    container_name: nginx
    # nginx -v 1.13.12
    image: nginx:latest
    volumes:
      - /usr/local/nginx/conf.d:/etc/nginx/conf.d
      - /usr/local/nginx/nginx.conf:/etc/nignx/nginx.conf
      - /usr/local/nginx/html:/usr/share/nginx/html
      - /usr/local/nginx/log:/var/log/nginx
    ports:
      - 8090:80
    environment:
      - NGINX_HOST=192.168.1.21
      - NGINX_PORT=80
``` 

nginx2alpine
```
version: '2.0'

services:
  nginx2alpine:
    restart: always
    image: nginx:1.11.6-alpine
    ports:
      - 8070:80
      #- 80:80
      - 443:443
    volumes:
      - /usr/local/nginx.bak/conf.d:/etc/nginx/conf.d
      - /usr/local/nginx.bak/nginx.conf:/etc/nginx/nginx.conf
      - /usr/local/nginx.bak/log:/var/log/nginx
      - /usr/local/nginx.bak/html:/var/www
     # - /etc/letsencrypt:/etc/letsencrypt
```
alpine
```
./nginx -s reload
```

启动
`docker-compose -f nginx-docker-compose.yml up -d`  
`-d` 后台启动  


## 2. location
[refer to](https://blog.csdn.net/ZHangFFYY/article/details/78494637)  
[beginners_guide](http://nginx.org/en/docs/beginners_guide.html?spm=5176.1972344.1.36.3cdf5aaa6nBg6M#conf_structure)   
**next time you should read slowly and times**  

这时候 要访问第一个网页 就是 IP/www1   
要访问第二的网页就是 IP/www2   
关于alias和root的区别：   
root和alias是系统文件路径的设置。   
root用来设置根目录，而alias用来重置当前文件的目录。  
```linux
location /img/ {
    alias /var/www/image/;
}
#若按照上述配置的话，则访问/img/目录里面的文件时，ningx会自动去/var/www/image/目录找文件
location /img/ {
    root /var/www/image;
}
#若按照这种配置的话，则访问/img/目录下的文件时，nginx会去/var/www/image/img/目录下找文件。
```

l2 : http://192.168.1.21:8090/test2/test.html  
l1 : http://192.168.1.21:8090/test/test.html  
l1 and l2 is the same view
```linux
location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }
    # l2
    location /test2/ {
       alias  /usr/share/nginx/html/test/;
    }
    # l1
    location /test/ {
       root  /usr/share/nginx/html/;
    }
```

## 3. frequently used command
./nginx -t
> nginx: the configuration file /etc/nginx/nginx.conf syntax is ok  
nginx: configuration file /etc/nginx/nginx.conf test is successful  
