package com.example.storage;

import com.example.entity.TrainingType;
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
public class TrainingTypeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List <TrainingType> getTrainingTypeMap() {
        List<TrainingType> trainingTypes;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
            Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
            criteriaQuery.select(root);

            trainingTypes = session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all training types", e);
        }
        return trainingTypes;
    }

    public Optional<TrainingType> getTrainingType(int trainingTypeId) {
        try (Session session = sessionFactory.openSession()) {
            TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
            return Optional.ofNullable(trainingType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve training type by ID: " + trainingTypeId, e);
        }
    }

    @Transactional
    public void createTrainingType(TrainingType trainingType) {
        try (Session session = sessionFactory.openSession()) {
            session.save(trainingType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create training type", e);
        }
    }

    @Transactional
    public void updateTrainingType(TrainingType trainingType) {
        try (Session session = sessionFactory.openSession()) {
            session.update(trainingType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update training type", e);
        }
    }

    @Transactional
    public void deleteTrainingType(int trainingTypeId) {
        try (Session session = sessionFactory.openSession()) {
            TrainingType trainingType = session.get(TrainingType.class, trainingTypeId);
            if (trainingType != null) {
                session.delete(trainingType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete training type by ID: " + trainingTypeId, e);
        }
    }
}