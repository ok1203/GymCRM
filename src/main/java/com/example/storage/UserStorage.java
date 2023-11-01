package com.example.storage;

import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class UserStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getAllUsers() {
        List<User> users;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        users = session.createQuery(criteriaQuery).list();
        session.close();
        return users;
    }

    public Optional<User> getUserById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        return Optional.ofNullable(user);
    }

    public User saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        String originalUsername = user.getUserName();
        String newUsername = originalUsername;
        int serialNumber = 1;

        while (isUsernameTaken(session, newUsername)) {
            serialNumber++;
            newUsername = originalUsername + serialNumber;
        }

        user.setUserName(newUsername);

        session.save(user);
        return user;
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void deleteUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            session.delete(user);
        }
    }

    public void changeUserPassword(int userId, String newPassword) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            user.setPassword(newPassword);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void activateUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            user.setActive(true);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void deactivateUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, userId);
        if (user != null) {
            user.setActive(false);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    private boolean isUsernameTaken(Session session, String username) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userName"), username));

        List<User> users = session.createQuery(criteriaQuery).list();
        return !users.isEmpty();
    }
}
