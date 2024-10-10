package com.esame.kit.model.dao;

import com.esame.kit.model.dao.cookieImpl.COOKIEFactory;
import com.esame.kit.model.dao.mysqlImpl.MYSQLJDBCIMPLFactory;

import java.util.Map;

public abstract class DAOFactory {
    // list of Dao types supported by the factory

    // different mode initialisation
    public static  final  String MYSQLJDBCIMPL="MYSQLJDBCIMPLFactory";
    public static final String COOKIEIMPL = "COOKIEIMPL";


    public abstract void beginTransaction();
    public  abstract  void commitTransaction();
    public  abstract  void rollbackTransaction();
    public abstract  void closeTransaction();

    public  abstract  UserDAO getUserDAO();

    public abstract  TemplateDAO getTemplateDAO();

    public  abstract  CommentDAO getCommentDAO();

    public  abstract  LikeDAO getLikeDAO();

    public  abstract  NoteDAO getNoteDAO();

    public abstract PrenotazioneDAO getPrenotazioneDAO();

    public static DAOFactory getDAOFactory(String withFactory, Map factoryParameters){
        if(withFactory.equals(MYSQLJDBCIMPL)){
            return new MYSQLJDBCIMPLFactory(factoryParameters);
        }else  if (withFactory.equals(COOKIEIMPL)){
            return new COOKIEFactory(factoryParameters);
        }else{
            return null;
        }
    }
}
