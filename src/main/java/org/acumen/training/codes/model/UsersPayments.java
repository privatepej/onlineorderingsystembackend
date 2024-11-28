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
@Table(name = "users_payments")
public class UsersPayments {
	
	private Integer paymentid;
	private Integer userid;
	private String paymenttype;
	private String securitycode;
	private LocalDate expirationdate;
	private String cardtype;
	private String cardholdername;
	
	
	private Users users;

	private Set<UserOrders> userOrders = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paymentid", nullable = false, unique = true)
	public Integer getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}
	
	@Column(name = "userid", nullable = false)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "paymenttype", nullable = false, length = 50)
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	
	@Column(name = "securitycode", nullable = false, length = 10)
	public String getSecuritycode() {
		return securitycode;
	}
	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}
	
	@Column(name = "expirationdate", nullable = false)
	public LocalDate getExpirationdate() {
		return expirationdate;
	}
	
	public void setExpirationdate(LocalDate expirationdate) {
		this.expirationdate = expirationdate;
	}
	
	@Column(name = "cardtype", nullable = false, length = 50)
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	@Column(name = "cardholdername", nullable = false, length = 100)
	public String getCardholdername() {
		return cardholdername;
	}
	public void setCardholdername(String cardholdername) {
		this.cardholdername = cardholdername;
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
	
	
// userOrders
	@OneToMany(mappedBy = "usersPayments", fetch = FetchType.EAGER )
	public Set<UserOrders> getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(Set<UserOrders> userOrders) {
		this.userOrders = userOrders;
	}
}
