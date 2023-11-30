package com.example.repo;

import com.example.entity.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingTypeRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<TrainingType> findAll() {
        List<TrainingType> trainingTypes;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        criteriaQuery.select(root);

        trainingTypes = session.createQuery(criteriaQuery).list();
        return trainingTypes;
    }

    public Optional<TrainingType> get(int id) {
        Session session = sessionFactory.getCurrentSession();
        TrainingType trainingType = session.get(TrainingType.class, id);
        return Optional.ofNullable(trainingType);
    }

    public Optional<TrainingType> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("typeName"), name));
        TrainingType trainingType = session.createQuery(criteriaQuery).uniqueResult();
        return Optional.ofNullable(trainingType);
    }

    public TrainingType create(TrainingType trainingType) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainingType);
        return trainingType;
    }

    public void update(TrainingType trainingType) {
        Session session = sessionFactory.getCurrentSession();
        session.update(trainingType);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        TrainingType trainingType = session.get(TrainingType.class, id);
        if (trainingType != null) {
            session.delete(trainingType);
        }
    }
}
