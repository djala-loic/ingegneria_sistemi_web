package com.esame.kit.controller;

import com.esame.kit.model.dao.DAOFactory;
import com.esame.kit.model.dao.UserDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.mo.User;
import com.esame.kit.services.config.Configuration;
import com.esame.kit.services.logService.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home {

    public Home(){}

    public  static  void  view (HttpServletRequest request, HttpServletResponse response)  {

        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        try{

            Map<String,Object> sessionFactoryParameters = new HashMap<>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);

            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            assert session != null;
            session.beginTransaction();


            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            commonView(db,session,request);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            if (session != null) session.rollbackTransaction();
            if (db != null) db.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if (db != null) {
                db.closeTransaction();
            }
            if (session != null) {
                session.closeTransaction();
            }
        }
    }

    public  static  void  login (HttpServletRequest request, HttpServletResponse response) {
        DAOFactory session = null; 
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage= null;
        User loggedUser = null;
        try{
            Map<String,Object> sessionFactoryParameters = new HashMap<>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);

            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            if (session != null) {
                session.beginTransaction();
            }

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            if (db != null) {
                db.beginTransaction();
            }

            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = dbUserDAO.findUser("email",null,null,email);

            if(user==null || (user !=null && !user.getPassword().equals(password))){
                sessionUserDAO.delete(null,null);
                applicationMessage = "UserName e passWord Errati";
            }else{
                loggedUser = sessionUserDAO.create(user.getUserId(),user.getUsername(),user.getPassword(),user.getFirstname(),user.getSecondName(),user.getLanguagesCodePreferences(), user.getEmail(),user.getRole());
                loggedUser = dbUserDAO.findUser("id",user.getUserId(),null,null);
            }

            db.commitTransaction();
            session.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser",loggedUser);
            request.setAttribute("applicationMessage",applicationMessage);
            request.setAttribute("viewUrl","home/view");
            request.setAttribute("applicationMessage",applicationMessage);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error");
            if(db!= null) db.rollbackTransaction();
            if (session != null) session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static  void  logout (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        Map<String,Object> FactoryParameter = new HashMap<>();
        Logger logger = LogService.getApplicationLogger();

        FactoryParameter.put("request",request);
        FactoryParameter.put("response",response);


        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,FactoryParameter);
            assert session != null;
            session.beginTransaction();
            UserDAO sessionUserDAO = session.getUserDAO();// recupero la sessione dell'user
            User loggedUser = sessionUserDAO.findLoggedUser();// recupero l'user in cookies

            sessionUserDAO.delete(loggedUser,null);

            commonView(null,null,request);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller Error logout",e);
            assert session != null;
            session.rollbackTransaction();
            throw  new RuntimeException(e);
        }finally {
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void newUser (HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("viewUrl", "home/registerUserView");
    }

    public  static  void insertUser (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;
        User loggedUser=null;


        Map<String,Object> userData = new HashMap<>();
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String languageCodePreferences = request.getParameter("languageCodePreferences");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");



        Map<String,Object> factoryParameters = new HashMap<>();
        factoryParameters.put("request",request);
        factoryParameters.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameters);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO= db.getUserDAO();

            UserDAO sessionUserDAO = session.getUserDAO();
            loggedUser = sessionUserDAO.findLoggedUser();



            if (loggedUser!=null) {
                loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
                loggedUser = sessionUserDAO.create(loggedUser.getUserId(),loggedUser.getUsername(),loggedUser.getPassword(),loggedUser.getFirstname(),loggedUser.getSecondName(),loggedUser.getLanguagesCodePreferences(), loggedUser.getEmail(),loggedUser.getRole());
                applicationMessage ="user esiste gia";
                userData.put("firstName",firstName);
                userData.put("secondName",firstName);
                userData.put("email",email);
                userData.put("role",role);
                userData.put("languageCodePreferences",languageCodePreferences);
            }else{
                try {
                    loggedUser = dbUserDAO.create(null,null,password,firstName,secondName,languageCodePreferences,email,role);
                    loggedUser = sessionUserDAO.create(loggedUser.getUserId(),loggedUser.getUsername(),loggedUser.getPassword(),loggedUser.getFirstname(),loggedUser.getSecondName(),loggedUser.getLanguagesCodePreferences(), loggedUser.getEmail(),loggedUser.getRole());
                } catch (DuplicatedObjectException e) {
                    applicationMessage = "user già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento di un utente già esistente");
                }
            }

            db.commitTransaction();
            session.commitTransaction();

            commonView(db,session,request);

            request.setAttribute("applicationMessage",applicationMessage);
            if (applicationMessage!=null) {
                request.setAttribute("viewUrl", "home/registerUserView");
                request.setAttribute("userData",userData);
            }
            else request.setAttribute("viewUrl","home/view");


        }catch (Exception e){
            logger.log(Level.SEVERE,"Controller error register",e);
            if(db != null) db.rollbackTransaction();
            if(session != null) session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void  edit (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session=null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;

        Map<String,Object> factoryParameters = new HashMap<>();
        factoryParameters.put("request",request);
        factoryParameters.put("response",response);

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameters);
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            db.beginTransaction();

            commonView(db,session,request);

            if(applicationMessage != null)request.setAttribute("viewUrl","home/view");
            else request.setAttribute("viewUrl","home/editUserView");

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            assert session != null;
            session.rollbackTransaction();
            assert db != null;
            db.rollbackTransaction();
            throw  new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void  editUser (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session=null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;
        User loggedUser = null;

        Map<String,Object> factoryParameters = new HashMap<>();
        factoryParameters.put("request",request);
        factoryParameters.put("response",response);


        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String languageCodePreferences = request.getParameter("languageCodePreferences");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL, factoryParameters);
            assert session != null;
            session.beginTransaction();
            UserDAO sessionUserDAO = session.getUserDAO();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL, null);
            assert db != null;
            db.beginTransaction();
            UserDAO dbUserDAO = db.getUserDAO();

            loggedUser = sessionUserDAO.findLoggedUser();
            if(loggedUser!= null){
                dbUserDAO.update(new User(loggedUser.getUserId(),  loggedUser.getUsername(), password,firstName, secondName, languageCodePreferences,email,role));
                db.commitTransaction();
                loggedUser=dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);
                db.commitTransaction();
                sessionUserDAO.update(loggedUser);
                session.commitTransaction();
            }else {
                applicationMessage="Error user non logged-- Modification Error";
            }

            commonView(db,session,request);

            request.setAttribute("applicationMessage",applicationMessage);
            if(applicationMessage != null) request.setAttribute("viewUrl","home/editUserView");
            else request.setAttribute("viewUrl","home/view");

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            assert session != null;
            session.rollbackTransaction();
            assert db != null;
            db.rollbackTransaction();
            throw  new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static  void deleteAccount (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null; // inizializzo  il gestore di session
        DAOFactory db = null;
        Map<String,Object> FactoryParameter = new HashMap<String,Object>();
        Logger logger = LogService.getApplicationLogger();

        FactoryParameter.put("request",request);
        FactoryParameter.put("response",response);


        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,FactoryParameter);
            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();
            assert session != null;
            session.beginTransaction();


            UserDAO sessionUserDAO = session.getUserDAO();// recupero la sessione dell'user
            User loggedUser = sessionUserDAO.findLoggedUser();// recupero l'user in cookies
            sessionUserDAO.delete(loggedUser,null);
            session.commitTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            loggedUser = dbUserDAO.findUser("id",loggedUser.getUserId(),null,null);


            dbUserDAO.delete(loggedUser,null);
            db.commitTransaction();

            commonView(db,session,request);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller Error logout",e);
            if (db != null) {
                db.rollbackTransaction();
            }
            if (session != null) {
                session.rollbackTransaction();
            }

            throw  new RuntimeException(e);
        }finally {
            if(session != null) session.closeTransaction();
            if (db != null) db.closeTransaction();
        }
    }

    private static void commonView(DAOFactory db, DAOFactory session, HttpServletRequest request) {
        User loggedUser = null;
        String applicationMessage= null;
        Logger logger = LogService.getApplicationLogger();

        try {
            if (session != null) {
                UserDAO userSessionDAO = session.getUserDAO();
                loggedUser = userSessionDAO.findLoggedUser();
            }

            if (loggedUser != null) {
                if (db != null) {
                    UserDAO dbUserDAO = db.getUserDAO();
                    loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
                }
            } else {
                applicationMessage = "user not found. connettiti.";
            }

            if (db != null) db.commitTransaction();
            if (session != null) session.commitTransaction();

            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "home/view");
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller error",e);
            if (session != null) session.rollbackTransaction();
            if (db != null) db.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

}
