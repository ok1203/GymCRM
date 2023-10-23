package com.example.storage;

import com.example.model.TrainingType;
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
public class TrainingTypeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<Integer, TrainingType> getTrainingTypeMap() {
        Map<Integer, TrainingType> trainingTypeMap = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
            Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
            criteriaQuery.select(root);

            List<TrainingType> trainingTypes = session.createQuery(criteriaQuery).list();
            for (TrainingType trainingType : trainingTypes) {
                trainingTypeMap.put(trainingType.getId(), trainingType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainingTypeMap;
    }

    public Optional<TrainingType> getTrainingType(int trainingTypeId) {
        TrainingType trainingType = null;
        try (Session session = sessionFactory.openSession()) {
            trainingType = session.get(TrainingType.class, trainingTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(trainingType);
    }

    public void createTrainingType(TrainingType trainingType) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(trainingType);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTrainingType(TrainingType trainingType) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(trainingType);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTrainingType(int trainingTypeId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
            if (trainingType != null) {
                session.delete(trainingType);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
