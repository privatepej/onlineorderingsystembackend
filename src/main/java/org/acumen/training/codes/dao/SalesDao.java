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
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(sales);
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

    public boolean updateSalesQuantity(Integer salesId, Integer newQuantity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
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
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteSalesItem(Integer salesId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Sales sales = session.get(Sales.class, salesId);
            if (sales != null) {
                session.remove(sales);
                transaction.commit();
                return true;
            } else {
                return false; 
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteAllSalesItemsByOrderId(Integer orderId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Sales> delete = builder.createCriteriaDelete(Sales.class);
            Root<Sales> root = delete.from(Sales.class);
            delete.where(builder.equal(root.get("orderid"), orderId));
            int deletedCount = session.createMutationQuery(delete).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    // CART
    public Sales getCartItem(Integer orderId, Integer productId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Sales> query = builder.createQuery(Sales.class);
            Root<Sales> root = query.from(Sales.class);
            query.select(root).where(
                builder.and(
                    builder.equal(root.get("orderid"), orderId),
                    builder.equal(root.get("itemno"), productId)
                )
            );
            return session.createQuery(query).uniqueResult();
        }
    }

    public List<Sales> getCartItems(Integer orderId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Sales> query = builder.createQuery(Sales.class);
            Root<Sales> root = query.from(Sales.class);
            query.select(root).where(builder.equal(root.get("orderid"), orderId));
            return session.createQuery(query).list();
        }
    }

    public boolean saveCartItem(Sales sales) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(sales);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCartItem(Sales sales) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(sales);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearCart(Integer orderId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Sales> query = builder.createCriteriaDelete(Sales.class);
            Root<Sales> root = query.from(Sales.class);
            query.where(builder.equal(root.get("orderid"), orderId));
            int deletedCount = session.createMutationQuery(query).executeUpdate();
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
            CriteriaDelete<Sales> query = builder.createCriteriaDelete(Sales.class);
            Root<Sales> root = query.from(Sales.class);
            query.where(
            		builder.equal(root.get("orderid"), orderId),
            		builder.equal(root.get("itemno"), productId)
            );
            int deletedCount = session.createMutationQuery(query).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

}
