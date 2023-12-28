package demo.lib.guaSpring;


import demo.lib.Common.Utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeanRegistry {
    private static HashMap<Class<?>, Object> BeanDefinition = new HashMap<>();


    public static Object getBean(Class<?> BeanType) {
        return BeanDefinition.get(BeanType);
    }

    public static void register(Class<?> BeanType, Object Bean) {
        BeanDefinition.put(BeanType, Bean);
    }

    public static List<Object> getBeansByAnnotation(Class<? extends Annotation> annotationClass) {
        List<Object> r = new ArrayList<>();
        for(Class<?> clazz: BeanDefinition.keySet()) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                r.add(getBean(clazz));
            }
        }
        return r;
    }


    public static <T extends Annotation> void scanComponent(Class<?> clazz, Class<T> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            Utility.log("class<%s> 有注解 <%s>", clazz, annotationClass);
            // 只支持一个构造器
            Constructor<?> constructor = clazz.getConstructors()[0];

            // 构造器参数不为 0 时, 去 BeanDefinition 里面找实例
            if (constructor.getParameterCount() > 0) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] args = new Object[constructor.getParameterCount()];
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> type = parameterTypes[i];
                    Object arg = getBean(type);
                    args[i] = arg;
                }
                Object instance;
                try {
                    instance = constructor.newInstance(args);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                register(clazz, instance);
            } else {
                Object instance;
                try {
                    instance = constructor.newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                Utility.log("instance %s", instance);
                register(clazz, instance);
            }

        }
    }

    static void scanBean(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            scanComponent(clazz, Service.class);
        }

        for (Class<?> clazz : classes) {
            scanComponent(clazz, Controller.class);
        }

        Utility.log("beans: %s", BeanDefinition);
    }
}
