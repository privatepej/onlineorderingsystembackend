package org.acumen.training.codes.dao;

import java.util.List;

import org.acumen.training.codes.model.UserOrders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class UserOrdersDao {

    @Autowired
    private SessionFactory sessionFactory;

    
    public boolean createOrder(UserOrders userOrders) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(userOrders);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean updateOrder(UserOrders userOrders) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(userOrders);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    
    public List<UserOrders> getOrdersByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserOrders> query = builder.createQuery(UserOrders.class);
            Root<UserOrders> root = query.from(UserOrders.class);
            query.select(root).where(builder.equal(root.get("userid"), userId));
            Query<UserOrders> q = session.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deleteOrder(Integer orderId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserOrders userOrder = session.get(UserOrders.class, orderId);
            if (userOrder != null) {
                session.remove(userOrder);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    //CART
    public UserOrders getPendingOrderByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserOrders> query = builder.createQuery(UserOrders.class);
            Root<UserOrders> root = query.from(UserOrders.class);
            query.select(root).where(
                builder.and(
                    builder.equal(root.get("userid"), userId),
                    builder.equal(root.get("status"), "CART")
                )
            );
            return session.createQuery(query).uniqueResult();
        }
    }
    
}