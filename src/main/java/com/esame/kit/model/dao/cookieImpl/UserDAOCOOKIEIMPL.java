package com.esame.kit.model.dao.cookieImpl;

import com.esame.kit.model.dao.UserDAO;
import com.esame.kit.model.dao.exception.NonExistObjectException;
import com.esame.kit.model.mo.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class UserDAOCOOKIEIMPL implements UserDAO {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public UserDAOCOOKIEIMPL (HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response= response;
    }

    @Override
    public User create(Long userId, String username, String password, String firstName, String secondName, String languagesCodePreferences, String email,String role) {
        User loggerUser = new User(userId,  username, password,firstName, secondName, languagesCodePreferences,email,"");
        Cookie cookie;
        cookie = new Cookie("loggedUser",encode(loggerUser));
        cookie.setPath("/");
        this.response.addCookie(cookie);
        return loggerUser;
    }

    @Override
    public void update(User user)  {
        Cookie cookie = new Cookie("loggedUser",encode(user));
        cookie.setPath("/");
        this.response.addCookie(cookie);
    }


    @Override
    public void delete(User user, String mode) throws NonExistObjectException {
        Cookie cookie = new Cookie("loggedUser","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        this.response.addCookie(cookie);
    }


    @Override
    public List<User> getAllUsers(String mode, String role, String search) {
        throw new UnsupportedOperationException("NOT SUPORTED YET.");
    }

    @Override
    public User findUser(String mode, Long userID, String username, String email) {
        throw new UnsupportedOperationException("NOT SUPORTED YET.");
    }

    @Override
    public User findLoggedUser() {
        Cookie[] cookies = this.request.getCookies();
        User loggedUser = null;

        if (cookies!=null) {
            for (int i = 0; i < cookies.length && loggedUser == null; i++) {
                if (cookies[i].getName().equals("loggedUser")) {
                    loggedUser = decode(cookies[i].getValue());
                }
            }
        }
        return loggedUser;
    }


    @Override
    public void restoreUser(User user) {
        throw new UnsupportedOperationException("NOT SUPORTED YET.");
    }

    @Override
    public void changeRole(User user, String role) throws NonExistObjectException {
        throw new UnsupportedOperationException("NOT SUPORTED YET.");
    }


    private String encode(User loggerUser) {
        String end = loggerUser.getUserId()+"#"+loggerUser.getUsername()+"#"+loggerUser.getFirstname().trim();
        return URLEncoder.encode(end);
    }

    private  User decode (String encodeLoggedString){
        User loggedUser = new User();
        encodeLoggedString = URLDecoder.decode(encodeLoggedString);
        String[] values = encodeLoggedString.split("#");

        loggedUser.setUserId(Long.parseLong(values[0].toString()));
        loggedUser.setUsername(values[1]);
        loggedUser.setFirstname(values[2]);

        return loggedUser;
    }
}