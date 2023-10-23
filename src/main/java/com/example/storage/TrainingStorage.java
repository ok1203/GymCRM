package com.example.storage;

import com.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TrainingStorage {
    @Autowired
    private SessionFactory sessionFactory;

    public Map<Integer, Training> getTrainingMap() {
        Map<Integer, Training> trainingMap = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
            Root<Training> root = criteriaQuery.from(Training.class);
            criteriaQuery.select(root);

            List<Training> trainings = session.createQuery(criteriaQuery).list();
            for (Training training : trainings) {
                trainingMap.put(training.getId(), training);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainingMap;
    }

    public Optional<Training> getTraining(int trainingId) {
        Training training = null;
        try (Session session = sessionFactory.openSession()) {
            training = session.get(Training.class, trainingId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(training);
    }

    public Training createTraining(Training training) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(training);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return training;
    }

    public void updateTraining(Training training) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(training);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTraining(int trainingId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Training training = session.get(Training.class, trainingId);
            if (training != null) {
                session.delete(training);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
