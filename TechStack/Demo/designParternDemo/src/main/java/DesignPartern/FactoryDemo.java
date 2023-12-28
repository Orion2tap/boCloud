package DesignPartern;

import java.io.*;

 class ReaderFactory {
    public static Reader getReader(String path, String reader) throws FileNotFoundException {
           if (reader.equals("BufferedReader")) {
               return getBufferedReader(path);
           } else if (reader.equals("LineNumberReader")) {
               return getLineNumberReader(path);
           } else {
               throw new RuntimeException(String.format("类型错误 %s", reader));
           }
    }


    public static Reader getBufferedReader(String path) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        return reader;
    }


    public static Reader getLineNumberReader(String path) throws FileNotFoundException {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        return reader;
    }
}

public class FactoryDemo {

    public static void main(String[] args) throws FileNotFoundException {
        Reader reader = ReaderFactory.getReader("a.txt", "BufferedReader");
    }
}
