package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.TemplateDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TemplateDAOMYSQLIMPL implements TemplateDAO {

    private final String COUNTER_ID = "templateCounterId";
    Connection connection;
    public TemplateDAOMYSQLIMPL(Connection connection){
        this.connection = connection;
    }

    @Override
    public Template create(User user, String title, String description, String code, String language,String link) throws DuplicatedObjectException {
        PreparedStatement ps;
        Template template ;
        try{
            String sql
                    = "SELECT * FROM templates"
                    + " WHERE "
                    + "deleteState = false AND "
                    + " UCASE(title) = ? ";


            ps = this.connection.prepareStatement(sql);
            ps.setString(1,title.toUpperCase());
            ResultSet rs = ps.executeQuery();
            boolean exist = rs.next();

            rs.close();

            if(exist) throw  new DuplicatedObjectException("tentativo di creare un Template con un titolo gia esistente. Cambia il titolo. agguingici cifre e caratteri speciali per un titolo univoco .");

            sql = "update counter set counter_value = counter_value+1 where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            ps.executeUpdate();

            sql = "SELECT counter_value FROM counter where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();

            template =  new Template( Long.parseLong(rs.getString("counter_value")), user, title, description, code, language,link);

            // creo un nuovo template
            sql = "INSERT INTO templates (templateID, userID, title, description, code, language,link) VALUES (?,?,?,?,?,?,?)";
            ps = this.connection.prepareStatement(sql);
            int i=1;
            ps.setString(i++, String.valueOf(template.getTemplateID()));
            ps.setString(i++, String.valueOf(template.getUser().getUserId()));
            ps.setString(i++, template.getTitle());
            ps.setString(i++, template.getDescription());
            ps.setString(i++, template.getCode());
            ps.setString(i++, template.getLanguage());
            ps.setString(i, template.getLink());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return template;
    }

    @Override
    public Template findByTemplateID(Long templateID) {
        PreparedStatement ps ;
        Template template= new Template();
        try{
            String sql = " SELECT * FROM templates "
                    + " WHERE "
                    + " templateID = ? ";

            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(templateID));
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            if(exists) template = readTemplateResult(rs);

            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return template;
    }

    /**
     *
     * @param template
     * @param mode String
     *             null o simple , forever
     * @throws NonExistObjectException
     */
    @Override
    public void delete(Template template, String mode) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError ="tentativo di cancellazione di un user non esistente";

        /*
            if deleteMode == true non cancello definitivamente se non si
         */
        boolean deleteMode = (mode==null);

        String sqlComment,sqlLikes,sqlNotes,sqlPrenotazione;

        try{
            String sql = "SELECT * FROM templates"
                    + " WHERE "
                    + " templateID = ?";

            if (deleteMode){
                sql+=" AND deleteState = false ";
            }

            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.getTemplateID()));

            ResultSet rs = ps.executeQuery();
            boolean exist  = rs.next();

            if(exist) {
                if (deleteMode) {
                    sql = "UPDATE templates "
                            + " SET "
                            + " deleteState = true "
                            + " WHERE "
                            + " templateID = ?";

                    // delete comments
                    sqlComment = " UPDATE comments " +
                            "SET " +
                            " deleteState = true" +
                            " where " +
                            " templateID = ? ";

                    ps = this.connection.prepareStatement(sqlComment);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();


                    ps = this.connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();

                }else{
                    sql = "DELETE FROM templates WHERE  templateID = ?";

                    // delete comments
                    sqlComment = "DELETE FROM comments WHERE  templateID = ?";

                    // delete all notes
                    sqlNotes = "DELETE FROM notes WHERE  templateID = ?";

                    // delete likes
                    sqlLikes = "DELETE FROM Likes WHERE  classType = 'Template' AND valueID = ?";

                    sqlPrenotazione= "DELETE FROM prenotazioni WHERE  templateID = ?";

                    ps = this.connection.prepareStatement(sqlComment);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();


                    ps = this.connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();

                    ps = this.connection.prepareStatement(sqlLikes);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();

                    ps = this.connection.prepareStatement(sqlNotes);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();


                    ps = this.connection.prepareStatement(sqlPrenotazione);
                    ps.setString(1, String.valueOf(template.getTemplateID()));
                    ps.executeUpdate();
                }
                ps.close();
            }else throw  new NonExistObjectException(msgError);
        }catch (SQLException  e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void restore(Template template) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError = "tentativo di delezione un template non esistente";

        try{
            String sql = "SELECT * FROM templates"
                    + " WHERE "
                    + " deleteState = true AND"
                    + " templateID = ?";
            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.getTemplateID()));

            ResultSet rs = ps.executeQuery();
            boolean exist  = rs.next();
            if(exist) {
                sql = "UPDATE templates "
                        + " SET "
                        + " deleteState = false "
                        + " WHERE "
                        + " templateID = ?";


                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(template.getTemplateID()));
                ps.executeUpdate();

                sql = "UPDATE comments "
                        + " SET "
                        + " deleteState = false "
                        + " WHERE "
                        + " templateID = ?";

                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(template.getTemplateID()));
                ps.executeUpdate();

                ps.close();
            }else throw  new NonExistObjectException(msgError);
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void update(Template template) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError = "tentativo di update un post non esistente";
        try{
            String sql = "SELECT * FROM templates "
                    + " WHERE "
                    + " templateID = ?";
            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(template.getTemplateID()));
            ResultSet rs = ps.executeQuery();
            boolean exist = rs.next();
            if(!exist) {throw  new NonExistObjectException(msgError);}

            // update il mio post
            sql = "UPDATE templates "
                    + " SET "
                    + " userID = ? ,"
                    + " title = ? ,"
                    + " description = ?,"
                    + " code = ? ,"
                    + " language = ? ," +
                    " link = ? "
                    + " WHERE "
                    + " templateID = ?";

            ps= this.connection.prepareStatement(sql);
            int i= 1;
            ps.setString(i++, String.valueOf(template.getUser().getUserId()));
            ps.setString(i++,template.getTitle());
            ps.setString(i++,template.getDescription());
            ps.setString(i++,template.getCode());
            ps.setString(i++,template.getLanguage());
            ps.setString(i++, template.getLink());
            ps.setString(i, String.valueOf(template.getTemplateID()));

            ps.executeUpdate();
            ps.close();

        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    /***
     *
     * @param user
     * @param mode String
     *             all,onLine,offLine
     * @param language
     * @param search
     * @return templates
     */
    @Override
    public List<Template> getAllTemplateByUserID(User user, String mode, String language, String search) {
        PreparedStatement ps;
        ArrayList<Template> templates = new ArrayList<>();
        try{
            String sql = " SELECT * FROM templates " +
                    " where  userID = "+user.getUserId()+" ";

            if(mode.equals("onLine")){
                sql+=" AND deleteState = false";
            }
            if(mode.equals("offLine")){
                sql+=" AND deleteState = true";
            }

            if(language!=null){
                sql+=" AND language = ? ";
            }

            if(search!=null){
                sql += " AND ( INSTR(language,?)>0 ";
                sql += " OR INSTR(title,?)>0 ";
                sql += " OR INSTR(description,?)>0 ";
                sql += " OR INSTR(code,?)>0 ) ";
            }

            sql+= " ORDER BY created_at DESC";

            ps = this.connection.prepareStatement(sql);

            if(language!=null){
                ps.setString(1,language);
            }

            if(search!=null){
                int i=1;
                ps.setString(i++,search);
                ps.setString(i++,search);
                ps.setString(i++,search);
                ps.setString(i,search);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                templates.add(readTemplateResult(rs));
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return templates;
    }

    /***
     *
     * @param mode String
     *             all,onLine,offLine
     * @return templates
     */
    @Override
    public List<Template> getAllTemplates(String mode,String language, String search) {
        PreparedStatement ps;
        ArrayList<Template> templates = new ArrayList<>();

        try{
            String sql = " SELECT * FROM templates ";

            if(mode !=null && mode.equals("onLine")){
                sql+=" where deleteState = false ";
            }

            if(mode !=null && mode.equals("offLine")){
                sql+=" where deleteState = true ";
            }

            if(mode == null && language!=null && !language.equals("all")){
                sql+=" WHERE  language = ? ";
            }

            if(mode!=null && language!=null && !language.equals("all")){
                sql+=" AND language = ? ";
            }

            if(mode!= null && search != null){
                sql += " AND ( INSTR(language,?)>0 ";
                sql += " OR INSTR(title,?)>0 ";
                sql += " OR INSTR(description,?)>0 )";
            }

            if(mode == null && search != null){
                sql += " WHERE ( INSTR(language,?)>0 ";
                sql += " OR INSTR(title,?)>0 ";
                sql += " OR INSTR(description,?)>0 ) ";
            }

            sql+= " ORDER BY created_at DESC ";
            ps = this.connection.prepareStatement(sql);

            if(language!=null && !language.equals("all")){
                ps.setString(1,language);
            }

            if(search!=null){
                int i=1;
                ps.setString(i++,search);
                ps.setString(i++,search);
                ps.setString(i,search);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                templates.add(readTemplateResult(rs));
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return templates;
    }
    private Template readTemplateResult(ResultSet rs){
        Template template = new Template();
        try{
            template.setTemplateID(Long.parseLong(rs.getString("templateID")));
            template.setTitle(rs.getString("title"));
            template.setDescription(rs.getString("description"));
            template.setCode(rs.getString("code"));
            template.setLink(rs.getString("link"));
            template.setLanguage(rs.getString("language"));
            template.setCreatedAt(rs.getString("created_at"));
            template.setDeleteState(rs.getBoolean("deleteState"));
            template.setUser(new User());
            template.getUser().setUserId(rs.getLong("userID"));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return template;
    }

}
