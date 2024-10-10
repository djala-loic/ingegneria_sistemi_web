package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.NoteDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.Note;
import com.esame.kit.model.mo.Template;
import com.esame.kit.model.mo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAOMSQLIMPL implements NoteDAO {
    private final String COUNTER_ID = "noteCounterId";
    Connection connection;

    public NoteDAOMSQLIMPL(Connection connection){
        this.connection = connection;
    }


    @Override
    public Note getNote(String mode, Long noteID, User user, Template template) {
        PreparedStatement ps;
        if (mode == null){
            mode = "noteID";
        }
        Note note=null;
        try {
            String sql = " SELECT * FROM notes ";
            if(mode.equals("noteID")) sql+=" WHERE noteID = ? ";
            if (mode.equals("template_user")) sql+="where templateID= ? AND userID = ?";


            ps = this.connection.prepareStatement(sql);
            if(mode.equals("noteID")) ps.setString(1, String.valueOf((noteID)));
            if (mode.equals("template_user")){
                ps.setString(1, String.valueOf(template.getTemplateID()));
                ps.setString(2, String.valueOf(user.getUserId()));
            }

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                note = readResultNote(rs);
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return note;
    }

    @Override
    public List<Note> getNotes(String mode, User user, Template template) {
        PreparedStatement ps;
        if (mode == null){
            mode = "templateID";
        }
        List<Note> notes= new ArrayList<>();
        try {
            String sql = " SELECT * FROM notes ";
            if(mode.equals("templateID")) sql+= " WHERE templateID = ? ";
            if(mode.equals("userID")) sql+="where userID = ?";

            ps = this.connection.prepareStatement(sql);
            if(mode.equals("templateID"))
                ps.setString(1, String.valueOf(template.getTemplateID()));
            if(mode.equals("userID")) ps.setString(1, String.valueOf(user.getUserId()));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                notes.add(readResultNote(rs));
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return notes;
    }

    @Override
    public Note create(User user, Template template, Long note) throws DuplicatedObjectException {
        PreparedStatement ps;
        Note Note=null;
        try{
            String sql = " SELECT * FROM notes "
                    + " WHERE userID = ? AND"
                    + "  templateID = ?";

            ps = this.connection.prepareStatement(sql);
            int i=1;
            ps.setString(i++,String.valueOf(user.getUserId()));
            ps.setString(i, String.valueOf(template.getTemplateID()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) throw new  DuplicatedObjectException("tentativo di creare una nota che esiste gia");
            else {
                sql = "update counter set counter_value=counter_value+1 where counter_id='" + COUNTER_ID + "'";
                ps = this.connection.prepareStatement(sql);
                ps.executeUpdate();

                sql = "SELECT counter_value FROM counter where counter_id='" + COUNTER_ID + "'";
                ps = this.connection.prepareStatement(sql);
                rs = ps.executeQuery();
                rs.next();

                Note = new Note();
                Note.setNoteID(Long.valueOf(rs.getString("counter_value")));
                Note.setUser(user);
                Note.setTemplate(template);
                Note.setNoteValue(note);

                sql = "INSERT INTO notes (noteID,userID,templateID,noteValue) values (?,?,?,?)";
                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(Note.getNoteID()));
                ps.setString(i++, String.valueOf(Note.getUser().getUserId()));
                ps.setString(i++, String.valueOf(Note.getTemplate().getTemplateID()));
                ps.setString(i, String.valueOf(Note.getNoteValue()));
                ps.executeUpdate();
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Note;
    }

    @Override
    public void update(Note note, Long value) throws NonExistObjectException {
        PreparedStatement ps;
        try{
            String sql = " SELECT * FROM notes "
                    + " WHERE userID = ? AND"
                    + "  templateID = ?";

            ps = this.connection.prepareStatement(sql);
            int i=1;
            ps.setString(i++,String.valueOf(note.getUser().getUserId()));
            ps.setString(i, String.valueOf(note.getTemplate().getTemplateID()));
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) throw new NonExistObjectException("tentativo di updating una nota non esistente");
            else {
                sql = " UPDATE notes "
                        + " SET "
                        + "  noteValue = ? "
                        + " WHERE "
                        + " userId = ?  AND"
                        + "templateID = ? AND "
                        + " noteID = ? ";

                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(note.getNoteValue()));
                ps.setString(i++, String.valueOf(note.getUser().getUserId()));
                ps.setString(i++, String.valueOf(note.getTemplate().getTemplateID()));
                ps.setString(i, String.valueOf(note.getNoteID()));
                ps.executeUpdate();
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Note note) throws  NonExistObjectException {
        PreparedStatement ps;
        try{
            String sql = " SELECT * FROM notes "
                    + " WHERE userID = ? AND"
                    + "  templateID = ?";

            ps = this.connection.prepareStatement(sql);
            int i=1;
            ps.setString(i++,String.valueOf(note.getUser().getUserId()));
            ps.setString(i, String.valueOf(note.getTemplate().getTemplateID()));
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) throw new NonExistObjectException("tentativo di deleting una nota non esistente");
            else {
                sql = " DELETE FROM notes "
                        + " where "
                        + " noteID = ? ";

                ps = this.connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(note.getNoteID()));
                ps.executeUpdate();
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private Note readResultNote(ResultSet rs) {
        Note note = new Note();
        try{
            note.setNoteID(Long.valueOf(rs.getString("noteID")));
            note.setNoteValue(Long.valueOf(rs.getString("noteValue")));
            note.setUser(new User());
            note.getUser().setUserId(Long.parseLong(rs.getString("userID")));
            note.setTemplate(new Template());
            note.getTemplate().setTemplateID(Long.parseLong(rs.getString("templateID")));
            note.setCreatedAt(rs.getString("createdAt"));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return note;
    }
}
