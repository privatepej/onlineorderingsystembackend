package org.acumen.training.codes.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.acumen.training.codes.model.Product;
import org.acumen.training.codes.model.ProductImages;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
	
	public List<Tuple> JoinTableProductImage(){
		List<Tuple> records = new ArrayList<>();
		try (Session sess = sessionFactory.openSession();) {
			CriteriaBuilder builder = sess.getCriteriaBuilder();
			CriteriaQuery<Tuple> sql = builder.createQuery(Tuple.class);
			Root<Product> from = sql.from(Product.class); 
			Join<Product, ProductImages> join = from.join("productImages", JoinType.LEFT);
			sql.multiselect(
		            from,            
		            join            
		        );
			Query<Tuple> query = sess.createQuery(sql);
			records = query.getResultList();
			return Collections.unmodifiableList(records);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return records;
	}
	
	
	public Product selectProductById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public boolean insertProduct(Product product) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(product); 
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
	
	public boolean saveProductImage(ProductImages productImage) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction(); 
		try {
			sess.persist(productImage); 
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
	
	

	 public Product findByProductName(String pname) {
		 Product product = new Product();
	        try (Session session = sessionFactory.openSession()) {
	            CriteriaBuilder builder = session.getCriteriaBuilder();
	            CriteriaQuery<Product> sql = builder.createQuery(Product.class);
	            Root<Product> from = sql.from(Product.class);
	            sql.select(from).where(builder.equal(from.get("pname"), pname));
	            Query<Product> query = session.createQuery(sql);
	            product = query.uniqueResult();
	            return product;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	 
	 
	  public boolean updateProductImage(ProductImages productImage) {
		  Session sess = sessionFactory.openSession();
			Transaction tx = sess.beginTransaction();
			try {
				sess.merge(productImage);
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



	    
	    public boolean updateProduct(Product product) {
	        Session sess = sessionFactory.openSession();
			Transaction tx = sess.beginTransaction();
			try {
				sess.merge(product);
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
    
    
    public boolean updateProductname(Integer id, String newProductName) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			Product product = sess.get(Product.class, id); 
			product.setPname(newProductName);
			sess.merge(product);
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
    
    public boolean updateProductPrice(Integer id, Double newProductPrice) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			Product product = sess.get(Product.class, id); 
			product.setPrice(newProductPrice);
			sess.merge(product);
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
    
    public boolean updateProductDescription(Integer id, String newProductDescription) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			Product product = sess.get(Product.class, id); 
			product.setDescription(newProductDescription);
			sess.merge(product);
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
    
    public boolean updateProductImage(Integer id, String newProductImage) {
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		try {
			ProductImages productImage = sess.get(ProductImages.class, id); 
			productImage.setImagename(newProductImage);
			sess.merge(productImage);
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
			Product product = sess.get(Product.class, id);
			sess.remove(product);
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
    
    public boolean deleteProductByName(String productName) {
        try (Session session = sessionFactory.openSession()) {
        	Transaction transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Product> delete = builder.createCriteriaDelete(Product.class);
            Root<Product> from = delete.from(Product.class);
            delete.where(builder.equal(from.get("pname"), productName));
            int deletedCount = session.createMutationQuery(delete).executeUpdate();
            transaction.commit();
            return deletedCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean doesProductExist(String pname) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> sql = builder.createQuery(Long.class);
            Root<Product> from = sql.from(Product.class);
            sql.select(builder.count(from));
            sql.where(builder.equal(from.get("pname"), pname));
            Long count = session.createQuery(sql).getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean doesImageExist(String imageName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> sql = builder.createQuery(Long.class);
            Root<ProductImages> from = sql.from(ProductImages.class);
            sql.select(builder.count(from))
                 .where(builder.equal(from.get("imagename"), imageName));
            Long count = session.createQuery(sql).getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    public ProductImages findImageByProductName(String pname) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductImages> sql = builder.createQuery(ProductImages.class);
            Root<ProductImages> from = sql.from(ProductImages.class);
            sql.select(from).where(builder.equal(from.get("pname"), pname));
            return session.createQuery(sql).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; 
        }
    }

    
}
