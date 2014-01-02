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
	private Long using;

	public Fsaccount(Long id, String account, String security, Long using) {
		this.id = id;
		this.account = account;
		this.security = security;
		this.using = using;
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

	/**
	 * @return the using
	 */
	@Column(name="noOfUse")
	public Long getUsing() {
		return using;
	}

	/**
	 * @param using the using to set
	 */
	public void setUsing(Long using) {
		this.using = using;
	}
}
