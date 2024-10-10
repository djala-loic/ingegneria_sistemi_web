package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Comment;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.util.List;

public interface CommentDAO {
    public abstract Comment create(Template template, User user, String body) ;

    public abstract  void update(Comment comment) throws NonExistObjectException;

    public  abstract  void delete(Comment comment,String mode) throws NonExistObjectException;


    Comment getCommentMode(String mode, Long userID, Long commentID, Long templateID);

    List<Comment> getComments(User user, Template template, String mode, String by);

    public abstract void  restore(Comment comment) throws NonExistObjectException;
}
