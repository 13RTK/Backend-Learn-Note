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











































