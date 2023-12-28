package demo;

import demo.Common.Utility;
import demo.Model.Session;
import demo.Model.SessionMapper;
import java.util.List;




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

    public static void testSQLSessionReturnList() {
        SQLSession sqlSession = new SQLSession();
        SessionMapper mapper = sqlSession.getMapper(SessionMapper.class);
        Utility.logFormat("toString %s", mapper);
        List<Session> sessionList = mapper.all();
        Session session = sessionList.get(0);
        ensure(session.id == 1, String.format("testSQLSessionReturnList id %s", session.id));
        ensure(session.sessionID.equals("testSession"), String.format("testSQLSessionReturnList sessionID %s", session.sessionID));
        ensure(session.userID == 1, String.format("testSQLSessionReturnList userID %s", session.userID));
    }

    public static void main(String[] args) {
        testSQLSessionReturnList();
    }

}
