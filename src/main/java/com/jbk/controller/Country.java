package com.jbk.controller;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
public class Country {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int cid;
   private String cname;
      
   public Country() {
	
}

@Override
public String toString() {
	return "Country [cid=" + cid + ", cname=" + cname + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
			+ ", toString()=" + super.toString() + "]";
}

public Country(int cid, String cname) {
	super();
	this.cid = cid;
	this.cname = cname;
}

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public String getCname() {
	return cname;
}

public void setCname(String cname) {
	this.cname = cname;
}



}
