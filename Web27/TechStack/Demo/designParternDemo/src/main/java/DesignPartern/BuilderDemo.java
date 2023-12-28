package DesignPartern;

/*
在 Speech 内部设置一个类 Builder
Speech 的构造器接受 Builder 的实例来填充自己的属性
Builder 类可以通过调用 set 函数来设置自己的各项属性
只需要每个属性写一个 set 方法
最终调用一个 Builder 类的 Build 函数, 来返回 Speech 的实例
 */

/*
    Speech speech = new Speech.Builder("班主任的发言").
        setA("A 的发言").
        setB("B 的发言").
        build();

    类似班会上先在草稿 (builder) 记录大家的发言, 要求班主任必须发言, 其他同学选择 A 和 B 发言,
    先调用内部类 Builder 的构造方法, 设定必选参数 (记录班主任的发言), this 就是这个草稿本
    然后调用 Builder 的 setA (记录同学 A 的发言)
    然后调用 Builder 的 setB (记录同学 B 的发言)
    最后调用 Builder 的 build (记录结束后将草稿本交给班长)

    调用 Speech 类的构造方法 (班长将草稿本上的发言整理成正式文档 speech1)
 */

class Speech {
    String headMaster;
    String a;
    String b;
    String c;

    // 静态内部类
    public static class Builder {
        private final String headMaster;
        private String a;
        private String b;
        private String c;

        // 必选参数 headMaster
        public Builder(String headMaster) {
            this.headMaster = headMaster;
        }

        // 可选参数 a b c
        public Builder setA(String a) {
            this.a = a;
            return this;
        }

        public Builder setB(String b) {
            this.b = b;
            return this;
        }
        public Builder setC(String c) {
            this.c = c;
            return this;
        }

        public Speech build() {
            return new Speech(this);
        }
    }

    // 构造器
    private Speech(Builder builder) {
        this.headMaster = builder.headMaster;
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
    }
}

public class BuilderDemo {
    public static void main(String[] args) {
        // 只记录班主任和同学 A 的发言
        Speech speech1 = new Speech.Builder("班主任的发言").setA("同学A的发言").build();
        // 记录班主任, 同学 A, 同学 B 的发言
        Speech speech2 = new Speech.Builder("班主任的发言").
                setA("同学A的发言").
                setB("同学B的发言").
                build();
    }
}
