package demo.testNewInstance;

public class UserWithConstructor {
    String username;
    String password;

    public UserWithConstructor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserWithConstructor{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
