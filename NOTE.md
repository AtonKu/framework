# notes #
 * **2018/04/08** 
   - 
   - String.value(Object), Object.toString() 和 (String)的区别：  
     - (String)：标准的类型转换，将Object类型转换为String类型，使用时需要注意该类型可以转换为String，因此最好用instanceof做个检查，判断是否
     可以转换，否则容易抛出ClasCaseException异常。此外，需特别小心的是因定义为Object 类型的对象在转成String时语法检查并不会报错，这将可能导致
     潜在的错误存在。这时要格外小心。如：  
     ``` java 
        Object obj = new Integer(100);
        String strVal = (String)obj;
     ```
     - toString(Object)：在这种使用方法中，因为java.lang.Object类里已有public方法.toString()，所以对任何严格意义上的java对象都可以调用
     此方法。但在使用时要注意，必须保证object不是null值，否则将抛出NullPointerException异常。采用这种方法时，通常派生类会覆盖Object里的
     toString（）方法。
     - String.valueOf：这个方法是静态的，直接通过String调用，可以说是完美，只是平时不习惯这样写而已，这样的实现避免了前面两个的不足和缺点。首先
     来看看他内部的实现机制：  
     ```java
        public static String valueOf(Object obj){
            return (obj==null) ? "null" : obj.toString()
        };
     ```
        在内部就是做了为空的判断的，所以就不会报出空指针异常。　　
        从上面的源码可以很清晰的看出null值不用担心的理由。但是，这也恰恰给了我们隐患。我们应当注意到，当object为null 时，
        String.valueOf（object）的值是字符串”null”，而不是null！！！在使用过程中切记要注意。
       
 * **2018/04/09**
   - 
   - 优化代码，创建数据库操作工具类，使service中的代码更加简洁
   - 使用Apache Common项目下的Dbutils工具类来使代码更加简洁
   - 为了使connection的创建和关闭对开发人员透明，使其完全隐藏起来，并确保一个线程中只有一个connection，可以使用ThreadLocal来存放本地线程变量。
   将当前线程放入ThreadLocal中存起来，使其线程安全。可以理解为ThreadLocal为隔离线程的容器。
   - 添加Apache DBCP 数据库连接池，对数据库的链接实现池化。