package com.jbk.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;

@Repository
public class DaoRegistration {
    @Autowired
    private SessionFactory factory;

    public Registration login(Registration reg) {
    	Session session = factory.openSession();
        try {
            Criteria criteria = session.createCriteria(Registration.class);
            criteria.add(Restrictions.eq("emailId",reg.getEmailId()));
            criteria.add(Restrictions.eq("password",reg.getPassword()));
            Registration user = (Registration) criteria.uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
			if (session!=null) {
				session.close();
			}
		}
    }
}

