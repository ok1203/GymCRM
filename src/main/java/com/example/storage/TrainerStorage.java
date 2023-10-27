package com.example.storage;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.*;

@Component
public class TrainerStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Trainer> getTrainerMap(String username, String password) {
        authenticateTrainer(username, password);
        List<Trainer> trainers;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);
            criteriaQuery.select(root);

            trainers = session.createQuery(criteriaQuery).list();

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all trainers", e);
        }
        return trainers;
    }

    public Optional<Trainer> getTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            return Optional.ofNullable(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainer by ID: " + trainerId, e);
        }
    }

    public Optional<Trainer> getTrainerByUsername(String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gymUser").get("userName"), username));

            Trainer trainer = session.createQuery(criteriaQuery).uniqueResult();
            return Optional.ofNullable(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainer by username: " + username, e);
        }
    }

    @Transactional
    public Trainer createTrainer(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            session.save(trainer);
            return trainer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create trainer", e);
        }
    }

    @Transactional
    public Trainer updateTrainer(Trainer trainer, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            session.update(trainer);
            return trainer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainer", e);
        }
    }

    @Transactional
    public void changeTrainerPassword(int trainerId, String newPassword, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            trainer.getGym_user().setPassword(newPassword);
            session.saveOrUpdate(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to change trainer's password", e);
        }
    }

    @Transactional
    public void deleteTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            session.delete(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete trainer by ID: " + trainerId, e);
        }
    }

    @Transactional
    public void activateTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            trainer.getGym_user().setActive(true);
            session.saveOrUpdate(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to activate trainer", e);
        }
    }

    @Transactional
    public void deactivateTrainer(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            trainer.getGym_user().setActive(false);
            session.saveOrUpdate(trainer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deactivate trainer", e);
        }
    }

    public List<Trainer> getNotAssignedActiveTrainersForTrainee(Trainee trainee, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);

            criteriaQuery.select(root).where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("isActive"), true),
                            criteriaBuilder.not(criteriaBuilder.isMember(trainee, root.get("trainees")))
                    )
            );

            return session.createQuery(criteriaQuery).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get not assigned active trainers for trainee", e);
        }
    }

    @Transactional
    public void addTrainingToTrainer(Trainer trainer, Training training, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            training.setTraineeId(trainer.getId());
            session.update(training);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update trainer", e);
        }
    }

    public List<Training> getTrainerTrainings(int trainerId, String username, String password) {
        authenticateTrainer(username, password);
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
            Root<Training> trainingRoot = criteriaQuery.from(Training.class);
            Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

            criteriaQuery.select(trainingRoot).where(criteriaBuilder.equal(trainerJoin.get("id"), trainerId));

            List<Training> trainings = session.createQuery(criteriaQuery).list();
            return trainings;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve trainee's trainings", e);
        }
    }

    public boolean authenticateTrainer(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);
            criteriaQuery.select(root).where(
                    criteriaBuilder.equal(root.get("gymUser").get("userName"), username),
                    criteriaBuilder.equal(root.get("gymUser").get("password"), password)
            );

            List<Trainer> trainers = session.createQuery(criteriaQuery).getResultList();

            if (!trainers.isEmpty()) { // Authentication succeeds if the query returns any results.
                return true;
            } else {
                throw new SecurityException("Authentication failed for user: " + username);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate trainer", e);
        }
    }
}


