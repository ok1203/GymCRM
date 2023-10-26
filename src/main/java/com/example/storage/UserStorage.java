package com.example.storage;

import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserStorage {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);

             users = session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all users", e);
        }
        return users;
    }

    @Transactional
    public Optional<User> getUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userId);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by ID: " + userId, e);
        }
    }

    @Transactional
    public User saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Transactional
    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.update(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Transactional
    public void deleteUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user by ID: " + userId, e);
        }
    }
}
