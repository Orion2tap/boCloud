package demo.Common;

public class Utility {

    static public void log(String s) {
        System.out.println(s);
    }

    static public void logFormat(String format, Object...args) {
        System.out.println(String.format(format, args));
    }

    static public String resourcePath(String path) {
        return String.format("src/main/resources/%s", path);
    }
}
