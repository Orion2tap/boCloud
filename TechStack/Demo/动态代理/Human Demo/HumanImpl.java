
// 该接口的一个实现类 HumanImpl
public class HumanImpl implements Human {
    @Override
    public String eat (String food) {
        String result = "eat " + food;
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        Human humanImpl = new HumanImpl();
        humanImpl.eat("rice");
    }
}