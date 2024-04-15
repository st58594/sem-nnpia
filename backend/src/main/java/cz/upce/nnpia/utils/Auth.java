package cz.upce.nnpia.utils;

import cz.upce.nnpia.exceptions.InvalidCredentialException;
import cz.upce.nnpia.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Auth {
    public static User getLoggedUser(){
        User user = getUser();
        if (user == null)
            throw new InvalidCredentialException("No one is logged.");
        return user;
    }
    public static User getUser(){
        if (SecurityContextHolder.getContext().getAuthentication() != null){
            var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user instanceof UserDetails){
                return (User) user;
            }
        }
        return null;
    }
}
