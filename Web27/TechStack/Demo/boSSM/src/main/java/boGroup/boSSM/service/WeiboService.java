package boGroup.boSSM.service;

import boGroup.boSSM.mapper.WeiboMapper;
import boGroup.boSSM.model.WeiboModel;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class WeiboService {
    WeiboMapper mapper;

    public WeiboService(WeiboMapper mapper) {
        // 依赖注入: Spring 自动 new 一个 WeiboMapper 接口的实例 mapper 传入 WeiboService 的构造函数
        this.mapper = mapper;
    }

    public WeiboModel add(String content) {
        WeiboModel m = new WeiboModel();
        m.setContent(content);
//        Utility.log("m before insert id %s", m.getId());    // id = null
        mapper.insert(m);
//        Utility.log("m after insert id %s", m.getId());     // id = 数据库取回的id
        return m;
    }

    public void update(Integer id, String content) {
        WeiboModel m = new WeiboModel();
        m.setId(id);
        m.setContent(content);
        mapper.update(m);
    }

    public void deleteById(Integer id) {
        mapper.delete(id);
    }

    public  WeiboModel findById(Integer id) {
        return mapper.select(id);
    }

    public ArrayList<WeiboModel> all() {
        return mapper.selectAll();
    }
}
