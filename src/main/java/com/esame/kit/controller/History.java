package com.esame.kit.controller;

import com.esame.kit.model.dao.*;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Comment;
import com.esame.kit.model.mo.Like;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;
import com.esame.kit.services.config.Configuration;
import com.esame.kit.services.logService.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class History {
    public History(){}


    public static  void view (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            db.beginTransaction();

            commonView(db,session, request,"templates/history",null,null);


        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }

    }

    public  static  void restore (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            assert db != null;
            db.beginTransaction();

            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            Long templateID = Long.valueOf(request.getParameter("templateID"));
            template = dbTemplateDAO.findByTemplateID(templateID);
            try {
                dbTemplateDAO.restore(template);
                db.commitTransaction();
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            request.setAttribute("applicationMessage",applicationMessage);
            commonView(db,session,request,"templates/history",null,null);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void delete (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        User loggedUser = null;
        List<Template> templates_offlines = null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            assert db != null;
            db.beginTransaction();

            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            String templateID = request.getParameter("templateID");

            UserDAO sessionUserDAO = session.getUserDAO();

            loggedUser = sessionUserDAO.findLoggedUser();

            templates_offlines = dbTemplateDAO.getAllTemplateByUserID(loggedUser,"offLine",null,null);

            try {
                if (templateID == null) {
                    for (Template template : templates_offlines) {
                        dbTemplateDAO.delete(template, "forever");
                        db.commitTransaction();
                    }
                } else {
                    dbTemplateDAO.delete(dbTemplateDAO.findByTemplateID(Long.valueOf(templateID)), "forever");
                    db.commitTransaction();
                }
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            request.setAttribute("applicationMessage",applicationMessage);
            commonView(db,session,request,"templates/history",null,null);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void deleteComment (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;
        List<Comment> comments_offlines = null;
        Comment comment= null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            assert db != null;
            db.beginTransaction();

            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();

            User loggedUser = sessionUserDAO.findLoggedUser();
            
            loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
            String commentID = request.getParameter("commentID");
            comments_offlines = dbCommentDAO.getComments(loggedUser,null,"offLine","user");

            try {
                if (commentID == null) {
                    for (Comment temp : comments_offlines) {
                        dbCommentDAO.delete(temp, "forever");
                        db.commitTransaction();
                    }
                } else {
                    comment = dbCommentDAO.getCommentMode(null, null, Long.valueOf(commentID), null);
                    dbCommentDAO.delete(comment, "forever");
                    db.commitTransaction();
                }
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            request.setAttribute("applicationMessage",applicationMessage);
            commonView(db,session,request,"templates/history",null,null);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void restoreComment (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Comment comment= null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            assert db != null;
            db.beginTransaction();

            CommentDAO dbCommentDAO = db.getCommentDAO();

            Long commentID = Long.valueOf(request.getParameter("commentID"));
            comment = dbCommentDAO.getCommentMode(null,null,commentID,null);
            try {
                dbCommentDAO.restore(comment);
                db.commitTransaction();
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            request.setAttribute("applicationMessage",applicationMessage);
            commonView(db,session,request,"templates/history",null,null);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    private static void commonView(DAOFactory db, DAOFactory session, HttpServletRequest request, String viewUrl,String language,String search) {
        if(viewUrl==null){
            viewUrl ="home/view";
        }

        Logger logger = LogService.getApplicationLogger();
        try {
            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();
            String applicationMessage = null;
            User loggedUser = null;
            List<Template> templates_onlines = null;
            List<Template> templates_offlines = null;
            List<Comment> comments_offlines = new ArrayList<>();

            loggedUser = sessionUserDAO.findLoggedUser();
            if (loggedUser != null) {
                loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
                templates_offlines = dbTemplateDAO.getAllTemplateByUserID(loggedUser, "offLine", language, search);
                templates_onlines = dbTemplateDAO.getAllTemplateByUserID(loggedUser, "onLine", language, search);
                for (int i = 0; i < templates_offlines.size(); i++) {
                    templates_offlines.set(i, getTemplate(dbTemplateDAO, dbUserDAO, db.getCommentDAO(), db.getLikeDAO(), templates_offlines.get(i).getTemplateID()));
                }
                for (int i = 0; i < templates_onlines.size(); i++) {
                    templates_onlines.set(i, getTemplate(dbTemplateDAO, dbUserDAO, db.getCommentDAO(), db.getLikeDAO(), templates_onlines.get(i).getTemplateID()));
                }

                for (Template template : templates_offlines) {
                    List<Comment> temp = dbCommentDAO.getComments(null,template,"offLine","template");
                    if (temp!=null && temp.size()>0)
                        comments_offlines.addAll(temp);
                }

                /*comments_offlines = dbCommentDAO.getComments(loggedUser, null, "offLine", "user");*/
                for (int i = 0; i < comments_offlines.size(); i++) {
                    comments_offlines.set(i, getComment(dbTemplateDAO, dbUserDAO, db.getCommentDAO(), db.getLikeDAO(), comments_offlines.get(i).getCommentID()));
                }

            } else {
                applicationMessage = "user not found. Login please";
            }
            db.commitTransaction();
            request.setAttribute("templates_offlines", templates_offlines);
            request.setAttribute("comments_offlines", comments_offlines);
            request.setAttribute("templates_onlines", templates_onlines);
            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            if (applicationMessage != null) request.setAttribute("viewUrl", viewUrl);
            else request.setAttribute("viewUrl", viewUrl);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller template error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    private static Comment getComment(TemplateDAO dbTemplateDAO, UserDAO dbUserDAO, CommentDAO dbCommentDAO, LikeDAO dbLikeDAO, Long commentID ) {
        Comment comment  ;

        comment = dbCommentDAO.getCommentMode("commentID",null,commentID,null);

        comment.setTemplate(dbTemplateDAO.findByTemplateID(comment.getTemplate().getTemplateID()));
        comment.setUser(dbUserDAO.findUser("id",comment.getUser().getUserId(),null,null));
        List<Like> tempLikes = dbLikeDAO.getAllLikesByClassTypeAndID("Comment", comment.getCommentID());
        Like[] likes = tempLikes.toArray(new Like[tempLikes.size()]);
        comment.setLikes(likes);
        return comment;
    }

    private static Template getTemplate(TemplateDAO dbTemplateDAO, UserDAO dbUserDAO, CommentDAO dbCommentDAO,LikeDAO dbLikeDAO, Long templateID) {
        Template template ;

        template = dbTemplateDAO.findByTemplateID(templateID);
        template.setUser(dbUserDAO.findUser("id",template.getUser().getUserId(),null,null));
        List<Comment> temp = dbCommentDAO.getComments(null ,template,"onLine","template");
        Comment [] comments = temp.toArray(new Comment[temp.size()]);

        for (int i = 0; i < comments.length; i++) {
            comments[i] =  getComment(dbTemplateDAO, dbUserDAO, dbCommentDAO, dbLikeDAO, comments[i].getCommentID());
        }

        template.setComments(comments);
        List<Like> tempLikes = dbLikeDAO.getAllLikesByClassTypeAndID("Template", template.getTemplateID());
        Like[] likes = tempLikes.toArray(new Like[tempLikes.size()]);
        template.setLikes(likes);
        return template;
    }


}
