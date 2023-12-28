package demo;

import demo.Common.Utility;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MapperInvocationHandler implements InvocationHandler {
    private final HashSet<Class<?>> basicType;
    private Connection connection;
    private Map<Method, String> sqlMap;

    MapperInvocationHandler(Connection connection, Map<Method, String> sqlMap) {
        this.connection = connection;
        this.sqlMap = sqlMap;
        this.basicType = getBasicType();
    }

    // 基本类型类的集合
    private static HashSet<Class<?>> getBasicType() {
        HashSet<Class<?>> basicType = new HashSet<>();
        basicType.add(Boolean.class);
        basicType.add(Integer.class);
        basicType.add(Double.class);
        basicType.add(String.class);
        basicType.add(int.class);
        basicType.add(boolean.class);
        basicType.add(BigInteger.class);
        return basicType;
    }

    private static Object getBasicTypeObject(ResultSet SQLResult) {
        try {
            Object o = SQLResult.getObject(1);
            return o;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Object getPlainOldJavaObject(Class<?> clazz, ResultSet SQLResult) {
        Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Class<%s> no constructor \nerror:%s", clazz, e));
        }
        Field[] fields = clazz.getDeclaredFields();

        Object o;
        try {
            o = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(String.format("constructor :<%s>\nerror:%s", constructor, e));
        }
        for (Field f : fields) {
            Object v;
            try {
                v = SQLResult.getObject(f.getName());
            } catch (SQLException e) {
                throw new RuntimeException(String.format("SQL Result no Filed \nerror:<%s> <%s>", f.getName(), e));
            }
            f.setAccessible(true);
            try {
                f.set(o, v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return o;
    }

    // 根据类型解析查询结果
    private static Object getObject(HashSet<Class<?>> basicType, ResultSet SQLResult, Method method) throws SQLException {
        Class<?> returnType = method.getReturnType();
        SQLResult.first();
        if (basicType.contains(returnType)) {
            return getBasicTypeObject(SQLResult);
        } else {
            return getPlainOldJavaObject(returnType, SQLResult);
        }
    }

    private static List<Object> getObjectList(HashSet<Class<?>> basicType, ResultSet SQLResult, Method method) throws SQLException {
        List<Object> list = new ArrayList<>();

        Type type = method.getGenericReturnType();
        ParameterizedType returnListType = (ParameterizedType) type;
        // 只接受一个泛型参数
        Class<?> elementType = (Class<?>) returnListType.getActualTypeArguments()[0];

        while (SQLResult.next()) {
            Object o;
            if (basicType.contains(elementType)) {
                o = getBasicTypeObject(SQLResult);
            } else {
                o = getPlainOldJavaObject(elementType, SQLResult);
            }
            list.add(o);
        }

        return list;
    }

    private PreparedStatement preparedStatement(String sql, Object[] args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
        }
        return statement;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Utility.logFormat("invoke %s", method.getDeclaringClass());
        if (!method.getName().equals("toString")) {
            Utility.logFormat("invoke mapper <%s> <%s>", method, args);
            String sql = sqlMap.get(method);
            Utility.logFormat("invoke execute <%s>", sql);

            // 预编译语句
            try (PreparedStatement statement = preparedStatement(sql, args)) {
                boolean hasResult = statement.execute();
                if (hasResult) {
                    ResultSet queryResult = statement.getResultSet();
                    boolean isList = List.class.equals(method.getReturnType());

                    if (isList) {
                        return getObjectList(basicType, queryResult, method);
                    } else {
                        return getObject(basicType, queryResult, method);
                    }
                } else {
                    return null;
                }
            }
        } else {
            return "todo fix toString";
        }
    }

}
