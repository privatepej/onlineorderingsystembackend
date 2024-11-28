package org.acumen.training.codes.dao;

import java.util.ArrayList;
import java.util.List;

import org.acumen.training.codes.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    
    public boolean insertCategory(Category category) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(category); 
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
    
    
    public boolean updateCategoryname(Integer id, String newCategoryName) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			Category category = sess.get(Category.class, id); 
			category.setCname(newCategoryName);
			sess.merge(category);
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
    
    public boolean deleteById(Integer id) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			Category category = sess.get(Category.class, id);
			sess.remove(category);
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
    
    public boolean deleteCategoryByName(String categoryName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Category> delete = builder.createCriteriaDelete(Category.class);
            Root<Category> root = delete.from(Category.class);
            delete.where(builder.equal(root.get("cname"), categoryName));
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
    
    public boolean doesCategoryExist(String cname) {
        System.out.println("Checking category existence for: " + cname);

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.select(builder.count(root));
            criteriaQuery.where(builder.equal(root.get("cname"), cname));
            Long count = session.createQuery(criteriaQuery).getSingleResult();
            System.out.println("Category count for " + cname + ": " + count);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
   


	

    // Get All Categories
    public List<Category> getAllCategories() {
		List<Category> records = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> sql = builder.createQuery(Category.class);
            Root<Category> from = sql.from(Category.class);
            sql.select(from);
			Query<Category> query = session.createQuery(sql);
			records = query.getResultList();
			return records;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    
}
