package boMVC.models;

public class Message {
    public String author;
    public String message;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
