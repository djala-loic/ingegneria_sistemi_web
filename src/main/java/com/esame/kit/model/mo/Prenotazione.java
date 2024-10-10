package com.esame.kit.model.mo;

import java.util.Arrays;
import java.util.List;

/**
 * Relazioni :
 *     Prenotazione - User --> N : 1
 *     Prenotazione - Template --> 1 : N
 */

public class Prenotazione {

    public  static  final List<String> Platform = Arrays.asList("Google meet","Zoom");
    private Long prenotazioneID;
    private User user;
    private String oggetto;
    private Template template;
    private String body;
    private  User destinatario;
    private String dataOra;
    private String viaIncontro;
    private String createdAt;

    private  String response;

    private String link;

    private Boolean deleteState;

    private Boolean accept;

    public Prenotazione(){}

    public  Prenotazione(Long prenotazioneID,User user,User destinatario,Template template ,String oggetto, String body,String dataOra,String viaIncontro){
        this.user = user;
        this.oggetto = oggetto;
        this.body = body;
        this.dataOra = dataOra;
        this.destinatario = destinatario;
        this.viaIncontro = viaIncontro;
        this.template = template;
        this.prenotazioneID = prenotazioneID;
    }

    public Long getPrenotazioneID() {
        return prenotazioneID;
    }

    public void setPrenotazioneID(Long prenotazioneID) {
        this.prenotazioneID = prenotazioneID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDataOra() {
        return dataOra;
    }

    public void setDataOra(String dataOra) {
        this.dataOra = dataOra;
    }

    public String getViaIncontro() {
        return viaIncontro;
    }

    public void setViaIncontro(String viaIncontro) {
        this.viaIncontro = viaIncontro;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(User destinatario) {
        this.destinatario = destinatario;
    }

    public boolean isDeleteState() {
        return deleteState;
    }

    public void setDeleteState(boolean deleteState) {
        this.deleteState = deleteState;
    }

    public boolean getDeleteState() {
        return this.deleteState;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Boolean isAccepted(){
        return this.accept;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }
}

