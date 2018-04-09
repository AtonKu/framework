package com.atonku.helper;

import com.atonku.util.CollectionUtil;
import com.atonku.util.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: 数据库操作工具类
 * @Date: 2018/4/9 9:15
 * @Author: GYT
 * @Modified by:
 **/
public class DatabaseHelper {

    //添加日志打印输出
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    //添加dbutils
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //添加ThreadLocal
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropsUtil.loadProps("jdbc.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库链接
     * @return
     * @time    ①2018/04/08
     *          ②2018/04/09
     */
    /* 添加ThreadLoacl后的代码，保证connection的线程安全 */
    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();    //<1>
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
            } finally {
                CONNECTION_HOLDER.set(connection);          //<2>
            }
        }
        return connection;
    }
    /* 原始代码 */
    /*public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return connection;
    }*/

    /**
     * 关闭数据库连接
     * @param
     * @time    ①2018/04/08
     *          ②2018/04/09
     */
    /* 添加ThreadLoacl后的代码 */
    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();         //<3>
            }
        }
    }
    /* 原始代码 */
    /*public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }*/

    //添加apache commons dbutils后添加的代码

    /**
     * 查询实体列表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     * @Note 使用QueryRunner对象进行面向实体的查询，dbutils会先执行sql语句，返回ResultSet，然后通过反射创建并初始化实体对象，
     *       我们需要的是list，可以使用BeanListHandler
     * @time    ①2018/04/08
     *          ②2018/04/09
     */
    /* 添加ThreadLoacl后的代码 */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection connection = getConnection();
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new  RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }
    /* 原始代码 */
    /*public static <T> List<T> queryEntityList(Connection connection, Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new  RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
        return entityList;
    }*/

    /**
     * 查询实体类
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     * @time 2018/04/09
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        Connection connection = getConnection();
        try {
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 使用MapListHandler来查询List<Map>对象
     * @param sql
     * @param params
     * @return
     * @time 2018/04/09
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> resultList;
        try {
            Connection connection = getConnection();
            resultList = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("execute query faliure", e);
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * 通用的更新方法，返回更新操作影响的数据库记录条数
     * @param sql
     * @param params
     * @return
     * @Time: 2018/04/09
     */
    public static int update(String sql, Object... params) {
        int result;
        try {
            Connection connection = getConnection();
            result = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 调用上面的更新方法插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     * @time 2018/04/09
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity : fieldMap is empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.indexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return update(sql, params) == 1;
    }

    /**
     * 更新实体类
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     * @time 2018/04/09
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity : fieldMap is empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + "WHERE id=?";

        List<Object> paramsList = new ArrayList<Object>();
        paramsList.addAll(fieldMap.values());
        paramsList.add(id);
        Object[] params = paramsList.toArray();
        return update(sql, params) == 1;

    }

    /**
     * 删除实体
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     * @time 2018/04/09
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return update(sql, id) ==1;
    }

    /**
     * 根据传入的实体类获取类名
     * @param entityClass
     * @return
     * @time 20018/04/09
     */
    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * =================================================================================================================
     * Class.forName()用法详解
     *
     * 主要功能
     * Class.forName(xxx.xx.xx)返回的是一个类。
     * Class.forName(xxx.xx.xx)的作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段。
     * 下面，通过解答以下三个问题的来详细讲解下Class.forName()的用法。
     * 一.什么时候用Class.forName()？
     * 先来个热身，给你一个字符串变量，它代表一个类的包名和类名，你怎么实例化它？你第一想到的肯定是new,但是注意一点：
     * A a = (A)Class.forName(“pacage.A”).newInstance();
     * 这和你 A a = new A()； 是一样的效果。
     *
     * 现在言归正传。
     * 动态加载和创建Class 对象，比如想根据用户输入的字符串来创建对象时需要用到：
     * String str = “用户输入的字符串” ;
     * Class t = Class.forName(str);
     * t.newInstance();
     *
     * 在初始化一个类，生成一个实例的时候，newInstance()方法和new关键字除了一个是方法，一个是关键字外，最主要有什么区别？
     *
     * 它们的区别在于创建对象的方式不一样，前者是使用类加载机制，后者是创建一个新类。
     *
     * 那么为什么会有两种创建对象方式？这主要考虑到软件的可伸缩、可扩展和可重用等软件设计思想。
     *
     * Java中工厂模式经常使用newInstance()方法来创建对象，因此从为什么要使用工厂模式上可以找到具体答案。 例如：
     * class c = Class.forName(“Example”);
     * factory = (ExampleInterface)c.newInstance();
     *
     * 其中ExampleInterface是Example的接口，可以写成如下形式：
     * String className = “Example”;
     * class c = Class.forName(className);
     * factory = (ExampleInterface)c.newInstance();
     *
     * 进一步可以写成如下形式：
     * String className = readfromXMlConfig;//从xml 配置文件中获得字符串
     * class c = Class.forName(className);
     * factory = (ExampleInterface)c.newInstance();
     *
     * 上面代码已经不存在Example的类名称，它的优点是，无论Example类怎么变化，上述代码不变，甚至可以更换Example的兄弟类Example2 , Example3 , Example4……，只要他们继承ExampleInterface就可以。
     *
     * 从JVM的角度看，我们使用关键字new创建一个类的时候，这个类可以没有被加载。但是使用newInstance()方法的时候，就必须保证：
     * 1、这个类已经加载；
     * 2、这个类已经连接了。
     * 而完成上面两个步骤的正是Class的静态方法forName()所完成的，这个静态方法调用了启动类加载器，即加载 java API的那个加载器。
     *
     * 现在可以看出，newInstance()实际上是把new这个方式分解为两步，即首先调用Class加载方法加载某个类，然后实例化。
     *
     * 这样分步的好处是显而易见的。我们可以在调用class的静态加载方法forName时获得更好的灵活性，提供给了一种降耦的手段。
     *
     * 二.new 和Class.forName（）有什么区别？
     * 其实上面已经说到一些了，这里来做个总结：
     * 首先，newInstance( )是一个方法，而new是一个关键字；
     * 其次，Class下的newInstance()的使用有局限，因为它生成对象只能调用无参的构造函数，而使用 new关键字生成对象没有这个限制。
     * 简言之：
     * newInstance(): 弱类型,低效率,只能调用无参构造。
     * new: 强类型,相对高效,能调用任何public构造。
     * Class.forName(“”)返回的是类。
     * Class.forName(“”).newInstance()返回的是object 。
     * 三.为什么在加载数据库驱动包的时候有用的是Class.forName( )，却没有调用newInstance( )？
     * 在Java开发特别是数据库开发中，经常会用到Class.forName( )这个方法。
     * 通过查询Java Documentation我们会发现使用Class.forName( )静态方法的目的是为了动态加载类。
     * 通常编码过程中，在加载完成后，一般还要调用Class下的newInstance( )静态方法来实例化对象以便操作。因此，单使用Class.forName( )是动态加载类是没有用的，其最终目的是为了实例化对象。
     *
     * 相关英文参考文献如下：
     * we just want to load the driver to jvm only, but not need to user the instance of driver,
     * so call Class.forName(xxx.xx.xx) is enough, if you call Class.forName(xxx.xx.xx).newInstance(),
     * the result will same as calling Class.forName(xxx.xx.xx),
     * because Class.forName(xxx.xx.xx).newInstance() will load driver first,
     * and then create instance, but the instacne you will never use in usual,
     * so you need not to create it.
     *
     * ========================== 华丽的分割线 =============================
     *
     * Class.forName()、Class.forName().newInstance() 、New 三者区别！
     * 终于明白为什么加载数据库驱动只用Class.forName（）了！！困扰了我2个小时！！希望我写的这个东西对各位有所帮助。
     *
     *
     *    在Java开发特别是数据库开发中，经常会用到Class.forName( )这个方法。
     * 通过查询Java Documentation我们会发现使用Class.forName( )静态方法的目的是为了动态加载类。
     * 在加载完成后，一般还要调用Class下的newInstance( )静态方法来实例化对象以便操作。因此，单单使用Class.forName( )是动态加载类是没有用的，其最终目的是为了实例化对象。
     *    这里有必要提一下就是Class下的newInstance()和new有什么区别？，首先，newInstance( )是一个方法，而new是一个关键字，其次，Class下的newInstance()的使用有局限，因为它生成对象只能调用无参的构造函数，而使用 new关键字生成对象没有这个限制。
     *    好，到此为止，我们总结如下：
     *    Class.forName("")返回的是类
     *    Class.forName("").newInstance()返回的是object
     *    有数据库开发经验朋友会发现，为什么在我们加载数据库驱动包的时候有的却没有调用newInstance( )方法呢？即有的jdbc连接数据库的写法里是Class.forName(xxx.xx.xx);而有一 些：Class.forName(xxx.xx.xx).newInstance()，为什么会有这两种写法呢？
     *    刚才提到，Class.forName("");的作用是要求JVM查找并加载指定的类，如果在类中有静态初始化器的话，JVM必然会执行该类的静态代码 段。而在JDBC规范中明确要求这个Driver类必须向DriverManager注册自己，即任何一个JDBC Driver的 Driver类的代码都必须类似如下：
     *   public class MyJDBCDriver implements Driver {
     *    static {
     *      DriverManager.registerDriver(new MyJDBCDriver());
     *   }
     *   }
     *  既然在静态初始化器的中已经进行了注册，所以我们在使用JDBC时只需要Class.forName(XXX.XXX);就可以了。
     *
     * 贴出Proxool 连接池的静态初始化方法：
     * public class ProxoolDriver implements Driver {
     *
     *     private static final Log LOG = LogFactory.getLog(ProxoolDriver.class);
     *
     *     static {
     *         try {
     *             DriverManager.registerDriver(new ProxoolDriver());
     *         } catch (SQLException e) {
     *             System.out.println(e.toString());
     *         }
     *     }
     * }
     *
     * =================================================================================================================
     *
     * =================================================================================================================
     * 每次获取connection时，先在ThreadLocal中寻找（<1>），若不存在，则创建新的链接，并放入ThreadLocal（<2>），当connection使用完毕后，需要
     * 移除ThreadLocal中持有的connection
     * =================================================================================================================
     *
     * =================================================================================================================
     * DbUtils 提供了多种的Handler:
     * BeanHandler : 返回Bean对象
     * BeanListHandler : 返回list对象
     * BeanMapHandler : 返回map对象
     * ArrayHandler : 返回Object[]对象
     * ArrayListHandler : 返回list对象
     * MapHandler : 返回map对象
     * MapListHandler : 返回list<map>对象
     * ScalarHandler : 返回某列的值
     * ColumnListHandler : 返回某列的值列表
     * KeyedHandler : 返回map<map>对象
     *
     */


}
