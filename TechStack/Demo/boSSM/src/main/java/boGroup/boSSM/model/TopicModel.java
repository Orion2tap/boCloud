package boGroup.boSSM.model;

import java.util.ArrayList;

public class TopicModel extends BaseModel {
    private Integer id;
    private String content;
    private String title;
    private Integer userId;
    private Integer boardId;
    private String createdTime;
    private String updatedTime;

    private ArrayList<TopicCommentModel> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public ArrayList<TopicCommentModel> getComments() {
        return comments;
    }

    public void setComments(ArrayList<TopicCommentModel> comments) {
        this.comments = comments;
    }
}
