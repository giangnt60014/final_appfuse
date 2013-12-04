package com.giangnt.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fsaccount")
public class Fsaccount {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Fsaccount() {
	}

	private Long id;
	private String account;
	private String security;

	public Fsaccount(Long id, String account, String security) {
		this.id = id;
		this.account = account;
		this.security = security;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="account", length=50)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name="security", length=50)
	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}
/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
*/
}
