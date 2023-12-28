package demo.example;

import demo.Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class GuaHashMap {
    private ArrayList<ArrayList<String>> list;
    private Integer __size;
    private Integer length;

    GuaHashMap() {
        __size = 17;
        list = new ArrayList<>();
        length = 0;

        for (int i = 0; i < __size; i++) {
            ArrayList<String> element = new ArrayList<>();
            list.add(element);
        }
    }

    private Integer size() {
        return this.length;
    }

    private Integer hash(String key) {
        int hashResult = 0;
        int jinwei = 1;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            int n = c * jinwei;

            hashResult = hashResult + n;
            jinwei = jinwei * 10;
        }

        if (hashResult < 0) {
            hashResult = - hashResult;
        }

        Integer index = hashResult % this.__size;
        return index;
    }

    void put(String key, String value) {
        Integer index = this.hash(key);
        ArrayList<String> l = this.list.get(index);

        boolean found = false;
        for (int i = 0; i < l.size(); i = i + 2) {
            String k = l.get(i);
            String v = l.get(i + 1);
            if (k.equals(key)) {
                l.set(i + 1, value);
                found = true;
            }
        }
        // 如果遍历完列表都没找到符合 key, 就把 key 和 value 添加到链表的后面
        if (!found) {
            l.add(key);
            l.add(value);
            this.length = this.length + 1;
        }
    }

    String get(String key) {
        Integer index = this.hash(key);
        ArrayList<String> l = this.list.get(index);

        for (int i = 0; i < l.size() ; i = i + 2) {
            String k = l.get(i);
            String v = l.get(i + 1);
            if (k.equals(key)) {
                return v;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (ArrayList<String> element: list) {
            if (element.size() > 0) {
                sb.append(element.toString());
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void testHashMap() {
//        HashMap<String, String> b = new HashMap<>();
        GuaHashMap dict = new GuaHashMap();
        dict.put("gua", "1");
        dict.put("name", "ggg");
        dict.put("hegith", "1.69");

        Utils.log("dict (%s) (%s)", dict, dict.length);
//        dict.put("gua", "vvff");
        Utils.log("dict (%s) (%s)", dict, dict.length);
        Utils.ensure(dict.get("gua").equals("1"), "testHashMap1");
        Utils.ensure(dict.get("name").equals("ggg"), "testHashMap2");
        Utils.ensure(dict.get("hegith").equals("1.69"), "testHashMap3");
    }

    public static void main(String[] args) {
        testHashMap();

    }
}
