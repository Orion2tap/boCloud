package demo.lib.Common;


public class Utility {
    static public void log(String format, Object...args) {
        System.out.println(String.format(format, args));
    }
}
