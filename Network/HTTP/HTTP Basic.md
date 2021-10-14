# 一、基础



## 1.1 定义

- 超文本传输协议(HyperText Transfer Protocol，缩写：HTTP)是一种**应用层协议**。HTTP是万维网的数据通信的基础。







## 1.2 版本/时代

- http1.0: client向server发送请求后获取资源后，连接会关闭，**每次请求都需要重新建立连接**
- http2.0: 在**一个TCP连接上**可以**传送多个HTTP请求和响应**，减少了建立和关闭连接的消耗和延迟，**在HTTP1.1中默认开启Connection： keep-alive**(所有请求都是单线程串行的，之后的请求需要等待之前的请求返回结果)



HTTP和HTTPS的异同

1. HTTPS协议需要CA证书
2. HTTP建立在TCP协议之上，传输的数据是明文。HTTPS建立在SSL/TLS加密层上，SSL/TLS又建立在TCP上，数据经过加密
3. HTTP端口为80，HTTPS为443
4. HTTPS解决了ISP劫持的问题







## 1.3 请求

Eg to www.baidu.com:

通用请求行:

```java
Request URL: https://www.baidu.com/
Request Method: GET
Status Code: 200 OK													// 状态码
Remote Address: 127.0.0.1:7890						// 127.0.0.1是localhost，7890是clash的默认端口号
```

- 这里使用了clash X接管了系统代理，没有代理则如下:

```java
Request URL: https://www.baidu.com/
Request Method: GET
Status Code: 200 OK
Remote Address: 112.80.248.76:443
```

请求头/消息头:

```java
Accept: text/html
Accept-Encoding: gzip, deflate, br
Accept-Language: en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7
Cache-Control: max-age=0
Connection: keep-alive
```







### 1) 请求行

在通用的请求头中:

```java
Request URL: https://www.baidu.com/
Request Method: GET
```

- 请求行中的**请求方式**:Get
- 其他的请求方式: Get, Post, HEAD, DELETE...
  - Post: 请求携带的数据少，有大小限制，会在browser的地址栏显示数据，不安全，但高效(因为请求数据量有限)
  - Get: 请求携带的数据到，没有大小限制，不会在browser的地址栏显示数据，安全，但不高效(现在不成问题了)



### 2) 消息头

```java
Accept: text/html					// 告知浏览器支持的类型
Accept-Encoding: gzip, deflate, br		// 告知浏览器支持的编码格式
Accept-Language: en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7		// 告知浏览器支持的语言
Cache-Control: max-age=0			// 缓存控制
Connection: keep-alive				// 保持连接
```











## 1.4 响应

Eg from www.baidu.com

```java
Cache-Control: private					// 缓存控制
Connection: keep-alive					// 保持连接
Content-Encoding: gzip					// 编码
Content-Type: text/html;charset=utf-8				// 资源类型
```





### 1) 响应体

```java
Cache-Control: private
Connection: keep-alive
Content-Encoding: gzip
Content-Type: text/html;charset=utf-8
Refresh:				// 告诉client多久刷新一次
Location:				// 让网页重新定位
```







### 2) 响应状态码

- 2XX: 请求成功响应
- 3XX: 请求重定向
- 4XX: 找不到资源
- 5XX: 服务器代码错误
  - 502: 网关错误
  - 503: 服务器不可用





