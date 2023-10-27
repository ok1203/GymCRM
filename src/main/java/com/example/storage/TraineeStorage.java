package com.example.storage;

import com.example.model.Trainee;
import com.example.model.Training;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TraineeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainee> getTraineeMap(String username, String password) {
        authenticateTrainee(username, password);
        List<Trainee> trainees;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> root = criteriaQuery.from(Trainee.class);
            criteriaQuery.select(root);

            trainees = session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all trainees", e);
        }
        return trainees;
    }

    public Optional<Trainee> getTrainee(int traineeId, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainee by ID: " + traineeId, e);
        }
    }

    public Optional<Trainee> getTraineeByUsername(String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> root = criteriaQuery.from(Trainee.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));
            Trainee trainee = session.createQuery(criteriaQuery).uniqueResult();
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainee by username: " + username, e);
        }
    }

    @Transactional
    public Trainee createTrainee(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            session.save(trainee);
            return trainee;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create trainee", e);
        }
    }

    @Transactional
    public Trainee updateTrainee(Trainee trainee, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            session.update(trainee);
            return trainee;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainee", e);
        }
    }

    @Transactional
    public void changeTraineePassword(int traineeId, String newPassword, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            trainee.getGym_user().setPassword(newPassword);
            session.saveOrUpdate(trainee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to change trainee's password", e);
        }
    }

    @Transactional
    public void deleteTrainee(int traineeId, String username, String password) {
        authenticateTrainee(username, password);
            try (Session session = sessionFactory.openSession()) {
                Trainee trainee = session.get(Trainee.class, traineeId);
                session.delete(trainee);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete trainee by ID: " + traineeId, e);
            }
    }

    @Transactional
    public void activateTrainee(int traineeId, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            trainee.getGym_user().setActive(true);
            session.saveOrUpdate(trainee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate trainee", e);
        }
    }

    @Transactional
    public void deactivateTrainee(int traineeId, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            trainee.getGym_user().setActive(false);
            session.saveOrUpdate(trainee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deactivate trainee", e);
        }
    }

    @Transactional
    public void deleteTraineeByUsername(String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<Trainee> delete = criteriaBuilder.createCriteriaDelete(Trainee.class);
            Root<Trainee> root = delete.from(Trainee.class);
            Join<Trainee, User> userJoin = root.join("gymUser");
            delete.where(criteriaBuilder.equal(userJoin.get("userName"), username));

            session.createQuery(delete).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete trainee by username: " + username, e);
        }
    }

    @Transactional
    public void addTrainingToTrainee(Trainee trainee, Training training, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            training.setTraineeId(trainee.getId());
            session.update(training);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainer", e);
        }
    }

    public List<Training> getTraineeTrainings(int traineeId, String username, String password) {
        authenticateTrainee(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
            Root<Training> trainingRoot = criteriaQuery.from(Training.class);
            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

            criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(traineeJoin.get("id"), traineeId));

            List<Training> trainings = session.createQuery(criteriaQuery).list();
            return trainings;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainee's trainings", e);
        }
    }

    public void authenticateTrainee(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate trainee", e);
        }
    }
}

