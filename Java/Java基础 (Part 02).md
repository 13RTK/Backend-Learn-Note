# 1.Inner class



## 1) overview



- Inner class is a class which in a class

Classification:

1. Member inner class(Created in a class)
2. Local inner class(Include anonymous class) (**Created in a method**)









## 2) Member inner class



### 1. format



example:

```java
public class External {
  public class Internal {
    
  }
}
```





**Attention**

- In folder, the inner-class's class file will be named as "**external-class&**inner-class.class", so don't use "&" to name a class file for avoid some mistakes.





exam:

![Xnip2021-02-25_14-43-51](Java基础.assets/Xnip2021-02-25_14-43-51.jpg)









### 2. usage



- The external-class can invoke internal var/method by create **a internal-class object in external-class method**
- Internal-class can invoke external content freely
- Can directly create a internal-class object than invoke what you want





By create a internal object in external method:

exam1:

![Xnip2021-02-25_15-02-59](Java基础.assets/Xnip2021-02-25_15-02-59.jpg)









Create a internal object than to invoke what you want



**Format**

```java
External-class.Internal-class object = new Exter().new Inter();
```



exam2:

![Xnip2021-02-25_15-06-34](Java基础.assets/Xnip2021-02-25_15-06-34.jpg)









Inner class can invoke external-class's anything

exam3:

![Xnip2021-02-25_15-10-28](Java基础.assets/Xnip2021-02-25_15-10-28.jpg)









### 3. same name var in inner



- If the you want to access external-class's var/methods, can use "external-class."



exam:

![Xnip2021-02-25_15-22-00](Java基础.assets/Xnip2021-02-25_15-22-00.jpg)







## 3) Local inner class



### 1. format



```java
public class OuterClass() {
  public void outerMethod() {
    class LocalInClass() {
      
    }
  }
}
```









### 2. usage



- Create a object **in local method to use local-inner-class**
- It **only can be used in the method**
- The **local inner can't use modifier(default)**







exam:

![Xnip2021-02-28_14-16-48](Java基础.assets/Xnip2021-02-28_14-16-48.jpg)







### 3. invoke local var



- **The invoked local var only can be a "final" value(Never change)**
- Since java8, can don't use "final" to modify the local var(But must be sure the var is a constant)



exam:

![Xnip2021-02-28_14-16-48](Java基础.assets/Xnip2021-02-28_14-16-48.jpg)









Opposition exam:

![Xnip2021-02-28_14-45-30](Java基础.assets/Xnip2021-02-28_14-45-30.jpg)











## 4) anonymous inner class



Advantage:

- Use for a implement/sub class **only need to use once**
- **Don't need** to create a sub/implement class **extra**





Format:

```java
public class Application {
  public static void main(String[] args) {
    Interface/SuperClass object_name = new Interface/SuperClass() {
      @override
      public ... interface_method/super_method() {
        
      }
    };
    
    object_name.method();
  }
}
```







exam:

![Xnip2021-02-28_15-15-44](Java基础.assets/Xnip2021-02-28_15-15-44.jpg)













**Attention**

Analyze format "new interface() {...}"

- "new" means create a object
- "interface()" is to **indicate which inferface** the anonymous-inner-class need to implement
- **In "{...}"** are correct anonymous-inner-class's content



**Usage**

- Anonymous-inner-class only can use once, when you create a object

If you want to create the anonymousInClass many time, you only can use a single defined implement-class

- Anonymous-inner-class just does't have class-name, but can has object-name

You can also create anonymous-objects, but it only can invoke method once





Anonymous-inner-class and anonymous -object:

![Xnip2021-03-01_11-24-48](Java基础.assets/Xnip2021-03-01_11-24-48.jpg)

****























# 2.Object





## 1) toString

- If you print a object, **it has same result with "toString" method**
- The **default "toString" method belong "Object" class**
- The default "toString" method will print the object's **address value**
- "Object" is **every class's super class**





exam:

![Xnip2021-03-04_11-22-13](Java基础.assets/Xnip2021-03-04_11-22-13.jpg)





![Xnip2021-03-04_11-23-46](Java基础.assets/Xnip2021-03-04_11-23-46.jpg)







Override "toString" method



exam:

![Xnip2021-03-04_11-36-28](Java基础.assets/Xnip2021-03-04_11-36-28.jpg)













## 2) equals





- “equals” method belong to Object class

- “equals” method in Object will use ”==” to compare two object(Only compare address value)

  

  

  For number value:

  Will compare their value

  

  For quote type(object)

  Will compare their address value(Object stored address value)



exam:

![Xnip2021-03-04_13-52-47](Java基础.assets/Xnip2021-03-04_13-52-47.jpg)







Override equals method

(You can use IDEA's shortcut to generate it)



- The newly "equals" method will compare all the attribute of the objects
- For a String type, you can also use "equals" in String to compare
- "String" class override the "equals" method, so it can compare the attribute, neither address value(==)





exam:

![Xnip2021-03-04_14-35-21](Java基础.assets/Xnip2021-03-04_14-35-21.jpg)







You better choose first one:

![Xnip2021-03-04_14-37-53](Java基础.assets/Xnip2021-03-04_14-37-53.jpg)





![Xnip2021-03-04_14-36-50](Java基础.assets/Xnip2021-03-04_14-36-50.jpg)







After override, this two object are same:

![Xnip2021-03-04_14-41-41](Java基础.assets/Xnip2021-03-04_14-41-41.jpg)







**Attention**

- **Can't use a "null" object** to directly invoke equals

- It **will throw "NullPointerException"**

  ****



















# 3.Objects




## 1) equals

- The "equals" in Objects can ignore null
- You can use "equals" method to compare with other object without exception





exam:

![Xnip2021-03-05_21-21-36](Java基础.assets/Xnip2021-03-05_21-21-36.jpg)





Use "equals" method in Objects:

![Xnip2021-03-05_21-26-18](Java基础.assets/Xnip2021-03-05_21-26-18.jpg)















