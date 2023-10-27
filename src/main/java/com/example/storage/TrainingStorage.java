package com.example.storage;

import com.example.model.Training;
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
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
            Root<Training> root = criteriaQuery.from(Training.class);
            criteriaQuery.select(root);

            trainings = session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all trainings", e);
        }
        return trainings;
    }

    public Optional<Training> getTraining(int trainingId) {
        try (Session session = sessionFactory.openSession()) {
            Training training = session.get(Training.class, trainingId);
            return Optional.ofNullable(training);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve training by ID: " + trainingId, e);
        }
    }

    @Transactional
    public Training createTraining(Training training) {
        try (Session session = sessionFactory.openSession()) {
            session.save(training);
            return training;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create training", e);
        }
    }

    @Transactional
    public void updateTraining(Training training) {
        try (Session session = sessionFactory.openSession()) {
            session.update(training);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update training", e);
        }
    }

    @Transactional
    public void deleteTraining(int trainingId) {
        try (Session session = sessionFactory.openSession()) {
            Training training = session.get(Training.class, trainingId);
            if (training != null) {
                session.delete(training);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete training by ID: " + trainingId, e);
        }
    }
}

