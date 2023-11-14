package com.example.repo;

import com.example.entity.Trainee;
import com.example.entity.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainee> findAll(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        List<Trainee> trainees;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root);

        trainees = session.createQuery(criteriaQuery).list();
        return trainees;
    }

    public Trainee create(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.save(trainee);
        return trainee;
    }

    public Optional<Trainee> get(int id, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        Trainee trainee = session.get(Trainee.class, id);
        return Optional.ofNullable(trainee);
    }

    public Trainee update(Trainee trainee, String username, String password){
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        Trainee updatedTrainee = (Trainee) session.merge(trainee);
        return updatedTrainee;

    }

    public void delete(int id, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        Trainee trainee = session.get(Trainee.class, id);
        if (trainee != null) {
            session.delete(trainee);
        }
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));
        Trainee trainee = session.createQuery(criteriaQuery).uniqueResult();
        return Optional.ofNullable(trainee);
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        authenticateTrainee(username, password);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

        criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(traineeJoin.get("id"), traineeId));

        List<Training> trainings = session.createQuery(criteriaQuery).list();
        return trainings;
    }

    public void authenticateTrainee(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> root = criteriaQuery.from(Trainee.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                criteriaBuilder.equal(root.get("gymUser").get("password"), password)
        );

        List<Trainee> trainees = session.createQuery(criteriaQuery).getResultList();

        if (trainees.isEmpty()) { // Authentication succeeds if the query returns any results.
            throw new SecurityException("Authentication failed for user: " + username);
        }
    }
}
