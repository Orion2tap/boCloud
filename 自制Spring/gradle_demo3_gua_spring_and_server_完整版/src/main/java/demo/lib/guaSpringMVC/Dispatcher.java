package demo.lib.guaSpringMVC;

import demo.lib.Common.Utility;
import demo.lib.guaSpring.BeanRegistry;
import demo.lib.guaSpring.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class Dispatcher {
    HashMap<String, Method> urlMethod;
    HashMap<String, Object> urlBean;


    public Dispatcher() {
        this.urlMethod = new HashMap<>();
        this.urlBean = new HashMap<>();

        List<Object> controllers = BeanRegistry.getBeansByAnnotation(Controller.class);
        for (Object controller:controllers) {
            Method[] methods = controller.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(GET.class)){
                    GET annotation = method.getAnnotation(GET.class);
                    String url = annotation.value();
                    Utility.log("scan url %s %s", url, method);
                    urlMethod.put(url, method);
                    urlBean.put(url, controller);
                }
            }
        }
    }


    public String doDispatch(String url) {
        Method method = urlMethod.get(url);
        Object object = urlBean.get(url);

        try {
            String response = (String) method.invoke(object);
            Utility.log("response: <%s>", response);
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
