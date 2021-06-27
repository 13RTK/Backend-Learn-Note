# 一、StackTrackElement

- 属于java.lang下，用于操作堆栈跟踪







相关方法:



构造方法:

- StackTraceElement(String declaringClass, String methodName, String fileName, int lineNumber)	
- StackTraceElement(String classLoaderName, String moduleName, String moduleVersion, String declaringClass, String methodName, String fileName, int lineNumber)



实例方法:

- boolean equals(Object obj): 
- String getClassLoaderName(): 返回该元素表示位置的**类加载器**的名称
- String getClassName(): 返回该元素表示位置的**类**的名称
- String getFileName(): 返回该元素表示位置的**源文件**的名称
- int getLineNumber(): 返回该元素表示位置的**源码位置**的行号
- String getMethodName(): 返回该元素表示位置的**方法**的名称
- String getModuleName():返回该元素表示位置的**模块**的名称
- String getModuleVersion(): 返回该元素表示位置的**模块版本**的名称
- int hashCode():
- boolean isNativeMethod():
- String toString():

![0FEFC17C-0BE0-4B99-A1F5-2A234A13A157](images/0FEFC17C-0BE0-4B99-A1F5-2A234A13A157.png)

****













# 二、Enum

- 用于创建枚举类，固定相关的值
- 创建相关对象时，直接调用枚举类中的字段来创建对象
- 使用对象时，直接通过类调用，获取通过方法来查找即可





## 1.创建

-  构造方法应该设置为private，不能新增enum类里的值，否则就不起限制作用了
- 每个实例都应该大写
- 如果有字段，则在实例后面添加括号



exam:

![Xnip2021-06-22_17-44-21](images/Xnip2021-06-22_17-44-21.jpg)





相关方法



构造方法: 

- protected Enum(String name, int ordinal): 仅有的构造方法





实例方法:

- int compareTo(E e): 对比和参数在类中的顺序
- boolean equals(Object other): 如果参数非该枚举类，返回false
- Class<E> getDeclaringClass(): 返回当前枚举对象所属的类
- String name(): 返回当前枚举类型的字面量
- int ordinal(): 返回当前枚举类型在枚举类中声明的位置
- String toString():
- static <T extends Enum<T>> T valueOf(Class<T> enumType, String name): 返回指定枚举类中，指定名称的枚举对象所属的枚举类类



****

















# 三、Multi Thread

- 在未使用多线程之前，我们只有一个线程(main主线程)
- 我们可以在处理其他类的时候，在该类的对象上创建一个新的线程，可以提供运行效率
- 想要在一个类的对象上创建一个新的线程，该类必须继承Thread类，或者实现Runable接口，两者都需要重写run()方法











## 1) 基础

1.创建一个实现Runnable接口的类，重写run()方法:

![Xnip2021-06-21_10-56-49](images/Xnip2021-06-21_10-56-49.jpg)











2.在main()中创建该对象，并将其传入Thread的构造方法中:

![Xnip2021-06-21_10-59-40](images/Xnip2021-06-21_10-59-40.jpg)

- 使用start()方法开始该线程
















## 2) Thread

- 属于java.lang包下，其实现了implements类







相关方法:



构造方法:

- Thread(): 创建一个线程
- Thread(Runnable target): 接收一个Runnable对象(或者其实现类)，以此创建一个线程
- Thread(Runnable target, String name): 接收一个字符串，将其作为线程名称
- Thread(String name): 接收一个字符串，将其作为线程名称





静态方法:

- static Thread CurrentThread(): 返回当前线程
- void interrupt(): 打断该线程
- boolean isInterrupted(): 判断该线程是否被打断
- static void sleep(long milis): 将该线程指定休眠时间，参数为毫秒值













## 3) synchronized

- volatile: 修饰变量，使得其作为共享内存，而不会保存在本地缓存中

volatile: 易变的，不稳定的













# 四、args参数

- 使用java命令调用.class文件时，在java命令后的输入内容即为参数，会被保存在args数组中，可以在程序中将其输出



![Xnip2021-06-22_10-44-26](images/Xnip2021-06-22_10-44-26.jpg)













