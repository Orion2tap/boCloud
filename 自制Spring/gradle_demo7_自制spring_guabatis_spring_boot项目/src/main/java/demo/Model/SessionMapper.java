package demo.Model;


import demo.guaBatis.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface SessionMapper {

    void add(int userID, String uuid);

    // lastInsertID mysql 返回的是 BigInteger
    BigInteger lastInsertID();

    int findUser(String sessionID);

    List<Session> all();
    Session findSession(Integer id);

}
