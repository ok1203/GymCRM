package com.example.repo;

import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<User> findAll() {
        List<User> users;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        users = session.createQuery(criteriaQuery).list();
        return users;
    }

    public User create(User user) {
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

    public Optional<User> get(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }

    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
    }

    public void changeUserPassword(int id, String newPassword) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            user.setPassword(newPassword);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public void activateUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            user.setActive(true);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public void deactivateUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            user.setActive(false);
            session.update(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
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
