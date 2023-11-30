package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainer> findAll(String username, String password) {
        //authenticateTrainer(username, password);
        List<Trainer> trainers;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);

        trainers = session.createQuery(criteriaQuery).list();

        return trainers;
    }

    public Trainer create(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainer);
        return trainer;
    }

    public Optional<Trainer> get(int id, String username, String password) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer trainer = session.get(Trainer.class, id);
        return Optional.ofNullable(trainer);
    }

    public Trainer update(Trainer trainer) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer updatedTrainer = (Trainer) session.merge(trainer);
        return updatedTrainer;
    }

    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));

        Trainer trainer = session.createQuery(criteriaQuery).uniqueResult();
        return Optional.ofNullable(trainer);
    }

    public void delete(int id, String username, String password) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        Trainer trainer = session.get(Trainer.class, id);
        session.delete(trainer);
    }

    public List<Trainer> getNotAssignedTrainersForTrainee(Trainee trainee) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);

        criteriaQuery.select(root).where(
                criteriaBuilder.not(criteriaBuilder.isMember(trainee, root.get("trainees")))
        );

        return session.createQuery(criteriaQuery).list();
    }

    public List<Training> getTrainerTrainings(int trainerId) {
        //authenticateTrainer(username, password);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(trainerJoin.get("id"), trainerId));

        List<Training> trainings = session.createQuery(criteriaQuery).list();
        return trainings;
    }

    public void authenticateTrainer(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainer> trainers = session.createQuery(criteriaQuery).getResultList();

        if (trainers.isEmpty()) { // Authentication succeeds if the query returns any results.
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }

}
