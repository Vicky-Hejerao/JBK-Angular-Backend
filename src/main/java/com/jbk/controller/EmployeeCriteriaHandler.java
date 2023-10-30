package com.jbk.controller;

import java.util.Date;
import java.util.List;
//import java.util.function.Predicate;
import javax.persistence.criteria.Predicate;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Transactional
public class EmployeeCriteriaHandler {
	
	private final EntityManager entityManager;

    @Autowired
    public EmployeeCriteriaHandler(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Employee> findEmployeesByStatus(String status) {
    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
        query.where(statusPredicate);

        return entityManager.createQuery(query).getResultList();
    }

//    public Employee updateEmployeeStatus(Integer employeeId, String newStatus) {
//        Employee employee = entityManager.find(Employee.class, employeeId);
//        String isSuspend=null;
//        if (employee != null) {
//            // Check if the new status is not "suspend" before updating
//            if (!"suspend".equals(newStatus)) {
//                employee.setStatus(newStatus);
//                employee.setUpdatedDate(new Date()); // Set the updated date
//                entityManager.merge(employee);
//                return employee;
//            }else {
//            	System.out.println(isSuspend="Not Changed Status Employee was Suspended");
//            	
//            }
//        }else {
//            System.out.println("Status 'suspend' is not allowed for updates");
//        }
//
//        return null; // Employee not found or status cannot be updated to "suspend"
//    }
    public Employee updateEmployeeStatus(Integer employeeId) {
        Employee employee = entityManager.find(Employee.class, employeeId);
        String isSuspend=null;
        if (employee != null) {
            // Check if the new status is not "suspend" before updating
            if ("active".equalsIgnoreCase(employee.getStatus())) {
                employee.setStatus("inactive");
                employee.setUpdatedDate(new Date()); // Set the updated date
                entityManager.merge(employee);
                return employee;
            }else {
            	employee.setStatus("active");
            	 return employee;
            }
        }else {
            System.out.println("Status 'suspend' is not allowed for updates");
        }

        return null; 
    }
}
