package com.example.storage;

import com.example.entity.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class TrainingTypeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List <TrainingType> getTrainingTypeMap() {
        List<TrainingType> trainingTypes;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        criteriaQuery.select(root);

        trainingTypes = session.createQuery(criteriaQuery).list();
        return trainingTypes;
    }

    public Optional<TrainingType> getTrainingType(int trainingTypeId) {
        Session session = sessionFactory.getCurrentSession();
        TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
        return Optional.ofNullable(trainingType);
    }

    public void createTrainingType(TrainingType trainingType) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainingType);
    }

    public void updateTrainingType(TrainingType trainingType) {
        Session session = sessionFactory.getCurrentSession();
        session.update(trainingType);
    }

    public void deleteTrainingType(int trainingTypeId) {
        Session session = sessionFactory.getCurrentSession();
        TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
        if (trainingType != null) {
            session.delete(trainingType);
        }
    }
}