package boMVC.models;

public class Session {
    public Integer userId;
    public String sessionId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "Session{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
