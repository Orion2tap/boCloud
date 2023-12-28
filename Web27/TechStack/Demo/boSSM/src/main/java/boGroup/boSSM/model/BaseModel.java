package boGroup.boSSM.model;

import java.lang.reflect.Field;

public class BaseModel {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 反射: 在程序运行时拿到类和实例的属性及对应值
        Field[] fields = this.getClass().getDeclaredFields();

        sb.append("(");
        for (Field f: fields) {
            try {
                // 设置能取到私有属性
                f.setAccessible(true);
                // "取得obj对象在这个Field上的值" http://www.51gjie.com/java/791.html
                // 取得this(当前运行的实例)在f属性上的值
                Object v = f.get(this);
                String s = String.format("%s: %s, ", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sb.append(")");
        return sb.toString();
    }
}

