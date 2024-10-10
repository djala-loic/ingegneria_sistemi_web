package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Prenotazione;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.util.List;

public interface PrenotazioneDAO {
    public abstract Prenotazione create(User user, User destinatario, Template template , String oggetto, String body, String dataOra, String viaIncontro) throws DuplicatedObjectException;

    /**
     * @param mode String
     *            puo valere null,forever
     * */
    public abstract void delete(String mode,Prenotazione prenotazione) throws NonExistObjectException;

    public abstract  void restore(Prenotazione prenotazione) throws NonExistObjectException;

    public abstract  void update(Prenotazione prenotazione) throws NonExistObjectException;

    /**
     * @param  mode String
     *             puo valere "PrenotazioneID", "user_template"
     * */
    public abstract Prenotazione getPrenotazione(String mode, Long PrenotazioneID, User user, Template template);

    /**
     * @param  mode String
     *              puo valere "user" ovvero il mittente,
     *              "destinatario", "template",
     *              "user_destinatario","destinatario_template"
     * */
    public abstract List<Prenotazione> getPrenotazioni(String mode, Boolean deleteState,String platform, String search,Boolean accept, User user, Template template, User destinatario);
}
