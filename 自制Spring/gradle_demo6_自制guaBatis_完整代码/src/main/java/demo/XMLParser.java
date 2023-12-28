package demo;

import demo.Common.Utility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


class XMLParser {
    private static Element getRoot(String path) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document d;
        try {
            d = builder.parse(new File(path));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        return d.getDocumentElement();

    }

    private static Method getMethod(Class<?> mapperClass, Node node) {
        Element element = (Element) node;
        String methodName = element.getAttribute("method");
        Method method = null;
        Method[] methods = mapperClass.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            throw new RuntimeException(String.format("找不到 method <%s> <%s>", mapperClass, methodName));
        }
        return method;
    }

    static Map<Method, String> getSQLMap(String path) {
        Element root = getRoot(path);
        Class<?> clazz = getClass(root);
        HashMap<Method, String> sqlMap = new HashMap<>();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            String sql = n.getTextContent().trim();
            // 库解析XML的时候, 如果有换行符会自动解析成一个 <text> node, 需要跳过
            if (!sql.equals("")) {
                Method method = getMethod(clazz, n);
                Utility.logFormat("load <%s> <%s>", sql, method);
                sqlMap.put(method, sql);
            }
        }
        return sqlMap;
    }

    private static Class<?> getClass(Element root) {
        String className = root.getAttribute("class");
        Class<?> mapperClass;
        try {
            mapperClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mapperClass;
    }

}
