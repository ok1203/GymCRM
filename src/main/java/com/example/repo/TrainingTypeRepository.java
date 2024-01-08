package com.example.repo;

import com.example.entity.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TrainingType> findAll() {
        List<TrainingType> trainingTypes;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        criteriaQuery.select(root);

        trainingTypes = entityManager.createQuery(criteriaQuery).getResultList();
        return trainingTypes;
    }

    public Optional<TrainingType> get(int id) {
        TrainingType trainingType = entityManager.find(TrainingType.class, id);
        return Optional.ofNullable(trainingType);
    }

    public Optional<TrainingType> getByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("typeName"), name));
        List<TrainingType> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        TrainingType trainingType = resultList.isEmpty() ? null : resultList.get(0);
        return Optional.ofNullable(trainingType);
    }

    public TrainingType create(TrainingType trainingType) {
        entityManager.persist(trainingType);
        return trainingType;
    }

    public void update(TrainingType trainingType) {
        entityManager.merge(trainingType);
    }

    public void delete(int id) {
        TrainingType trainingType = entityManager.find(TrainingType.class, id);
        if (trainingType != null) {
            entityManager.remove(trainingType);
        }
    }
}
