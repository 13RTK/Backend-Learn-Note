# 一、Socket技术



## 1. 建立Socket连接

- Socket是系统提供的通信技术，其支持TCP和UDP。Java对其底层进行了一套封装
- 要实现Socket通信，我们必须创建一个发送者(client)和接收者(server)，我们需要提前启动服务器，让服务器被动监听



### 1.1 server的创建

- 创建一个ServerSocket对象，调用其构造方法，其中传入服务器绑定的端口号:

```java
ServerSocket server = new ServerSocket(8080);
```

- 因为该类实现了Closeable接口，所以可以放在try()中，这样其就能自动关闭，不再需要我们手动使用close方法

```java
try (ServerSocket server = new ServerSocket(8080)) {
            
} catch (IOException e) {
  e.printStackTrace();
}
```

- 使用该ServerSocket对象调用accept方法，其会暂时阻塞当前这个服务器，直到接收到来自client的连接

```java
System.out.println("Waiting for client...");
Socket clientSocket = server.accept();
```



- accept方法会返回一个Socket对象，其代表与当前server建立连接的client
- 通过该Socket对象，调用getInetAddress()能获取一个InetAddress对象，再调用getHostAddress()能获取client的ip地址:

```java
System.out.println("client ip address: " + clientSocket.getInetAddress().getHostAddress());
```





完整写法:

![Xnip2022-02-23_21-34-25](Java Web.assets/Xnip2022-02-23_21-34-25.jpg)







### 1.2 client的创建

创建一个Socket对象，调用其构造方法，传入需要连接的服务ip地址和服务器对应的端口号(在创建ServerSocket对象时写了)

```java
Socket clientSocket = new Socket("localhost", 8080);
```



同样将其放入try中

```java
try (Socket clientSocket = new Socket("localhost", 8080)) {
  System.out.println("Connected!");
} catch (IOException e) {
  System.out.println("connection failed!");
  e.printStackTrace();
}
```











### 1.3 使用

- 先开启服务器:

![Xnip2022-02-23_21-34-25](Java Web.assets/Xnip2022-02-23_21-34-25.jpg)



- 再启动client进行连接:

![Xnip2022-02-23_21-51-05](Java Web.assets/Xnip2022-02-23_21-51-05.jpg)



![Xnip2022-02-23_21-51-37](Java Web.assets/Xnip2022-02-23_21-51-37.jpg)









- 将accept方法放在循环里就能一直接收连接请求了:

![Xnip2022-02-23_21-54-11](Java Web.assets/Xnip2022-02-23_21-54-11.jpg)