package org.acumen.training.codes.model;

import java.util.HashSet;
import java.util.Set;

import org.acumen.training.codes.enums.USER_ROLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String confirmpassword;
	private String address;
	private USER_ROLE role;
	
	private Set<UserOrders> userOrders = new HashSet<>();
	private Set<UsersPayments> usersPayments = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "username", nullable = false, length = 50)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "email", nullable = false, length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "confirmpassword", nullable = false, length = 100)
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
	@Column(name = "address", nullable = false, length = 255)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	public USER_ROLE getRole() {
		return role;
	}
	public void setRole(USER_ROLE role) {
		this.role = role;
	}
	
// userOrders
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER )
	public Set<UserOrders> getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(Set<UserOrders> userOrders) {
		this.userOrders = userOrders;
	}
	
	
// usersPayments
	
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER )
	public Set<UsersPayments> getUsersPayments() {
		return usersPayments;
	}
	public void setUsersPayments(Set<UsersPayments> usersPayments) {
		this.usersPayments = usersPayments;
	} 
	
	
}
