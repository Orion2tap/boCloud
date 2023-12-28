package demo;


import demo.Interface.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    static public void log(String format, Object...args) {
        System.out.println(String.format(format, args));
    }

    public static void main(String[] args) throws ClassNotFoundException {
        log("hello");
    }
}
