package com.example.repo;

import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        List<User> users;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        users = entityManager.createQuery(criteriaQuery).getResultList();
        return users;
    }

    public User create(User user) {
        String originalUsername = user.getUserName();
        String newUsername = originalUsername;
        int serialNumber = 1;

        while (isUsernameTaken(entityManager, newUsername)) {
            serialNumber++;
            newUsername = originalUsername + serialNumber;
        }

        user.setUserName(newUsername);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        entityManager.persist(user);
        return user;
    }

    public Optional<User> get(int id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public void changeUserPassword(int id, String newPassword) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setPassword(newPassword);
            entityManager.merge(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public void activateUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setActive(true);
            entityManager.merge(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public void deactivateUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setActive(false);
            entityManager.merge(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    private boolean isUsernameTaken(EntityManager entityManager, String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userName"), username));

        List<User> users = entityManager.createQuery(criteriaQuery).getResultList();
        return !users.isEmpty();
    }

    public Optional<User> findByUserName(String userName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userName"), userName));

        User user = entityManager.createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(user);
    }

}
