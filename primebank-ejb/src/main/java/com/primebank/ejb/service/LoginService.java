package com.primebank.ejb.service;

import com.primebank.core.entity.User;
import com.primebank.ejb.util.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Set;

@Stateless
public class LoginService {

    @PersistenceContext(unitName = "primeBankPU")
    private EntityManager em;

    public boolean validate(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(PasswordUtil.hashedPassword(password));
    }

    public Set<String> getRoles(String username) {
        User user = getUserByUsername(username);

        if (user != null) {
            String role = user.getUserRole().toString();
            return Set.of(role);
        }
        return Set.of();
    }

    public User getUserByUsername(String username) {
        try {
            return em.createNamedQuery("User.getUserByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("User lookup failed: " + e.getMessage());
            return null;
        }
    }
}
