package demo;




public class Main {


    static public void log(String format, Object...args) {
        System.out.println(String.format(format, args));
    }

    public static void ensure(boolean condition, String message) {
        if (!condition) {
            log("%s", message);
        } else {
            log("测试成功");
        }
    }

    public static void main(String[] args) {
        log("hello");
    }

}
