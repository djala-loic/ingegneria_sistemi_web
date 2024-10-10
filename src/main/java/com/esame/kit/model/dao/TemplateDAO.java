package com.esame.kit.model.dao;

import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.util.List;

public interface TemplateDAO{
    public abstract Template create (User user, String title, String description, String code, String language,String link) throws DuplicatedObjectException;

    public  abstract Template findByTemplateID(Long templateID);

    public abstract void update (Template template) throws NonExistObjectException;

    /**
     * @param mode String
     *             null o onLine ,onLine,offLine
     * */
    public  abstract  List<Template> getAllTemplateByUserID(User user,String mode,String language, String search);

    /**
     * @param mode String
     *             all,onLine,offLine
     * */
    public abstract List<Template> getAllTemplates(String mode,String language, String search);

    /**
     * @param mode String
     *             null o simple , forever
     * */
    public  abstract void delete (Template template,String mode) throws NonExistObjectException;

    public  abstract void restore(Template template) throws NonExistObjectException;
}
