package com.esame.kit.controller;

import com.esame.kit.model.dao.*;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.*;
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

public class Admin {
    public  Admin(){}

    public  static  void  view (HttpServletRequest request, HttpServletResponse response)  {
        DAOFactory session = null;
        DAOFactory db = null;
        String applicationMessage= null;
        Logger logger = LogService.getApplicationLogger();
        User loggedUser = null;
        List<User> users = null;
        

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

            process(db,session,request,"admin/view",null,null,null);

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

    public  static  void  templatesCommentsView (HttpServletRequest request, HttpServletResponse response)  {
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();



        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);assert db != null;
            db.beginTransaction();

            processTemplate(db,session,request,"admin/templatesComments/view",null,null,null);

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

    public static  void operationUser(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
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

            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();

            Long userID = Long.valueOf(request.getParameter("userID"));
            String operation = request.getParameter("operation");
            String role = request.getParameter("role");

            User loggedUser = sessionUserDAO.findLoggedUser();
            loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
            try {
                if (loggedUser != null) {
                    if (loggedUser.getRole().equals("admin")) {
                        if (userID != null && operation != null) {
                            User user = dbUserDAO.findUser("id", userID, null, null);
                            if (operation.equals("restore")) {
                                dbUserDAO.restoreUser(user);
                            }
                            if (operation.equals("delete")) {
                                dbUserDAO.delete(user, null);
                            }
                            if (operation.equals("deleteForever")) {
                                dbUserDAO.delete(user, "forever");
                            }

                            if (operation.equals("changeRole")) {
                                dbUserDAO.changeRole(user, role);
                            }
                            db.commitTransaction();
                            applicationMessage = "User con ID : " + user.getUserId() + "è stato restorato per bene";
                            request.setAttribute("applicationMessage", applicationMessage);
                        }

                    }
                }
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            request.setAttribute("applicationMessage",applicationMessage);
            process(db,session,request,"admin/view",null,null,null);

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

    public static void newUser(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameter);
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            db.beginTransaction();

            process(db,session,request,"admin/users/registerUserView",null,null,null);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller Admin error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static void insertUser(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        String applicationMessage= null;
        Logger logger = LogService.getApplicationLogger();
        User loggedUser = null;
        List<User> users = null;


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

            String firstName = request.getParameter("firstName");
            String secondName = request.getParameter("secondName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");

            loggedUser = sessionUserDAO.findLoggedUser();
            loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
            try {
                if (loggedUser != null) {
                    if (loggedUser.getRole().equals("admin")) {

                        User tmp = dbUserDAO.create(null, null, null, firstName, secondName, null, email, role);
                        db.commitTransaction();

                        tmp = dbUserDAO.findUser("id", tmp.getUserId(), null, null);
                        if (tmp != null) {
                            applicationMessage = "user created successfully";
                        } else {
                            applicationMessage = "user non è stato creato per bene ";
                        }
                    }
                }
            }catch (DuplicatedObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            request.setAttribute("applicationMessage",applicationMessage);

            process(db,session,request,"admin/view",null,null,null);

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

    public  static  void searchTemplate(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);assert db != null;
            db.beginTransaction();

            String language = request.getParameter("language");
            String search = request.getParameter("search");


            processTemplate(db,session,request,"admin/templatesComments/view",null,language,search);

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

    public  static  void searchByRole(HttpServletRequest request, HttpServletResponse response){
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


            String role = request.getParameter("role");
            String search = request.getParameter("search");


            process(db,session,request,"admin/view",null,role,search);
            request.setAttribute("search",((role !=null ?role : "")+" "+(search !=null ?search : "")).trim());
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

    public static  void operationTemplate(HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
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

            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            Long templateID = Long.valueOf(request.getParameter("templateID"));
            String operation = request.getParameter("operation");

            User loggedUser = sessionUserDAO.findLoggedUser();
            loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
            try {
                if (loggedUser != null) {
                    if (loggedUser.getRole().equals("admin")) {
                        if (templateID != null && operation != null) {
                            Template template = dbTemplateDAO.findByTemplateID(templateID);
                            if (operation.equals("restore")) {
                                dbTemplateDAO.restore(template);
                                db.commitTransaction();
                            }
                            if (operation.equals("delete")) {
                                dbTemplateDAO.delete(template,null);
                                db.commitTransaction();

                            }
                            if (operation.equals("deleteForever")) {
                                dbTemplateDAO.delete(template, "forever");
                                db.commitTransaction();
                            }

                            applicationMessage = "template con ID : " + template.getTemplateID() + "è stato "+operation+" per bene";
                            request.setAttribute("applicationMessage", applicationMessage);
                        }

                    }
                }
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            request.setAttribute("applicationMessage",applicationMessage);
            processTemplate(db,session,request,"admin/templatesComments/view",null,null,null);

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

    private static void process(DAOFactory db, DAOFactory session, HttpServletRequest request, String viewUrl, String mode, String role, String search) {
        if(viewUrl==null) {
            viewUrl = "home/view";
        }

        User loggedUser = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage= null;

        try {
            UserDAO sessionUserDAO = session.getUserDAO();
            loggedUser = sessionUserDAO.findLoggedUser();
            UserDAO dbUserDAO = db.getUserDAO();
            loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
            if (loggedUser != null) {
                if (loggedUser.isAdmin()) {
                    List<User> users = dbUserDAO.getAllUsers(mode,role,search);
                    db.commitTransaction();
                    request.setAttribute("users", users);
                } else {
                    viewUrl ="home/view";
                    applicationMessage = "You are not administrator";
                }
            }
            request.setAttribute("viewUrl", viewUrl);
            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            if (applicationMessage!=null)
                request.setAttribute("applicationMessage", applicationMessage);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            if (session != null) session.rollbackTransaction();
            if (db != null) db.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    private static void processTemplate(DAOFactory db, DAOFactory session, HttpServletRequest request, String viewUrl, String mode, String language, String search) {
        if(viewUrl==null) {
            viewUrl = "home/view";
        }

        User loggedUser = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage= null;

        try {
            UserDAO sessionUserDAO = session.getUserDAO();
            loggedUser = sessionUserDAO.findLoggedUser();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();

            loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
            if (loggedUser != null) {
                if (loggedUser.getRole().equals("admin")) {
                    List<Template> templates = dbTemplateDAO.getAllTemplates(mode,language,search);
                    db.commitTransaction();

                    for (int i = 0; i < templates.size(); i++) {
                        templates.set(i, getTemplate(db,session, templates.get(i).getTemplateID()));
                    }
                    request.setAttribute("templates", templates);
                } else {
                    viewUrl ="home/view";
                    applicationMessage = "You are   not administrator";
                }
            }
            request.setAttribute("viewUrl", viewUrl);
            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            if (applicationMessage!=null)
                request.setAttribute("applicationMessage", applicationMessage);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            if (session != null) session.rollbackTransaction();
            if (db != null) db.rollbackTransaction();
            throw new RuntimeException(e);
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

        if(loggerUser != null && loggerUser.isAdmin()) {

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

            List<Prenotazione> tempPrenotazioni = dbPrenotazioneDAO.getPrenotazioni("template", null, null, null, null, null, template, null);
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
        Comment comment  ;
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


}
