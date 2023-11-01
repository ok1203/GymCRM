package com.example.storage;

import com.example.entity.Trainee;
import com.example.entity.Training;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Component
public class TraineeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainee> getTraineeMap(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        List<Trainee> trainees;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root);

        trainees = session.createQuery(criteriaQuery).list();
        return trainees;
    }

    public Optional<Trainee> getTrainee(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        Trainee trainee = session.get(Trainee.class, traineeId);
        return Optional.ofNullable(trainee);
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));
        Trainee trainee = session.createQuery(criteriaQuery).uniqueResult();
        return Optional.ofNullable(trainee);
    }

    public Trainee createTrainee(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainee);
        return trainee;
    }

    public Trainee updateTrainee(Trainee trainee, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        session.update(trainee);
        return trainee;
    }

    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        Trainee trainee = session.get(Trainee.class, traineeId);
        trainee.getGym_user().setPassword(newPassword);
        session.saveOrUpdate(trainee);
    }

    public void deleteTrainee(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        Trainee trainee = session.get(Trainee.class, traineeId);
        if (trainee != null) {
            session.delete(trainee);
        }
    }

    public void activateTrainee(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        Trainee trainee = session.get(Trainee.class, traineeId);
        trainee.getGym_user().setActive(true);
        session.saveOrUpdate(trainee);
    }

    public void deactivateTrainee(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        Trainee trainee = session.get(Trainee.class, traineeId);
        trainee.getGym_user().setActive(false);
        session.saveOrUpdate(trainee);
    }

    public void deleteTraineeByUsername(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Trainee> delete = criteriaBuilder.createCriteriaDelete(Trainee.class);
        Root<Trainee> root = delete.from(Trainee.class);
        Join<Trainee, User> userJoin = root.join("gymUser");
        delete.where(criteriaBuilder.equal(userJoin.get("userName"), username));

        session.createQuery(delete).executeUpdate();
    }

    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        training.setTraineeId(trainee.getId());
        session.update(training);
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password, session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(traineeJoin.get("id"), traineeId));

        List<Training> trainings = session.createQuery(criteriaQuery).list();
        return trainings;
    }

    public void authenticateTrainee(String username, String password, Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainee> trainees = session.createQuery(criteriaQuery).getResultList();

        if (trainees.isEmpty()) { // Authentication succeeds if the query returns any results.
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }
}

