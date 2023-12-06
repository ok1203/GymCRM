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
public class TraineeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Trainee> findAll() {
        List<Trainee> trainees;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root);

        trainees = entityManager.createQuery(criteriaQuery).getResultList();
        return trainees;
    }

    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    public Optional<Trainee> get(int id) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        return Optional.ofNullable(trainee);
    }

    public Trainee update(Trainee trainee){
        Trainee updatedTrainee = (Trainee) entityManager.merge(trainee);
        return updatedTrainee;

    }

    public void delete(int id) {
        Trainee trainee = entityManager.find(Trainee.class, id);
        if (trainee != null) {
            entityManager.remove(trainee);
        }
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));
        Trainee trainee = entityManager.createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(trainee);
    }

    public List<Training> getTraineeTrainings(int traineeId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(traineeJoin.get("id"), traineeId));

        List<Training> trainings = entityManager.createQuery(criteriaQuery).getResultList();
        return trainings;
    }

    public void authenticateTrainee(String username, String password) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainee> trainees = entityManager.createQuery(criteriaQuery).getResultList();

        if (trainees.isEmpty()) { // Authentication succeeds if the query returns any results.
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }

    public void addTrainersToTrainee(int traineeId, List<Trainer> trainersToAdd) {
        Trainee trainee = entityManager.find(Trainee.class, traineeId);

        if (trainee != null) {
            List<Trainer> existingTrainers = trainee.getTrainers();
            existingTrainers.addAll(trainersToAdd);
            trainee.setTrainers(existingTrainers);

            entityManager.merge(trainee);
        } else {
            throw new IllegalArgumentException("Trainee with ID " + traineeId + " not found.");
        }
    }
}
