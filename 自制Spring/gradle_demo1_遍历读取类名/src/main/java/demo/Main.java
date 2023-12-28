package demo;


public class Main {
    static public void log(String format, Object...args) {
        System.out.println(String.format(format, args));
    }

    public static void main(String[] args) {
        log("作业1");
    }
}
