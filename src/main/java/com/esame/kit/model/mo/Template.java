package com.esame.kit.model.mo;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Relazioni :
 *     Template - user --> N : 1
 *     Template - Comment --> 1 : N
 *     Template - Prenotazione --> 1 : N
 *     Template - Note --> 1 : N
 */

public class Template {

    public static final List<String> LANGUAGELIST= Arrays.asList("vueJs","reactJs","nextJs","others");
    private Long templateID;
    private User user;
    private String title;
    private String description;
    private String code;
    private String language;

    private String link;

    private boolean  deleteState;

    private String createdAt;

    private Float TotNote;

    // Relazioni
    private Like[] likes;

    private Comment[] comments;

    private  Note[] notes;

    private  Prenotazione[] prenotazioni;

    public  Template(){}
    public  Template (Long templateID, User user, String titre, String description, String code, String language,String link){
        this.templateID = templateID;
        this.user = user;
        this.title= titre;
        this.description = description;
        this.code  = code;
        this.language = language;
        this.link = link;
    }

    public Long getTemplateID() {
        return templateID;
    }

    public void setTemplateID(long templateID) {
        this.templateID = templateID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(boolean deleteState) {
        this.deleteState = deleteState;
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

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public Note[] getNotes() {
        return notes;
    }

    public Note getNotesByUser(User user, Template template){
        for (Note n: this.notes) {
            if((n.getUser().getUserId() == user.getUserId())
                    &&(template.getTemplateID() == n.getTemplate().getTemplateID())){
                return n;
            }
        }
        return null;
    }
    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

    public String getTotNote() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        return nf.format(this.TotNote);
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static boolean isPrenotedByUser (User user,Template template){
        for (Prenotazione prenotazione: template.getPrenotazioni()) {
            if(prenotazione.getUser().getUserId() == user.getUserId() && prenotazione.getTemplate().getTemplateID() == template.getTemplateID()){
                return  true;
            }
        }
        return false;
    }

    public void setTotNote(Float totNote) {
        TotNote = totNote;
    }

    public Prenotazione[] getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Prenotazione[] prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
}
