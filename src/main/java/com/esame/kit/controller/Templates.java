package com.esame.kit.controller;

import com.esame.kit.model.dao.*;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.*;
import com.esame.kit.services.config.Configuration;
import com.esame.kit.services.logService.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Templates {
    public Templates(){}

    public static  void view (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            commonView(db, session, request,null,null,null);
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

    public  static void newTemplate (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            commonView(db,session,request,"templates/newTemplate",null,null);
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

    public static void create (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        User loggedUser = null;
        Template template= null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            loggedUser = sessionUserDAO.findLoggedUser();
            if(loggedUser != null) {
                loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
                // creo il nuovo template
                String title  = request.getParameter("title");
                String description = request.getParameter("description");
                String code = request.getParameter("code");
                String language = request.getParameter("language");
                String link = request.getParameter("link");

                try {
                    template = dbTemplateDAO.create(loggedUser, title, description, code, language,link);
                    db.commitTransaction();
                    template = dbTemplateDAO.findByTemplateID(template.getTemplateID());
                    template = getTemplate(db,session, template.getTemplateID());
                }catch (DuplicatedObjectException e){
                    applicationMessage = e.getMessage();
                    logger.log(Level.INFO,e.getMessage());
                }

                if(applicationMessage!=null){
                    commonView(db,session,request,"templates/newTemplate",null,null);
                    request.setAttribute("applicationMessage",applicationMessage);
                }
                else {
                    commonView(db, session, request, "templates/oneView", null, null);
                    request.setAttribute("template", template);
                }
            }
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

    public static void delete (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String  applicationMessage= null;
        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            Long templateID  = Long.valueOf(request.getParameter("templateID"));

            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            Template template = dbTemplateDAO.findByTemplateID(templateID);
            try {
                dbTemplateDAO.delete(template,null);
            }catch (NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, "Tentativo di cancellazione di template non esistente");
            }


            db.commitTransaction();
            commonView(db, session, request,null,null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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

    public static void showTemplate (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        List<Comment> comments = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();

            String templateID  = request.getParameter("templateID");
            template = dbTemplateDAO.findByTemplateID(Long.parseLong(templateID));
            comments = dbCommentDAO.getComments(null,template,"onLine","template");
            template = getTemplate(db,session, Long.valueOf(templateID));

            for (int i = 0; i < comments.size(); i++) {
                comments.set(i,getComment(db,comments.get(i).getCommentID()));
            }

            request.setAttribute("template",template);
            request.setAttribute("comments",comments);
            commonView(db,session,request,"templates/oneView",null,null);
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

    public static void edit (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            String templateID  = request.getParameter("templateID");
            template = dbTemplateDAO.findByTemplateID(Long.valueOf(templateID));
            template = getTemplate(db,session, Long.valueOf(templateID));
            request.setAttribute("template",template);
            commonView(db,session,request,"templates/editTemplate",null,null);

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

    public static void update (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        String applicationMessage=null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            String templateID  = request.getParameter("templateID");
            String title  = request.getParameter("title");
            String description = request.getParameter("description");
            String code = request.getParameter("code");
            String language = request.getParameter("language");
            String link = request.getParameter("link");

            template = dbTemplateDAO.findByTemplateID(Long.valueOf(templateID));

            template = new Template(Long.parseLong(templateID),template.getUser(),title,description,code,language,link);
            try {
                dbTemplateDAO.update(template);
                db.commitTransaction();
            }catch (NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            template = getTemplate(db,session, Long.valueOf(templateID));

            request.setAttribute("template",template);
            commonView(db,session,request,"templates/oneView",null,null);
            request.setAttribute("applicationMessage",applicationMessage);

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

    public static  void  createComment(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();


            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();

            String templateID  = request.getParameter("templateID");
            String userID  = request.getParameter("userID");
            String body  = request.getParameter("comment");

            template = dbTemplateDAO.findByTemplateID(Long.valueOf(templateID));
            User user = dbUserDAO.findUser("id", Long.valueOf(userID),null,null);
            dbCommentDAO.create(template,user,body);
            db.commitTransaction();

            template = getTemplate(db,session,template.getTemplateID());
            request.setAttribute("template",template);
            commonView(db,session,request,"templates/oneView",null,null);

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

    public  static  void  deleteComment (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        Comment comment = null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();


                String templateID  = request.getParameter("templateID");
                String commentID  = request.getParameter("commentID");
                template = dbTemplateDAO.findByTemplateID(Long.valueOf(templateID));

                if (template!=null) {
                    comment = dbCommentDAO.getCommentMode(null,null,Long.valueOf(commentID),null);
                    try{
                        dbCommentDAO.delete(comment,null);
                        db.commitTransaction();
                    }catch (NonExistObjectException e){
                        applicationMessage = e.getMessage();
                        logger.log(Level.INFO, "Tentativo di cancellazione di commento non esistente");
                    }

                    template = getTemplate(db,session, template.getTemplateID());
                }

            request.setAttribute("template",template);
            commonView(db,session,request,"templates/oneView",null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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

    public static  void ToggleLike (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            LikeDAO dbLikeDAO = db.getLikeDAO();

            Long userID = Long.valueOf(request.getParameter("userID"));
            String classType = request.getParameter("classType");
            Long valueID = Long.valueOf(request.getParameter("valueID"));


            try {
                Like like = dbLikeDAO.getLikes(userID,classType,valueID);
                if (like == null)
                    dbLikeDAO.create(userID,classType,valueID);
                else dbLikeDAO.delete(like);
                db.commitTransaction();
            }catch (DuplicatedObjectException | NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            commonView(db,session,request,null,null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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

    public static  void commentToggleLike (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;
        List<Comment> comments = new ArrayList<>();
        Template template = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            LikeDAO dbLikeDAO = db.getLikeDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();

            Long userID = Long.valueOf(request.getParameter("userID"));
            String classType = request.getParameter("classType");
            Long valueID = Long.valueOf(request.getParameter("valueID"));
            String templateID  = request.getParameter("templateID");


            try {
                Like like = dbLikeDAO.getLikes(userID,classType,valueID);
                if (like == null)
                    dbLikeDAO.create(userID,classType,valueID);
                else dbLikeDAO.delete(like);
                db.commitTransaction();

                template = dbTemplateDAO.findByTemplateID(Long.parseLong(templateID));
                comments = dbCommentDAO.getComments(null, template, "onLine", "template");
                template = getTemplate(db,session, Long.valueOf(templateID));

                for (int i = 0; i < comments.size(); i++) {
                    comments.set(i,getComment(db,comments.get(i).getCommentID()));
                }
            }catch (DuplicatedObjectException | NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }


            request.setAttribute("template",template);
            request.setAttribute("comments",comments);
            commonView(db,session,request,"templates/oneView",null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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

    public static void searchByLanguage (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            String language  = request.getParameter("language");

            if(language!=null && !language.equals("all"))
                commonView(db, session, request,null,language,null);
            else commonView(db, session, request,null,null,null);

            request.setAttribute("search",language);
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

    public static void searchByTitle (HttpServletRequest request, HttpServletResponse response) {
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

            String search  = request.getParameter("search");

            commonView(db, session, request,null,null,search);
            request.setAttribute("search","Risultati della ricerca << "+search+" >>");
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

    public  static  void noteTemplate(HttpServletRequest request , HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        List<Comment> comments = null;
        String applicationMessage =null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();
            NoteDAO dbNoteDAO = db.getNoteDAO();

            String templateID  = request.getParameter("templateID");
            String userID  = request.getParameter("userID");
            String note  = request.getParameter("note");

            template = dbTemplateDAO.findByTemplateID(Long.parseLong(templateID));
            comments = dbCommentDAO.getComments(null,template,"onLine","template");
            template = getTemplate(db,session, Long.valueOf(templateID));

            User user = dbUserDAO.findUser("id",Long.parseLong(userID),null,null);

            try {
                if (note != null) {
                    Note noteObject = dbNoteDAO.getNote("template_user", null, user, template);
                    if (noteObject != null) {
                        dbNoteDAO.update(noteObject, Long.parseLong(note));
                    } else {
                        dbNoteDAO.create(user, template, Long.parseLong(note));
                    }
                    db.commitTransaction();
                }
            }catch (DuplicatedObjectException | NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            template = getTemplate(db,session, Long.valueOf(templateID));


            request.setAttribute("template",template);
            request.setAttribute("comments",comments);
            commonView(db,session,request,"templates/oneView",null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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


    public  static  void deleteNoteTemplate(HttpServletRequest request , HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        List<Comment> comments = null;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();
            NoteDAO dbNoteDAO = db.getNoteDAO();

            String templateID  = request.getParameter("templateID");
            String note  = request.getParameter("note");

            template = dbTemplateDAO.findByTemplateID(Long.parseLong(templateID));
            comments = dbCommentDAO.getComments(null,template,"onLine","template");
            template = getTemplate(db,session, Long.valueOf(templateID));

            try {
                if (note != null) {
                    Note noteObject = dbNoteDAO.getNote(null, Long.parseLong(note), null, null);
                    if (noteObject != null) {
                        dbNoteDAO.delete(noteObject);
                        db.commitTransaction();
                    }
                }
            }catch (NonExistObjectException e){
                applicationMessage = e.getMessage();
                logger.log(Level.INFO,e.getMessage());
            }
            template = getTemplate(db,session, Long.valueOf(templateID));


            request.setAttribute("template",template);
            request.setAttribute("comments",comments);
            commonView(db,session,request,"templates/oneView",null,null);
            request.setAttribute("applicationMessage",applicationMessage);
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

    public static void PrenotaRicevimento(HttpServletRequest request,HttpServletResponse response ){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Template template= null;
        List<Comment> comments = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            CommentDAO dbCommentDAO = db.getCommentDAO();
            NoteDAO dbNoteDAO = db.getNoteDAO();

            String templateID  = request.getParameter("templateID");
            String userID  = request.getParameter("userID");
            String author  = request.getParameter("authorID");


            template = dbTemplateDAO.findByTemplateID(Long.parseLong(templateID));
            template = getTemplate(db,session, Long.valueOf(templateID));

            User  mittente  = dbUserDAO.findUser("id",Long.parseLong(userID),null,null);
            User destinatario = dbUserDAO.findUser("id",Long.parseLong(author),null,null);
            request.setAttribute("template",template);
            request.setAttribute("mittente",mittente);
            request.setAttribute("destinatario",destinatario);

            commonView(db,session,request,"templates/prenotazioneTemplate",null,null);
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

    private static Template getTemplate(DAOFactory db,DAOFactory session, Long templateID) {
        Template template ;
        TemplateDAO dbTemplateDAO =db.getTemplateDAO();
        UserDAO dbUserDAO=db.getUserDAO();
        UserDAO sessionDAO = session.getUserDAO();
        CommentDAO dbCommentDAO=db.getCommentDAO();
        LikeDAO dbLikeDAO =db.getLikeDAO();
        NoteDAO dbNoteDAO=db.getNoteDAO();
        PrenotazioneDAO dbPrenotazioneDAO =db.getPrenotazioneDAO();

        User loggerUser = sessionDAO.findLoggedUser();
        loggerUser = dbUserDAO.findUser("id",loggerUser.getUserId(),null,null);

        if(loggerUser != null) {

            template = dbTemplateDAO.findByTemplateID(templateID);
            template.setUser(dbUserDAO.findUser("id", template.getUser().getUserId(), null, null));
            List<Comment> temp = dbCommentDAO.getComments(null, template, "onLine", "template");
            Comment[] comments = temp.toArray(new Comment[temp.size()]);

            for (int i = 0; i < comments.length; i++) {
                comments[i] = getComment(db,comments[i].getCommentID());
            }

            template.setComments(comments);
            List<Like> tempLikes = dbLikeDAO.getAllLikesByClassTypeAndID("Template", template.getTemplateID());
            Like[] likes = tempLikes.toArray(new Like[tempLikes.size()]);
            template.setLikes(likes);

            List<Note> tempNotes = dbNoteDAO.getNotes(null, null, template);
            Note[] notes = tempNotes.toArray(new Note[tempNotes.size()]);
            template.setNotes(notes);

            List<Prenotazione> tempPrenotazioni = dbPrenotazioneDAO.getPrenotazioni("user", null, null, null, null, loggerUser, null, null);
            Prenotazione[] prenotazioni = tempPrenotazioni.toArray(new Prenotazione[tempPrenotazioni.size()]);
            template.setPrenotazioni(prenotazioni);

            float noteMoyen = 0;
            for (Note note : notes) {
                noteMoyen += note.getNoteValue();
            }
            if (notes.length > 0) noteMoyen = noteMoyen / notes.length;
            else noteMoyen = 0;

            template.setTotNote(noteMoyen);
            return template;
        }else{
            return null;
        }
    }

    private static Comment getComment(DAOFactory db, Long commentID ) {
        Comment comment;
        TemplateDAO dbTemplateDAO = db.getTemplateDAO();
        UserDAO dbUserDAO= db.getUserDAO();
        CommentDAO dbCommentDAO = db.getCommentDAO();
        LikeDAO dbLikeDAO=db.getLikeDAO();

        comment = dbCommentDAO.getCommentMode("commentID",null,commentID,null);

        comment.setTemplate(dbTemplateDAO.findByTemplateID(comment.getTemplate().getTemplateID()));
        comment.setUser(dbUserDAO.findUser("id",comment.getUser().getUserId(),null,null));

        List<Like> tempLikes = dbLikeDAO.getAllLikesByClassTypeAndID("Comment", comment.getCommentID());
        Like[] likes = tempLikes.toArray(new Like[tempLikes.size()]);
        comment.setLikes(likes);
        return comment;
    }

    private static void commonView(DAOFactory db, DAOFactory session, HttpServletRequest request,String viewUrl,String language,String search) {
        if(viewUrl == null){
            viewUrl = "templates/view";
        }

        Logger logger = LogService.getApplicationLogger();
        try {
            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            User loggedUser = sessionUserDAO.findLoggedUser();
            List<Template> templates = null;
            String applicationMessage = null;
            if (loggedUser != null) {
                loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
                templates = dbTemplateDAO.getAllTemplates("onLine", language, search);

                for (int i = 0; i < templates.size(); i++) {
                    templates.set(i, getTemplate(db,session, templates.get(i).getTemplateID()));
                }
            } else {
                applicationMessage = "user not found. Login please";
            }

            db.commitTransaction();
            session.commitTransaction();
            request.setAttribute("templates", templates);
            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            if (applicationMessage != null){
                request.setAttribute("applicationMessage", applicationMessage);
                request.setAttribute("viewUrl", "home/view");
            }
            else request.setAttribute("viewUrl", viewUrl);
        }catch (Exception e) {
            logger.log(Level.SEVERE, "controller template error ", e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

}
