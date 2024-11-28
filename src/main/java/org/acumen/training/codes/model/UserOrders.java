package org.acumen.training.codes.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "user_orders")
public class UserOrders {

	private Integer orderid;
	private Integer userid;
	private Integer paymentid;
	private LocalDate orderon;
	private Double ordert;
	
	private Users users;
	private UsersPayments usersPayments;
	
    private Set<Sales> sales = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid", nullable = false, unique = true)
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	
	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "paymentid")
	public Integer getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}
	
	@Column(name = "orderon", nullable = false)
	public LocalDate getOrderon() {
		return orderon;
	}
	public void setOrderon(LocalDate orderon) {
		this.orderon = orderon;
	}
	
	@Column(name = "ordert", nullable = false)
	public Double getOrdert() {
		return ordert;
	}
	public void setOrdert(Double ordert) {
		this.ordert = ordert;
	}
	
// users
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id",insertable = false, updatable = false)
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
// usersPayments
	
	@ManyToOne
	@JoinColumn(name = "paymentid", referencedColumnName = "paymentid",insertable = false, updatable = false)
	public UsersPayments getUsersPayments() {
		return usersPayments;
	}
	public void setUsersPayments(UsersPayments usersPayments) {
		this.usersPayments = usersPayments;
	}
	
// sales
    @OneToMany(mappedBy = "userOrders", fetch = FetchType.EAGER)
	public Set<Sales> getSales() {
		return sales;
	}
	
	public void setSales(Set<Sales> sales) {
		this.sales = sales;
	}
	
}
