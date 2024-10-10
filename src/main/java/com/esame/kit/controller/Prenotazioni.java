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

public class Prenotazioni {
    public Prenotazioni(){}

    public static void view (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            commonView(db,session,request,null,null,null,null);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static void search (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();
            String platform = request.getParameter("platform");
            String searchValue = request.getParameter("search");
            String accettati = request.getParameter("accept");
            Boolean accept = null;

            if (accettati != null ) {
                if (accettati.equals("all")) accept = null;
                if (accettati.equals("accettati")) accept = true;
                if (accettati.equals("in Attesa")) accept = false;
            }

            commonView(db,session,request,null,platform,searchValue,accept);
            String s="";
            if(platform!=null) s += platform;
            if(searchValue!=null) s += searchValue;
            if(accettati != null) s+= accettati;
            request.setAttribute("search",s);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
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
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String templateID  = request.getParameter("templateID");
            String mittenteID  = request.getParameter("mittenteID");
            String body  = request.getParameter("body");
            String viaIncontro  = request.getParameter("viaIncontro");
            String dataOra  = request.getParameter("dataOra");
            String oggetto  = request.getParameter("oggetto");
            String destinatarioID  = request.getParameter("destinatarioID");

            User mittente = dbUserDAO.findUser("id",Long.parseLong(mittenteID),null,null);
            User destinatario = dbUserDAO.findUser("id",Long.parseLong(destinatarioID),null,null);
            Template template = dbTemplateDAO.findByTemplateID(Long.valueOf(templateID));

            try {
                Prenotazione prenotazione = dbPrenotazioneDAO.create(mittente, destinatario, template, oggetto, body, dataOra, viaIncontro);
                db.commitTransaction();
                prenotazione = dbPrenotazioneDAO.getPrenotazione(null, prenotazione.getPrenotazioneID(), null, null);
                request.setAttribute("prenotazione",prenotazione);
            }catch (DuplicatedObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }

            commonView(db,session,request,null,null,null,null);
            request.setAttribute("applicationMessage",applicationMessage);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void  oneView (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String prenotazioneID = request.getParameter("prenotazioneID");
            Prenotazione prenotazione = dbPrenotazioneDAO.getPrenotazione(null,Long.parseLong(prenotazioneID),null,null);
            prenotazione = getPrenotazione(dbPrenotazioneDAO, db.getTemplateDAO(), db.getUserDAO(),prenotazione.getPrenotazioneID());

            commonView(db,session,request,"prenotazione/oneView",null,null,null);
            request.setAttribute("prenotazione",prenotazione);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void  delete (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String prenotazioneID = request.getParameter("prenotazioneID");
            Prenotazione prenotazione = dbPrenotazioneDAO.getPrenotazione(null,Long.parseLong(prenotazioneID),null,null);
            prenotazione = getPrenotazione(dbPrenotazioneDAO, db.getTemplateDAO(), db.getUserDAO(),prenotazione.getPrenotazioneID());

            try {
                dbPrenotazioneDAO.delete("forever", prenotazione);
                db.commitTransaction();
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            request.setAttribute("applicationMessage",applicationMessage);

            commonView(db,session,request,null,null,null,null);
            request.setAttribute("prenotazione",prenotazione);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public  static  void  edit (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();


        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String prenotazioneID = request.getParameter("prenotazioneID");
            Prenotazione prenotazione = dbPrenotazioneDAO.getPrenotazione(null,Long.parseLong(prenotazioneID),null,null);
            prenotazione = getPrenotazione(dbPrenotazioneDAO, db.getTemplateDAO(), db.getUserDAO(),prenotazione.getPrenotazioneID());

            commonView(db,session,request,"prenotazione/editPrenotazione",null,null,null);
            request.setAttribute("prenotazione",prenotazione);
        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static void update (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Prenotazione prenotazione;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String prenotazioneID  = request.getParameter("prenotazioneID");
            String body  = request.getParameter("body");
            String viaIncontro  = request.getParameter("viaIncontro");
            String dataOra  = request.getParameter("dataOra");
            String oggetto  = request.getParameter("oggetto");


            try {
                prenotazione = dbPrenotazioneDAO.getPrenotazione(null, Long.parseLong(prenotazioneID), null, null);
                prenotazione = getPrenotazione(dbPrenotazioneDAO, dbTemplateDAO, dbUserDAO, Long.valueOf(prenotazioneID));
                prenotazione.setOggetto(oggetto);
                prenotazione.setDataOra(dataOra);
                prenotazione.setViaIncontro(viaIncontro);
                prenotazione.setBody(body);

                dbPrenotazioneDAO.update(prenotazione);
                db.commitTransaction();

                prenotazione = getPrenotazione(dbPrenotazioneDAO, dbTemplateDAO, dbUserDAO, Long.valueOf(prenotazioneID));
                request.setAttribute("prenotazione", prenotazione);
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            request.setAttribute("applicationMessage",applicationMessage);

            commonView(db,session,request,"prenotazione/oneView",null,null,null);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    public static void acceptPrenotazione (HttpServletRequest request, HttpServletResponse response){
        DAOFactory session = null;
        DAOFactory db = null;
        Logger logger = LogService.getApplicationLogger();
        Prenotazione prenotazione;
        String applicationMessage = null;

        Map<String, Object> factoryParameter = new HashMap<>();
        factoryParameter.put("request",request);
        factoryParameter.put("response",response);
        try{
            session = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,factoryParameter);
            assert session != null;
            session.beginTransaction();

            db = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            assert db != null;
            db.beginTransaction();

            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();


            String prenotazioneID  = request.getParameter("prenotazioneID");
            String responsePrenotazione  = request.getParameter("response");
            String link  = request.getParameter("link");
            String accettato  = request.getParameter("accept");
            Boolean accept =false;
            if(accettato!= null ){
                if (accettato.equals("yes")) accept = true;
                if(accettato.equals("no")) accept= false;
            }


            try {
                prenotazione = dbPrenotazioneDAO.getPrenotazione(null, Long.parseLong(prenotazioneID), null, null);
                prenotazione.setResponse(responsePrenotazione);
                prenotazione.setLink(link);
                prenotazione.setAccept(accept);
                dbPrenotazioneDAO.update(prenotazione);
                db.commitTransaction();

                prenotazione = getPrenotazione(dbPrenotazioneDAO, dbTemplateDAO, dbUserDAO, Long.valueOf(prenotazioneID));
                request.setAttribute("prenotazione", prenotazione);
            }catch (NonExistObjectException e) {
                applicationMessage = e.getMessage();
                logger.log(Level.INFO, e.getMessage());
            }
            request.setAttribute("applicationMessage",applicationMessage);
            commonView(db,session,request,"prenotazione/oneView",null,null,null);

        }catch (Exception e){
            logger.log(Level.SEVERE,"controller prenotazione error ",e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }finally {
            if(db != null) db.closeTransaction();
            if(session != null) session.closeTransaction();
        }
    }

    private static void commonView(DAOFactory db, DAOFactory session, HttpServletRequest request,String viewUrl,String platform,String search,Boolean accept) {
        if(viewUrl == null){
            viewUrl = "prenotazione/view";
        }

        Logger logger = LogService.getApplicationLogger();
        try {
            UserDAO sessionUserDAO = session.getUserDAO();
            UserDAO dbUserDAO = db.getUserDAO();
            TemplateDAO dbTemplateDAO = db.getTemplateDAO();
            PrenotazioneDAO dbPrenotazioneDAO = db.getPrenotazioneDAO();

            List<Prenotazione> prenotazioni= new ArrayList<>();
            User loggedUser = sessionUserDAO.findLoggedUser();
            String applicationMessage = null;
            if (loggedUser != null) {
                loggedUser = dbUserDAO.findUser("id", loggedUser.getUserId(), null, null);
                String mode="user";
                if(loggedUser.isCreatore()) mode="destinatario";
                if(loggedUser.isLettore()) mode= "user";

                if (mode.equals("user"))
                    prenotazioni = dbPrenotazioneDAO.getPrenotazioni(mode,null, platform, search,accept,loggedUser,null,null);
                if(mode.equals("destinatario")) prenotazioni = dbPrenotazioneDAO.getPrenotazioni(mode,null, platform, search,accept,null,null,loggedUser);
                for (int i = 0; i < prenotazioni.size(); i++) {
                    prenotazioni.set(i, getPrenotazione(dbPrenotazioneDAO,dbTemplateDAO, dbUserDAO, prenotazioni.get(i).getPrenotazioneID()));
                }
            } else {
                applicationMessage = "user not found. Login please";
            }

            db.commitTransaction();
            session.commitTransaction();
            request.setAttribute("prenotazioni", prenotazioni);
            request.setAttribute("loggedOn", loggedUser != null);
            request.setAttribute("loggedUser", loggedUser);
            if (applicationMessage != null){
                request.setAttribute("viewUrl", "home/view");
                request.setAttribute("applicationMessage", applicationMessage);
            }
            else request.setAttribute("viewUrl", viewUrl);
        }catch (Exception e) {
            logger.log(Level.SEVERE, "controller prenotazione error ", e);
            assert db != null;
            db.rollbackTransaction();
            session.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    private static Prenotazione getPrenotazione(PrenotazioneDAO dbPrenotazioneDAO,TemplateDAO dbTemplateDAO, UserDAO dbUserDAO, Long prenotazioneID) {

        Prenotazione prenotazione ;

        prenotazione = dbPrenotazioneDAO.getPrenotazione(null,prenotazioneID,null,null);
        prenotazione.setUser(dbUserDAO.findUser("id",prenotazione.getUser().getUserId(),null,null));
        prenotazione.setDestinatario(dbUserDAO.findUser("id",prenotazione.getDestinatario().getUserId(),null,null));
        prenotazione.setTemplate(dbTemplateDAO.findByTemplateID(prenotazione.getTemplate().getTemplateID()));

        return prenotazione;
    }

}
