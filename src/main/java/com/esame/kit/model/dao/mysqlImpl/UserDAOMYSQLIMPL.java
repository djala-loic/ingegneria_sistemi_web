package com.esame.kit.model.dao.mysqlImpl;

import com.esame.kit.model.dao.UserDAO;
import com.esame.kit.model.dao.exception.DuplicatedObjectException;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOMYSQLIMPL implements UserDAO {
    private final String COUNTER_ID = "userCounterId";
    Connection connection;

    public UserDAOMYSQLIMPL(Connection connection){
        this.connection = connection;
    }


    @Override
    public User create(Long userId, String username, String password, String firstName, String secondName, String languagesCodePreferences, String email, String role) throws DuplicatedObjectException {
        PreparedStatement ps;
        User user;
        try{
            String sql
                    = " SELECT userID FROM users "
                    + " WHERE "
                    +" deleteState = false AND "
                    +" email = ? ";

            ps = this.connection.prepareStatement(sql);
            int i =1;
            ps.setString(i,email);

            ResultSet rs = ps.executeQuery();
            boolean userExist = rs.next();


            if(userExist){
                throw new DuplicatedObjectException("questo utente esiste gia !!!.");
            }

            sql = "update counter set counter_value=counter_value+1 where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            ps.executeUpdate();

            sql = "SELECT counter_value FROM counter where counter_id='" + COUNTER_ID + "'";
            ps = this.connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();


            user = new User(Long.parseLong(rs.getString("counter_value")),  username, password,firstName, secondName, languagesCodePreferences,email,role);
            user.setUsername(makeUserName(firstName, secondName, user.getUserId()));

            if(password== null && languagesCodePreferences == null){
                sql = "INSERT INTO users  (userID, email, username, firstName, secondName, role)  VALUES (?,?,?,?,?,?)";
                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(user.getUserId()));
                ps.setString(i++, user.getEmail());
                ps.setString(i++, user.getUsername());
                ps.setString(i++, user.getFirstname());
                ps.setString(i++, user.getSecondName());
                ps.setString(i, user.getRole());
            }else {
                sql
                        = "INSERT INTO users  (userID, email, username, firstName, secondName, languageCodePreference ,password,role)  VALUES (?,?,?,?,?,?,?,?)";
                ps = this.connection.prepareStatement(sql);
                i = 1;
                ps.setString(i++, String.valueOf(user.getUserId()));
                ps.setString(i++, user.getEmail());
                ps.setString(i++, user.getUsername());
                ps.setString(i++, user.getFirstname());
                ps.setString(i++, user.getSecondName());
                ps.setString(i++, user.getLanguagesCodePreferences());
                ps.setString(i++, user.getPassword());
                ps.setString(i, user.getRole());
            }
            ps.executeUpdate();

            ps.close();
            rs.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public void update(User user) throws NonExistObjectException {
        PreparedStatement ps;
        try {

            String sql
                    = " SELECT userID FROM users"
                    + " WHERE "
                    + " deleteState = false AND "
                    + " userID = ?";

            ps = this.connection.prepareStatement(sql);

            int i =1;
            ps.setString(i,String.valueOf(user.getUserId()));

            ResultSet rs = ps.executeQuery();;
            boolean gia = rs.next();
            rs.close();

            if (!gia) throw  new NonExistObjectException("tentativo di aggiornamento di un componente non esistente");

            user.setUsername(makeUserName(user.getFirstname(),user.getSecondName(),user.getUserId()));

            sql = " UPDATE users "
                    + " SET "
                    + "   firstName = ?, "
                    + "   secondName = ?, "
                    + "   email = ?, "
                    + "   password = ?, "
                    + "   username = ? ,"
                    + "   languageCodePreference = ? "
                    + " WHERE "
                    + " userId = ? ";


            ps = this.connection.prepareStatement(sql);
            i =1 ;
            ps.setString(i++,user.getFirstname());
            ps.setString(i++,user.getSecondName());
            ps.setString(i++,user.getEmail());
            ps.setString(i++,user.getPassword());
            ps.setString(i++,user.getUsername());
            ps.setString(i++, user.getLanguagesCodePreferences());
            ps.setString(i,String.valueOf(user.getUserId()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param user
     * @param mode String
     *  mode puo valere null, forever // indica il modo con cui si delete lo detto user in db a seconda lo deleteState
     * @throws NonExistObjectException
     */
    @Override
    public void delete(User user, String mode) throws NonExistObjectException {
        PreparedStatement ps;
        String msgError ="tentativo di cancellazione di un user non esistente";
        boolean deleteMode = !( mode!=null && mode.equals("forever"));
        String sqlComment,sqlLikes,sqlTemplate,sqlNotes,sqlPrenotazione;
        try {

            String sql
                    = " SELECT userID FROM users"
                    + " WHERE " +
                    "userID = ?";

            if(deleteMode) {
                sql+=" AND deleteState = false";
            }

            ps = this.connection.prepareStatement(sql);
            int i =1;
            ps.setString(i,String.valueOf(user.getUserId()));
            ResultSet rs = ps.executeQuery();;
            boolean gia = rs.next();
            rs.close();

            if (!gia) throw  new NonExistObjectException(msgError);


            if(deleteMode) {
                sql = " UPDATE users "
                        + " SET "
                        + " deleteState = true"
                        + " WHERE "
                        + " userID = ? ";

                sqlComment = " UPDATE comments "
                        + " SET "
                        + " deleteState = true"
                        + " WHERE "
                        + " userID = ? ";

                sqlTemplate = " UPDATE templates "
                        + " SET "
                        + " deleteState = true"
                        + " WHERE "
                        + " userID = ? ";

                ps = this.connection.prepareStatement(sqlComment);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sqlTemplate);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sql);
                ps.setString(1,String.valueOf(user.getUserId()));
                ps.executeUpdate();

            }else {
                sql = "DELETE FROM users WHERE  userID = ?";

                sqlTemplate = "DELETE FROM templates WHERE  userID = ?";

                sqlComment = "DELETE FROM comments WHERE  userID = ?";

                sqlLikes = "DELETE FROM Likes WHERE  classType = 'Template' AND valueID = ?";

                sqlNotes = "DELETE FROM notes WHERE  userID = ?";

                sqlPrenotazione= "DELETE FROM prenotazioni WHERE  mittenteID = ? OR destinatarioID = ?";

                ps = this.connection.prepareStatement(sqlComment);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sqlTemplate);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sql);
                ps.setString(1,String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sqlLikes);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sqlNotes);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.executeUpdate();

                ps = this.connection.prepareStatement(sqlPrenotazione);
                ps.setString(1, String.valueOf(user.getUserId()));
                ps.setString(2, String.valueOf(user.getUserId()));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restoreUser(User user) throws NonExistObjectException {
        PreparedStatement ps;
        try {

            String sql
                    = " SELECT userID FROM users"
                    + " WHERE "
                    + " deleteState = true AND "
                    + " userID = ?";

            ps = this.connection.prepareStatement(sql);

            int i =1;
            ps.setString(i, String.valueOf(user.getUserId()));

            ResultSet rs = ps.executeQuery();;
            boolean gia = rs.next();
            rs.close();

            if (!gia) throw  new NonExistObjectException("tentativo di restorazione di un user non cancellato");


            sql = " UPDATE users "
                    + " SET "
                    + " deleteState = false"
                    + " WHERE "
                    + " userId = ? ";


            ps = this.connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(user.getUserId()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeRole(User user, String role) throws NonExistObjectException {
        PreparedStatement ps;

        try {

            String sql
                    = " SELECT userID FROM users"
                    + " WHERE "
                    + " deleteState = 'N' AND "
                    + " userID = ?";

            ps = this.connection.prepareStatement(sql);

            int i =1;
            ps.setString(i, String.valueOf(user.getUserId()));

            ResultSet rs = ps.executeQuery();;
            boolean gia = rs.next();
            rs.close();

            if (!gia) throw  new NonExistObjectException("tentativo di creare u admin con un user cancellato");


            sql = " UPDATE users "
                    + " SET "
                    + " role = ?"
                    + " WHERE "
                    + " userId = ? ";


            ps = this.connection.prepareStatement(sql);
            i = 1;
            ps.setString(i++,role);
            ps.setString(i, String.valueOf(user.getUserId()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers(String mode, String role, String search) {
        PreparedStatement ps;
        ArrayList<User> users = new ArrayList<>();
        try{
            String sql = " SELECT * FROM users";
            if (mode!=null && !mode.equals("all")) {
                sql += " WHERE deleteState = ?";
            }

            if (role!=null && !role.equals("all")){
                sql += " WHERE UCASE(role) = ?";
            }

            if (search!=null){
                sql += " WHERE (INSTR(username,?)>0 ";
                sql += " OR INSTR(email,?)>0 ";
                sql += " OR INSTR(firstName,?)>0 ";
                sql += " OR INSTR(secondName,?)>0) ";
            }

            sql+= " ORDER BY created_at DESC ";

            ps = this.connection.prepareStatement(sql);
            if (mode!=null && !mode.equals("all")) {
                ps.setString(1,mode);
            }

            if (role!=null && !role.equals("all")) {
                ps.setString(1,role);
            }

            if (search!=null) {
                int i =1;
                ps.setString(i++, search);
                ps.setString(i++, search);
                ps.setString(i++, search);
                ps.setString(i, search);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                users.add(readUserResult(rs));
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            throw  new RuntimeException(e);

        }
        return users;
    }

    @Override
    public User findUser(String mode, Long userID, String username, String email) {
        PreparedStatement ps;
        boolean gia;
        User  user=null;
        if(mode.equals("id")){
            mode="userID";
        }
        String sql
                = "SELECT * FROM users"
                + " WHERE " +
                mode + " =  ? ";
        try {
            ps = this.connection.prepareStatement(sql);

            if (mode.equals("userID")){
                ps.setString(1, String.valueOf(userID));
            } else if (mode.equals("username")) {
                ps.setString(1,username);
            }else{
                ps.setString(1,email);
            }

            ResultSet rs = ps.executeQuery();

            gia = rs.next();

            if(gia){
                user = readUserResult(rs);
            }
            rs.close();

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findLoggedUser() {
        throw new UnsupportedOperationException("NOT SUPPORTED YET.");
    }

    private  String makeUserName (String email,String secondName,long Id){
        return "@"+email.split("@")[0].toString()+"_"+secondName+"_"+Id;
    }

    private  User readUserResult (ResultSet rs) {
        User user = new User();
        try {
            user.setUserId(rs.getLong("userID"));
            user.setRole(rs.getString("role"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFirstname(rs.getString("firstName"));
            user.setSecondName(rs.getString("secondName"));
            user.setDeleteState(rs.getBoolean("deleteState"));
            user.setLanguagesCodePreferences(rs.getString("languageCodePreference"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}
