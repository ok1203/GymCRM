package com.example.repo;

import com.example.entity.Training;
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
public class TrainingRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Training> findAll() {
        List<Training> trainings;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        criteriaQuery.select(root);

        trainings = session.createQuery(criteriaQuery).list();

        return trainings;
    }

    public Training create(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.save(training);
        return training;
    }

    public Optional<Training> get(int id) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, id);
        return Optional.ofNullable(training);
    }

    public void update(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.update(training);
    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, id);
        if (training != null) {
            session.delete(training);
        }
    }
}
