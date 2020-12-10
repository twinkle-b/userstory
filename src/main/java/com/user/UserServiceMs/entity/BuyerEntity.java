package com.user.UserServiceMs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyer")
public class BuyerEntity {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="BUYERID",nullable = false)
	private Integer buyerId;
	@Column(name="NAME",nullable = false,length = 45)
	private String  name;
	@Column(name="EMAIL",nullable = false,length = 45)
	private String email;
	@Column(name="PHONENUMBER",nullable = false,length = 45)
	private String phoneNumber;
	@Column(name="PASSWORD",nullable = false,length = 45)
    private String password;
	@Column(name="ISPRIVILEGED")
	private Integer isPrivileged;
	@Column(name="REWARDPOINTS")
	private Integer rewardPoints;
	@Column(name="ISACTIVE")
	private Integer isActive;
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIsPrivileged() {
		return isPrivileged;
	}
	public void setIsPrivileged(Integer isPrivileged) {
		this.isPrivileged = isPrivileged;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	public BuyerEntity()
	{
		super();
	}
	
}
