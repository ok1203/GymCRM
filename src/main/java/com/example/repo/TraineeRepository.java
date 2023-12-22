package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Trainer;
import com.example.entity.Training;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Date;
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

    public List<Training> getTraineeTrainings(int traineeId, Date fromDate, Date toDate, int trainerId, int trainingTypeId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(traineeJoin.get("id"), traineeId));

        if (fromDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(trainingRoot.get("date"), fromDate));
        }

        if (toDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(trainingRoot.get("date"), toDate));
        }

        if (trainerId > 0) {
            predicates.add(criteriaBuilder.equal(trainingRoot.get("trainerId"), trainerId));
        }

        if (trainingTypeId > 0) {
            predicates.add(criteriaBuilder.equal(trainingRoot.get("trainingTypeId"), trainingTypeId));
        }

        criteriaQuery.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

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
