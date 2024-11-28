package org.acumen.training.codes.dao;

import org.acumen.training.codes.model.Users;
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
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public boolean createUser(Users users) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(users); 
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
    
    
    
    
    
    public Users findByEmail(String email) {
        Users users = new Users();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> sql = builder.createQuery(Users.class);
            Root<Users> root = sql.from(Users.class);
            sql.select(root).where(builder.equal(root.get("email"), email));
            Query<Users> query = session.createQuery(sql);
            users = query.uniqueResult();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    
    public boolean updateUser(Users users) {
    	Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			sess.merge(users);
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

    
    


}
