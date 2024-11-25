package org.acumen.training.codes.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	private Integer id;
	private String pname;
	private Double price;
	private String description;
	private String categoryname;

    private Category category;
    
    private Set<ProductImages> productImages = new HashSet<>();
    private Set<Sales> sales = new HashSet<>();

    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "pname", nullable = false, length = 100, unique = true)
	public String getPname() {
		return pname;
	}
	
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Column(name = "price", nullable = false)
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(name = "categoryname", nullable = false,  length = 100)
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	// category
	@ManyToOne
    @JoinColumn(name = "categoryname", referencedColumnName = "cname", insertable = false, updatable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
// productImages
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER,  cascade = {CascadeType.MERGE, CascadeType.REMOVE} )
	public Set<ProductImages> getProductImages() {
		return productImages;
	}
	public void setProductImages(Set<ProductImages> productImages) {
		this.productImages = productImages;
	}
	
// sales
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER )
	public Set<Sales> getSales() {
		return sales;
	}
	public void setSales(Set<Sales> sales) {
		this.sales = sales;
	}
	
	
}
