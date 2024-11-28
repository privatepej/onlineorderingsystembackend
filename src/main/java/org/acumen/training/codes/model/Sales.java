package org.acumen.training.codes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales")
public class Sales {
	
	private Integer salesid;
	private Integer orderid;
	private Integer itemno;
	private Integer qty;
	private LocalDate soldon;
	private String payment;
	
    private Product product;
    private UserOrders userOrders;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salesid", nullable = false, unique = true)
	public Integer getSalesid() {
		return salesid;
	}
	public void setSalesid(Integer salesid) {
		this.salesid = salesid;
	}
	
	@Column(name = "orderid", nullable = false)
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "itemno", nullable = false)
	public Integer getItemno() {
		return itemno;
	}
	
	public void setItemno(Integer itemno) {
		this.itemno = itemno;
	}
	
	@Column(name = "qty", nullable = false)
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	@Column(name = "soldon", nullable = false)
	public LocalDate getSoldon() {
		return soldon;
	}
	public void setSoldon(LocalDate soldon) {
		this.soldon = soldon;
	}
	
	@Column(name = "payment", nullable = false, length = 50)
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
// product
	@ManyToOne
    @JoinColumn(name = "itemno", referencedColumnName = "id", insertable = false, updatable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
// userOrders
	@ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "orderid", insertable = false, updatable = false)
	public UserOrders getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(UserOrders userOrders) {
		this.userOrders = userOrders;
	}
	
	
	
}
