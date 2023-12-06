package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Trainer> findAll() {
        List<Trainer> trainers;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root);

        trainers = entityManager.createQuery(criteriaQuery).getResultList();

        return trainers;
    }

    public Trainer create(Trainer trainer) {
        entityManager.persist(trainer);
        return trainer;
    }

    public Optional<Trainer> get(int id) {
        Trainer trainer = entityManager.find(Trainer.class, id);
        return Optional.ofNullable(trainer);
    }

    public Trainer update(Trainer trainer) {
        Trainer updatedTrainer = (Trainer) entityManager.merge(trainer);
        return updatedTrainer;
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));

        Trainer trainer = entityManager.createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(trainer);
    }

    public void delete(int id) {
        Trainer trainer = entityManager.find(Trainer.class, id);
        entityManager.remove(trainer);
    }

    public List<Trainer> getNotAssignedTrainersForTrainee(Trainee trainee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);

        criteriaQuery.select(root).where(
                criteriaBuilder.not(criteriaBuilder.isMember(trainee, root.get("trainees")))
        );

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Training> getTrainerTrainings(int trainerId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(trainerJoin.get("id"), trainerId));

        List<Training> trainings = entityManager.createQuery(criteriaQuery).getResultList();
        return trainings;
    }

    public void authenticateTrainer(String username, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> root = criteriaQuery.from(Trainer.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainer> trainers = entityManager.createQuery(criteriaQuery).getResultList();

        if (trainers.isEmpty()) { // Authentication succeeds if the query returns any results.
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }

}
