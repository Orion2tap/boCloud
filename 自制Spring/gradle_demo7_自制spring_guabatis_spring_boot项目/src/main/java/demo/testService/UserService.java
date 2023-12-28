package demo.testService;


import demo.Model.Session;
import demo.Model.SessionMapper;
import demo.guaSpring.Service;

@Service
public class UserService {
    private SessionMapper sessionMapper;

    public UserService(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    public Session findSession(Integer id) {
        return sessionMapper.findSession(id);
    }
}
