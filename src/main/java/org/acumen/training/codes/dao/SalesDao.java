package org.acumen.training.codes.dao;

import java.util.List;

import org.acumen.training.codes.model.Sales;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class SalesDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    public boolean createSales(Sales sales) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(sales); 
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

    public boolean updateSalesQuantity(Integer salesId, Integer newQuantity) {
        try (Session session = sessionFactory.openSession()) {
        	Transaction transaction = session.beginTransaction();
            Sales sales = session.get(Sales.class, salesId);
            if (sales != null) {
                sales.setQty(newQuantity);
                session.merge(sales);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteSalesItem(Integer salesId) {
        try (Session session = sessionFactory.openSession()) {
        	Transaction transaction = session.beginTransaction();
            Sales sales = session.get(Sales.class, salesId);
            if (sales != null) {
                session.remove(sales);
                transaction.commit();
                return true;
            } else {
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteAllSalesItemsByOrderId(Integer orderId) {
        try (Session session = sessionFactory.openSession()) {
        	Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Sales> delete = builder.createCriteriaDelete(Sales.class);
            Root<Sales> from = delete.from(Sales.class);
            delete.where(builder.equal(from.get("orderid"), orderId));
            int deletedCount = session.createMutationQuery(delete).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // CART
    public Sales getCartItem(Integer orderId, Integer productId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Sales> sql = builder.createQuery(Sales.class);
            Root<Sales> from = sql.from(Sales.class);
            sql.select(from).where(
                builder.and(
                    builder.equal(from.get("orderid"), orderId),
                    builder.equal(from.get("itemno"), productId)
                )
            );
            return session.createQuery(sql).uniqueResult();
        }
    }

    public List<Sales> getCartItems(Integer orderId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Sales> sql = builder.createQuery(Sales.class);
            Root<Sales> from = sql.from(Sales.class);
            sql.select(from).where(builder.equal(from.get("orderid"), orderId));
            return session.createQuery(sql).list();
        }
    }

    public boolean saveCartItem(Sales sales) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(sales); 
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

    public boolean updateCartItem(Sales sales) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			sess.merge(sales);
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

    public boolean clearCart(Integer orderId) {
        try (Session session = sessionFactory.openSession()) {
        	Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Sales> sql = builder.createCriteriaDelete(Sales.class);
            Root<Sales> from = sql.from(Sales.class);
            sql.where(builder.equal(from.get("orderid"), orderId));
            int deletedCount = session.createMutationQuery(sql).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeCartItem(Integer orderId, Integer productId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Sales> sql = builder.createCriteriaDelete(Sales.class);
            Root<Sales> from = sql.from(Sales.class);
            sql.where(
            		builder.equal(from.get("orderid"), orderId),
            		builder.equal(from.get("itemno"), productId)
            );
            int deletedCount = session.createMutationQuery(sql).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

}
