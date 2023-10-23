package com.example.storage;

import com.example.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TrainerStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<Integer, Trainer> getTrainerMap() {
        Map<Integer, Trainer> traineeMap = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);
            criteriaQuery.select(root);

            List<Trainer> trainers = session.createQuery(criteriaQuery).list();
            for (Trainer trainer : trainers) {
                traineeMap.put(trainer.getId(), trainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traineeMap;
    }

    public Optional<Trainer> getTrainer(int trainerId) {
        Trainer trainer = null;
        try (Session session = sessionFactory.openSession()) {
            trainer = session.get(Trainer.class, trainerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(trainer);
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        Trainer trainer = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> root = criteriaQuery.from(Trainer.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gym_user").get("user_name"), username));

            trainer = session.createQuery(criteriaQuery).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(trainer);
    }

    public Trainer createTrainer(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(trainer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainer;
    }

    public Trainer updateTrainer(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(trainer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainer;
    }

    public void changeTrainerPassword(int trainerId, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            if (trainer != null) {
                trainer.getGym_user().setPassword(newPassword);
                session.beginTransaction();
                session.saveOrUpdate(trainer);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTrainer(int trainerId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, trainerId);
            if (trainer != null) {
                session.delete(trainer);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateTrainer(int trainerId) {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            if (trainer != null) {
                trainer.getGym_user().setActive(true);
                session.beginTransaction();
                session.saveOrUpdate(trainer);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivateTrainer(int trainerId) {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            if (trainer != null) {
                trainer.getGym_user().setActive(false);
                session.beginTransaction();
                session.saveOrUpdate(trainer);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
