package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Note;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.util.List;

public interface NoteDAO {

    /*
    * @params mode String
    * puo valere noteID,user,template,
    * */
    public abstract Note getNote(String mode, Long noteID, User user, Template template);


    /*
     * @params mode String
     * puo valere user,template,
     * */
    public abstract List<Note> getNotes(String mode,  User user, Template template);

    public abstract Note create(User user,Template template,Long note) throws DuplicatedObjectException;

    public abstract void update(Note note,Long value) throws NonExistObjectException;

    public abstract void  delete(Note note) throws NonExistObjectException;
}
