package com.esame.kit.model.mo;

import java.util.List;

/**
 * Relazioni :
 *     Note - User --> N : 1
 *     Note - Template --> N : 1
 */
public class Note {
    public static final int MIN = 0;
    public static final int MAX = 5;
    public static final  int STEP = 1;

    private Long noteID;
    private Long noteValue;
    private User user;
    private Template template;
    private String createdAt;

    public Note(){}
    public  Note(Long noteID,Long noteValue,User user,Template template){
        this.noteID= noteID;
        this.noteValue = noteValue;
        this.user = user;
        this.template = template;
    }

    public Long getNoteID() {
        return noteID;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    public Long getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(Long noteValue) {
        this.noteValue = noteValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public static  boolean isNotedForUser(Long userID, Long templateID, List<Note> notes){
        for (Note note: notes) {
            if(note.getUser().getUserId() == userID
                    && note.getTemplate().getTemplateID() == templateID){
                return true;
            }
        }
        return  false;
    }
}
