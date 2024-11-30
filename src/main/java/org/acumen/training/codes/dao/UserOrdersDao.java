package org.acumen.training.codes.dao;

import java.util.List;

import org.acumen.training.codes.model.Category;
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
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(userOrders); 
			tx.commit();
			return true;
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
    }
    
    
    public boolean updateOrder(UserOrders userOrders) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			sess.merge(userOrders);
			tx.commit();
			return true;
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
    }

    
    public List<UserOrders> getOrdersByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserOrders> sql = builder.createQuery(UserOrders.class);
            Root<UserOrders> from = sql.from(UserOrders.class);
            sql.select(from).where(builder.equal(from.get("userid"), userId));
            Query<UserOrders> q = session.createQuery(sql);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deleteOrder(Integer orderId) {
        Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			UserOrders userOrder = sess.get(UserOrders.class, orderId);
			sess.remove(userOrder);
			tx.commit();
			return true;
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
    }
    
    //CART
    public UserOrders getPendingOrderByUserId(Integer userId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserOrders> sql = builder.createQuery(UserOrders.class);
            Root<UserOrders> from = sql.from(UserOrders.class);
            sql.select(from).where(
                builder.and(
                    builder.equal(from.get("userid"), userId),
                    builder.equal(from.get("status"), "CART")
                )
            );
            return session.createQuery(sql).uniqueResult();
        }
    }
    
}