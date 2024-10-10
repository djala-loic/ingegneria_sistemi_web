package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.PrenotazioneDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Prenotazione;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAOMYSQLIMPL implements PrenotazioneDAO {

    private final String COUNTER_ID = "prenotazioneCounterId";
    Connection connection;

    public  PrenotazioneDAOMYSQLIMPL(Connection connection){
        this.connection = connection;
    }

    @Override
    public Prenotazione create(User user, User destinatario, Template template, String oggetto, String body, String dataOra, String viaIncontro) throws DuplicatedObjectException {
        PreparedStatement ps;
        Prenotazione prenotazione = null;
        String sql ;

        try {
            sql=" SELECT * FROM prenotazioni " +
                    " WHERE mittenteID = ? " +
                    "AND destinatarioID = ? " +
                    "AND templateID = ? ";

            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, String.valueOf(user.getUserId()));
            ps.setString(i++,String.valueOf(destinatario.getUserId()));
            ps.setString(i,String.valueOf(template.getTemplateID()));
            ResultSet rs = ps.executeQuery();
            boolean gia = rs.next();

            if(gia) throw new DuplicatedObjectException("tentativo di creare una prenotazione gia esistente");

            sql = "update counter set counter_value = counter_value+1 where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            ps.executeUpdate();

            sql = "SELECT counter_value FROM counter where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();

            prenotazione = new Prenotazione(Long.parseLong(rs.getString("counter_value")),user,destinatario,template,oggetto,body,dataOra,viaIncontro);
            sql="INSERT INTO prenotazioni (prenotazioneID,mittenteID,destinatarioID,oggetto,body,dataOra,viaIncontro,templateID) values (?,?,?,?,?,?,?,?)";
            ps = this.connection.prepareStatement(sql);
            i=1;
            ps.setString(i++,String.valueOf(prenotazione.getPrenotazioneID()));
            ps.setString(i++, String.valueOf(prenotazione.getUser().getUserId()));
            ps.setString(i++, String.valueOf(prenotazione.getDestinatario().getUserId()));
            ps.setString(i++,oggetto);
            ps.setString(i++,body);
            ps.setString(i++,dataOra);
            ps.setString(i++,viaIncontro);
            ps.setString(i,String.valueOf(template.getTemplateID()));
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return prenotazione;
    }

    @Override
    public void update(Prenotazione prenotazione) throws NonExistObjectException {
        PreparedStatement ps;
        String sql ;
        try {
            sql=" SELECT * FROM prenotazioni " +
                    " WHERE mittenteID = ? " +
                    " AND destinatarioID = ? " +
                    " AND templateID = ? ";

            ps = this.connection.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, String.valueOf(prenotazione.getUser().getUserId()));
            ps.setString(i++,String.valueOf(prenotazione.getDestinatario().getUserId()));
            ps.setString(i,String.valueOf(prenotazione.getTemplate().getTemplateID()));
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) throw new NonExistObjectException("tentativo di cancellare una prenotazione che non esiste");

            sql="UPDATE prenotazioni " +
                    " SET " +
                    " oggetto = ? ," +
                    " body = ?  ," +
                    " dataOra = ? ," +
                    " viaIncontro = ? ," +
                    " accepted = "+prenotazione.getAccept()+
                    " , " +
                    " link = ? , " +
                    " response = ? " +
                    " WHERE " +
                    " mittenteID = ? " +
                    " AND destinatarioID = ? " +
                    " AND templateID = ? ";

            ps = this.connection.prepareStatement(sql);
            i=1;
            ps.setString(i++,prenotazione.getOggetto());
            ps.setString(i++,prenotazione.getBody());
            ps.setString(i++,prenotazione.getDataOra());
            ps.setString(i++,prenotazione.getViaIncontro());
            ps.setString(i++,String.valueOf(prenotazione.getLink()));
            ps.setString(i++,String.valueOf(prenotazione.getResponse()));
            ps.setString(i++, String.valueOf(prenotazione.getUser().getUserId()));
            ps.setString(i++, String.valueOf(prenotazione.getDestinatario().getUserId()));
            ps.setString(i,String.valueOf(prenotazione.getTemplate().getTemplateID()));
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restore(Prenotazione prenotazione) throws NonExistObjectException {

    }

    @Override
    public void delete(String mode,Prenotazione prenotazione) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError ="tentativo di cancellazione di una prenotazione non esistente";
        boolean deleteMode = (mode==null);

        try{
            String sql = " SELECT * FROM prenotazioni"
                    + " WHERE "
                    + " prenotazioneID = ?";

            if (deleteMode){
                sql+=" AND deleteState = false ";
            }

            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(prenotazione.getPrenotazioneID()));
            ResultSet rs = ps.executeQuery();
            boolean exist  = rs.next();

            if(exist) {
                if (deleteMode) {
                    sql = " UPDATE prenotazioni  "
                            + " SET "
                            + " deleteState = true "
                            + " WHERE "
                            + " prenotazioni = ? ";

                }else{
                    sql = " DELETE FROM prenotazioni WHERE  prenotazioneID = ? ";
                }

                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(prenotazione.getPrenotazioneID()));
                ps.executeUpdate();

                ps.close();
            }else throw  new NonExistObjectException(msgError);
        }catch (SQLException  e){
            throw  new RuntimeException(e);
        }
    }


    /**
     *
     * @param mode String
     *             puo valere "prenotazioneID" o null, "user_template"
     * @param prenotazioneID
     * @param user
     * @param template
     * @return Prenotazione
     */
    @Override
    public Prenotazione getPrenotazione(String mode, Long prenotazioneID, User user, Template template) {
        if (mode == null ){
            mode = "prenotazioneID";
        }
        Prenotazione prenotazione = null;
        PreparedStatement ps; String sql;
        try {
            sql = " SELECT * FROM prenotazioni " +
                    " WHERE ";
            if(mode.equals("prenotazioneID")) sql+=" prenotazioneID = ? ";
            if(mode.equals("user_template")) sql += "  mittenteID = ? AND templateID = ?";

            ps = this.connection.prepareStatement(sql);
            if(mode.equals("prenotazioneID")) ps.setString(1, String.valueOf(prenotazioneID));
            if(mode.equals("user_template")) {
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.setString(1,String.valueOf(template.getTemplateID()));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                prenotazione = readPrenotazioneResult(rs);
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return prenotazione;
    }

    /**
     *
     * @param mode String
     *              puo valere "user" ovvero il mittente,
     *              "destinatario", "template",
     *              "user_destinatario","destinatario_template"
     * @param user
     * @param template
     * @param destinatario
     * @return
     */
    @Override
    public List<Prenotazione> getPrenotazioni(String mode, Boolean deleteState,String platform, String search,Boolean accept, User user, Template template, User destinatario) {
        PreparedStatement ps ;
        List<Prenotazione> prenotazioni = new ArrayList<>();
        String sql;
        if(mode==null) mode = "user";
        if(deleteState == null)  deleteState = false;
        try {
            sql = " SELECT * FROM prenotazioni ";
            if(mode.equals("user")) sql+=" WHERE mittenteID = ?";
            if(mode.equals("template")) sql+=" WHERE templateID = ?";
            if(mode.equals("destinatario")) sql+=" WHERE destinatarioID = ?";
            if(mode.equals("user_destinatario")) sql+=" WHERE mittenteID = ? AND destinatarioID = ? ";
            if(mode.equals("destinatario_template")) sql+=" WHERE destinatarioID = ? AND templateID = ?";
            if(platform!= null && !platform.equals("all")) sql+=" AND viaIncontro = '"+platform+"' ";
            if(search!= null && !search.equals("all")){
                sql += " AND ( INSTR(oggetto, '"+search+"')>0 ";
                sql += " OR INSTR(viaIncontro,'"+search+"')>0) ";
            }
            if(accept!=null ) sql+=" AND accepted = "+accept;

            sql+= " AND deleteState = "+deleteState;
            sql+=" ORDER BY createdAt DESC";

            ps=this.connection.prepareStatement(sql);
            if(mode.equals("user")) ps.setString(1, String.valueOf(user.getUserId()));
            if(mode.equals("template")) ps.setString(1, String.valueOf(template.getTemplateID()));;
            if(mode.equals("destinatario"))  ps.setString(1, String.valueOf(destinatario.getUserId()));
            if(mode.equals("user_destinatario")) {
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.setString(2, String.valueOf(destinatario.getUserId()));
            }
            if(mode.equals("destinatario_template")){
                ps.setString(1, String.valueOf(destinatario.getUserId()));
                ps.setString(1, String.valueOf(template.getTemplateID()));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                prenotazioni.add(readPrenotazioneResult(rs));
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return prenotazioni;
    }

    private Prenotazione readPrenotazioneResult(ResultSet rs) {
        Prenotazione prenotazione = new Prenotazione();
        try{
            prenotazione.setPrenotazioneID(Long.valueOf(rs.getString("prenotazioneID")));
            prenotazione.setBody(rs.getString("body"));
            prenotazione.setDataOra(rs.getString("dataOra"));
            prenotazione.setDeleteState(Boolean.parseBoolean(rs.getString("deleteState")));
            prenotazione.setUser(new User());
            prenotazione.getUser().setUserId(Long.parseLong(rs.getString("mittenteID")));
            prenotazione.setDestinatario(new User());
            prenotazione.getDestinatario().setUserId(Long.parseLong(rs.getString("destinatarioID")));
            prenotazione.setViaIncontro(rs.getString("viaIncontro"));
            prenotazione.setCreatedAt(rs.getString("createdAt"));
            prenotazione.setTemplate(new Template());
            prenotazione.getTemplate().setTemplateID(Long.parseLong(rs.getString("templateID")));
            prenotazione.setOggetto(rs.getString("oggetto"));
            prenotazione.setAccept(rs.getBoolean("accepted"));
            prenotazione.setResponse(rs.getString("response"));
            prenotazione.setLink(rs.getString("link"));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return prenotazione;
    }

}
