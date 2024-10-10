package com.esame.kit.model.dao.cookieImpl;

import com.esame.kit.model.dao.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public class COOKIEFactory extends DAOFactory {
    private Map factoryParameter;

    // nei cookie parliamo di resquest e response HttpServlet
    private HttpServletRequest request;
    private HttpServletResponse response;

    public COOKIEFactory(Map factoryParameter){
        this.factoryParameter=factoryParameter;
    }

    @Override
    public void beginTransaction() {
        try {
            this.request = (HttpServletRequest) factoryParameter.get("request");
            this.response = (HttpServletResponse) factoryParameter.get("response");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }

    @Override
    public void closeTransaction() {

    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOCOOKIEIMPL(request,response);
    }

    @Override
    public TemplateDAO getTemplateDAO() {
        return  null;
    }

    @Override
    public CommentDAO getCommentDAO() {
        return null;
    }

    @Override
    public LikeDAO getLikeDAO() {
        return null;
    }

    @Override
    public NoteDAO getNoteDAO() {
        return null;
    }

    @Override
    public PrenotazioneDAO getPrenotazioneDAO() {
        return null;
    }
}
