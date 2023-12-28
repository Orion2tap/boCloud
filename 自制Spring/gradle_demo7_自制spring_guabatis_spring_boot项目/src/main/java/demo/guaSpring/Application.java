package demo.guaSpring;

import demo.Common.Utility;
import demo.guaServer.Server;
import demo.guaSpringMVC.Dispatcher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

    private static ArrayList<String> blackList = new ArrayList<>() {{
        add("demo.ServletForTomcat");
    }};

    static List<String> getClassNames(Class<?> clazz) {
        URL url = clazz.getResource(String.format("%s.class", clazz.getSimpleName()));
        Objects.requireNonNull(url);
        Path rootDirPath = null;
        try {
            // 找到项目的根路径
            rootDirPath = Path.of(clazz.getResource("/").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 下面是遍历指定文件夹下的所有文件, 输出符合要求的文件名
        Stream<Path> stream;
        try {
            stream = Files.walk(rootDirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 这是用来进行文件匹配的实例, 可以匹配所有 .class 结尾的文件
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.class");
        Path finalRootDirPath = rootDirPath;
        // 下面是遍历文件夹下的所有文件, 筛选出符合要求的文件名, 再用文件名拼出完整类名, 最后放入一个 List
        List<String> classNames = stream
                // 筛选出 .class 结尾的文件
                .filter(path -> matcher.matches(path.getFileName()))
                // 对每个 .class 结尾的文件, 找到相对路径, 返回完整类的类名
                .map(path -> {
                    Path relativePath = finalRootDirPath.relativize(path);
                    // 把路径名的分隔符 / 替换成 . , 转成类名
                    String className = relativePath.toString().replace(File.separatorChar, '.');
                    className = className.substring(0, className.length() - 6);
                    Utility.log("walk %s %s %s", className, relativePath, path);
                    return className;
                })
                .collect(Collectors.toList());
        return classNames;
    }

    static List<Class<?>> getClasses(ClassLoader loader, List<String> classNames) {
        List<Class<?>> classes = new ArrayList<>();
        for (String name: classNames) {
            Class<?> clazz;
            try {
                if (!blackList.contains(name)) {
                    // 根据类名来加载类
                    Utility.log("load class <%s>", name);
                    clazz = loader.loadClass(name);
                } else {
                    continue;
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            classes.add(clazz);
        }
        return classes;
    }

    public static void scan(Class<?> clazz) {
        List<String> classNames = getClassNames(clazz);
        List<Class<?>> classes = getClasses(clazz.getClassLoader(), classNames);
        BeanRegistry.scanBean(classes);
    }

    public static void run(Class<?> clazz) {
        Utility.log("GuaSpring Application run");
        scan(clazz);

        Dispatcher dispatcherForGuaServer = new Dispatcher();

        Server server = new Server(dispatcherForGuaServer);
        server.run(9000);
    }

}
