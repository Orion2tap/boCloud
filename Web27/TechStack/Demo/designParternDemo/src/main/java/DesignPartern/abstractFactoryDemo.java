package DesignPartern;

abstract class Gun {
    public void fire() {
        this.trigger();
        System.out.println("发射");
    }

    abstract public void trigger();
}

class AK extends  Gun {

    public void trigger() {
        System.out.println("扣动 AK 扳机");
    }

}

class M4 extends Gun {

    public void trigger() {
        System.out.println("扣动 M4 扳机");
    }

}

abstract class GunFactory {
    public Gun getGun() {
        Gun gun = getGunObject();
        check(gun);
        return gun;
    }

    abstract public Gun getGunObject();

    abstract public void check(Gun gun);
}

class AKFactory extends GunFactory{
    @Override
    public Gun getGunObject() {
        return new AK();
    }

    @Override
    public void check(Gun gun) {
        System.out.println("check ak 扳机");
        System.out.println("check ak 弹匣");
    }

}

class M4Factory  extends  GunFactory{
    @Override
    public Gun getGunObject() {
        return new M4();
    }

    public void check(Gun gun) {
        System.out.println("check m4 枪管");
        System.out.println("check m4 瞄准器 ");
    }
}

public class abstractFactoryDemo {
    public static void main(String[] args) {
        AKFactory factory = new AKFactory();
        Gun gun = factory.getGun();
        gun.fire();
    }
}
