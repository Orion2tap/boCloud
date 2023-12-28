package demo;

import demo.guaServer.GuaRequest;
import demo.guaServer.GuaResponse;
import demo.guaSpring.Application;
import demo.guaSpringMVC.Dispatcher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@WebServlet("/*")
public class ServletForTomcat extends HttpServlet {
    private Dispatcher dispatcher;

    @Override
    public void init()  {
        Application.scan(ServletForTomcat.class);
        this.dispatcher = new Dispatcher();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        GuaRequest guaRequest = new GuaRequest();
        guaRequest.path = request.getRequestURI();

        GuaResponse guaResponse = new GuaResponse();
        dispatcher.doDispatch(guaRequest, guaResponse);

        for (String key:guaResponse.headers.keySet()) {
            String value = guaResponse.headers.get(key);
            response.setHeader(key, value);
        }

        //设置逻辑实现
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(guaResponse.body);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}