package com.esame.kit.model.mo;

import java.util.Arrays;
import java.util.List;

/**
 * Relazioni :
 *      User - template --> 1 : N
 *      User - Note --> 1 : N
 *      User - Prenotazione --> 1 : N
 *      User - Comment --> 1 : N
 *      User-Like --> 1:N
 */

public class User {

    public static final List<String> ROLE= Arrays.asList("admin","creatore","lettore");

    private  Long userId;
    private  String username;
    private String password;
    private  String firstname;
    private String secondName;

    private String languagesCodePreferences;
    private  String email;

    private  String role;

    private boolean deleteState;


    private  Template[] templates;

    private  Comment[] comments;

    private  Note[] notes;

    private Prenotazione[] prenotazioni;

    private Like[] likes;

    public User () {}

    public User (long userId, String username, String password, String firstName, String secondName, String languagesCodePreferences,String email,String role){
        this.userId = userId;
        this.username=username;
        this.password=password;
        this.firstname=firstName;
        this.secondName=secondName;
        this.languagesCodePreferences=languagesCodePreferences;
        this.email = email;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLanguagesCodePreferences() {
        return languagesCodePreferences;
    }

    public void setLanguagesCodePreferences(String languagesCodePreferences) {
        this.languagesCodePreferences = languagesCodePreferences;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return this.role+" | "+this.username+" | "+this.userId+" | "+this.firstname+" | "+this.secondName+" | "+this.email+" | "+this.deleteState+" | "+this.languagesCodePreferences+" | "+this.password;
    }

    public Template[] getTemplates() {
        return templates;
    }

    public void setTemplates(Template[] templates) {
        this.templates = templates;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(boolean deleteState) {
        this.deleteState = deleteState;
    }

    public boolean isDelete(){
        return this.deleteState;
    }

    public boolean isCreatore() {
        return this.role.toLowerCase().equals("creatore");
    }
    public boolean isAdmin() {
        return this.role.toLowerCase().equals("admin");
    }
    public boolean isLettore() {
        return this.role.toLowerCase().equals("lettore");
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

    public Prenotazione[] getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(Prenotazione[] prenotazioni) {
        this.prenotazioni = prenotazioni;
    }
    public Like[] getLikes() {
        return likes;
    }

    public void setLikes(Like[] likes) {
        this.likes = likes;
    }
}
