package org.acumen.training.codes.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.acumen.training.codes.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

    public ProductDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public List<Product> selectAllProduct(){
		List<Product> records = new ArrayList<>();
		try (Session sess = sessionFactory.openSession();) {
			CriteriaBuilder builder = sess.getCriteriaBuilder();
			CriteriaQuery<Product> sql = builder.createQuery(Product.class);
			Root<Product> from = sql.from(Product.class);
			sql.select(from);			
			Query<Product> query = sess.createQuery(sql);
			records = query.getResultList();
			
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return records;
	}
}
