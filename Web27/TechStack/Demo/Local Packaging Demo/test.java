public class test {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void main(String[] args) {
        log("hello world");
    }
}
