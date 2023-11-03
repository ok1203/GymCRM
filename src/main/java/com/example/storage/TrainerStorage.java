package com.example.storage;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class TrainerStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainer> getTrainerMap(String username, String password) {
        authenticateTrainer(username, password);
        List<Trainer> trainers;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);

        trainers = session.createQuery(criteriaQuery).list();

        return trainers;
    }

    public Optional<Trainer> getTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer trainer = session.get(Trainer.class, trainerId);
        return Optional.ofNullable(trainer);
    }

    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));

        Trainer trainer = session.createQuery(criteriaQuery).uniqueResult();
        return Optional.ofNullable(trainer);
    }

    public Trainer createTrainer(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainer);
        return trainer;
    }

    public Trainer updateTrainer(Trainer trainer, String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer updatedTrainer = (Trainer) session.merge(trainer);
        return updatedTrainer;
    }

    public void deleteTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer trainer = session.get(Trainer.class, trainerId);
        session.delete(trainer);
    }

    public List<Trainer> getNotAssignedActiveTrainersForTrainee(Trainee trainee, String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);

        criteriaQuery.select(root).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isActive"), true),
                        criteriaBuilder.not(criteriaBuilder.isMember(trainee, root.get("trainees")))
                )
        );

        return session.createQuery(criteriaQuery).list();
    }

    public List<Training> getTrainerTrainings(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(trainerJoin.get("id"), trainerId));

        List<Training> trainings = session.createQuery(criteriaQuery).list();
        return trainings;
    }

    public boolean authenticateTrainer(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainer> trainers = session.createQuery(criteriaQuery).getResultList();

        if (!trainers.isEmpty()) { // Authentication succeeds if the query returns any results.
            return true;
        } else {
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }
}