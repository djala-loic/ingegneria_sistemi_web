package com.esame.kit.model.mo;

/**
 * Relazioni :
 *     Comment - user --> N : 1
 *     Comment - Template --> N : 1
 *     Comment - Like --> 1 : N
 */

public class Comment {
    private Template template;
    private User user;
    private  Long commentID;
    private  String body;

    private  boolean deleteState;
    private  String createdAt;
    private  Like[] likes;

    public  Comment(){};
    public  Comment (long commentID,Template template,User user, String body) {
        this.body= body;
        this.user= user;
        this.template= template;
        this.commentID= commentID;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Like[] getLikes() {
        return likes;
    }

    public void setLikes(Like[] likes) {
        this.likes = likes;
    }

    public boolean isDeleteState() {
        return deleteState;
    }

    public void setDeleteState(boolean deleteState) {
        this.deleteState = deleteState;
    }
}
