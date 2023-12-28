package boGroup.boSSM;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 接口
interface TodoMapper {
    Integer select(Integer id, String content);
}


class MapperProxy implements InvocationHandler {
    // 接口方法的具体实现
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("接口方法调用");
        System.out.printf("方法<%s>\n",  method.toString());
        for (int i = 0; i < args.length; i++) {
            System.out.printf("参数 %s: <%s>\n", i, args[i]);
        }
        return args[0];
    }
}

public class DynamicProxyDemo {

    public static void main(String[] args) {
        TodoMapper t = (TodoMapper) Proxy.newProxyInstance(
                // 接口的类加载器
                TodoMapper.class.getClassLoader(),
                // 接口
                new Class[]{TodoMapper.class},
                // 处理器
                new MapperProxy()
        );

        Utility.log("select 调用");
        Integer r = t.select(2, "todo");
        Utility.log("select 结束");
        System.out.printf("response: %s", r);
    }
}
