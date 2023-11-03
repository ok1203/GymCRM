package com.example.storage;

import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class TrainingStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Training> getTrainingMap() {
        List<Training> trainings;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        criteriaQuery.select(root);

        trainings = session.createQuery(criteriaQuery).list();

        return trainings;
    }

    public Optional<Training> getTraining(int trainingId) {
       Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, trainingId);
        return Optional.ofNullable(training);
    }

    public Training createTraining(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.save(training);
        return training;
    }

    public void updateTraining(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.update(training);
    }

    public void deleteTraining(int trainingId) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, trainingId);
        if (training != null) {
            session.delete(training);
        }
    }
}

