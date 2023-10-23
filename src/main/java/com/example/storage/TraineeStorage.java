package com.example.storage;

import com.example.model.Trainee;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TraineeStorage {

    @Autowired
    private SessionFactory sessionFactory;

    public Map<Integer, Trainee> getTraineeMap() {
        Map<Integer, Trainee> traineeMap = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> root = criteriaQuery.from(Trainee.class);
            criteriaQuery.select(root);

            List<Trainee> trainees = session.createQuery(criteriaQuery).list();
            for (Trainee trainee : trainees) {
                traineeMap.put(trainee.getId(), trainee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traineeMap;
    }

    public Optional<Trainee> getTrainee(int traineeId) {
        Trainee trainee = null;
        try (Session session = sessionFactory.openSession()) {
            trainee = session.get(Trainee.class, traineeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(trainee);
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        Trainee trainee = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> root = criteriaQuery.from(Trainee.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("gym_user").get("userName"), username));

            trainee = session.createQuery(criteriaQuery).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(trainee);
    }

    public Trainee createTrainee(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(trainee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public Trainee updateTrainee(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(trainee);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public void changeTraineePassword(int traineeId, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                trainee.getGym_user().setPassword(newPassword);
                session.beginTransaction();
                session.saveOrUpdate(trainee);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteTrainee(int traineeId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                session.delete(trainee);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activateTrainee(int traineeId) {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                trainee.getGym_user().setActive(true);
                session.beginTransaction();
                session.saveOrUpdate(trainee);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivateTrainee(int traineeId) {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                trainee.getGym_user().setActive(false);
                session.beginTransaction();
                session.saveOrUpdate(trainee);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTraineeByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<Trainee> delete = criteriaBuilder.createCriteriaDelete(Trainee.class);
            Root<Trainee> root = delete.from(Trainee.class);

            Join<Trainee, User> userJoin = root.join("gymUser");
            delete.where(criteriaBuilder.equal(userJoin.get("userName"), username));

            session.beginTransaction();
            session.createQuery(delete).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
