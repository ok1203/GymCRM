package com.example.repo;

import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Training> findAll() {
        List<Training> trainings;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        criteriaQuery.select(root);

        trainings = entityManager.createQuery(criteriaQuery).getResultList();

        return trainings;
    }

    public Training create(Training training) {
        entityManager.persist(training);
        return training;
    }

    public Optional<Training> get(int id) {
        Training training = entityManager.find(Training.class, id);
        return Optional.ofNullable(training);
    }

    public void update(Training training) {
        entityManager.merge(training);
    }

    public void delete(int id) {
        Training training = entityManager.find(Training.class, id);
        if (training != null) {
            entityManager.remove(training);
        }
    }
}
