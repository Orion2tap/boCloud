package demo.guaSpringMVC;

import demo.Common.Utility;
import demo.guaServer.GuaRequest;
import demo.guaServer.GuaResponse;
import demo.guaSpring.BeanRegistry;
import demo.guaSpring.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class Dispatcher {
    HashMap<String, Method> urlMethod;
    HashMap<String, Object> urlBean;


    public  Dispatcher() {
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


    public void doDispatch(GuaRequest guaRequest, GuaResponse guaResponse) {
        Method method = urlMethod.get(guaRequest.path);
        Object object = urlBean.get(guaRequest.path);

        try {
            method.invoke(object, guaRequest, guaResponse);
            Utility.log("response: <%s>", guaResponse);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
