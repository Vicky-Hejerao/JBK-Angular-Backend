package com.jbk.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;


@RestController
@RequestMapping("/registration")
@CrossOrigin (origins = "http://localhost:4200")
public class EmployeeRestController {
	
	
	@Autowired
	private SessionFactory sessionfactory;
	private Employee employee;
	
	@Value("${image.upload.directory}")
    private String uploadDirectory; // The directory where images will be stored
	
	private final EmployeeCriteriaHandler employeeCriteriaHandler;
	
    @Autowired
    public EmployeeRestController(EmployeeCriteriaHandler employeeCriteriaHandler) {
        this.employeeCriteriaHandler = employeeCriteriaHandler;
    }
	@PostMapping("postEmpl")
	public String postEmpl(@RequestBody Employee employee) {
		Session session = null;
		try {
			session = sessionfactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(employee.getCountry());

			session.save(employee);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			return "Error creating employee=" + employee.getName();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return "Employee created successfully=" + employee.getName();
	}

	@GetMapping("getEmpById/{id}")
	public Employee getEmpById(@PathVariable int id) {

		Session session = sessionfactory.openSession();

		Employee employee = session.get(Employee.class, id);
		session.close();
		return employee;

	}

	@PutMapping("updateEmployeeapi/{id}")
	@Transactional
	public ResponseEntity<String> updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
		//Session session = (Session) sessionfactory.getCurrentSession().get(Employee.class, id);

		try {
			Employee existingEmployee = sessionfactory.getCurrentSession().get(Employee.class, id);
			//session = sessionfactory.openSession();
//			Transaction tx = session.beginTransaction();
//			EmployeeRestController emp=new EmployeeRestController();
//			Employee empByid1 = emp.getEmpByid(id,session);
			//Employee existingEmployee = session.get(Employee.class, id);
//			if (existingEmployee == null) {
//				tx.rollback();
//				session.close();
//				return "Employee not found";
//			}
			existingEmployee.setName(employee.getName());
	        existingEmployee.setEmailid(employee.getEmailid());
	        existingEmployee.setMobileno(employee.getMobileno());
	        existingEmployee.setStatus(employee.getStatus());
	        existingEmployee.setDepartment(employee.getDepartment());
	        existingEmployee.setCreatedDate(employee.getCreatedDate());
	        existingEmployee.setCreatedBy(employee.getCreatedBy());
	        existingEmployee.setUpdatedDate(employee.getUpdatedDate());
	        existingEmployee.setUpdatedBy(employee.getUpdatedBy());
	        existingEmployee.setCountry(employee.getCountry());
			
//			Country country = existingEmployee.getCountry();
//	        if (country != null) {
//	        	session.evict(country);
//	            session.update(country);
//	        }
//			session.evict(existingEmployee);
//			session.update(existingEmployee);
//			tx.commit();
//			System.out.println("Employee updated successfully.");
//			return "Employee Updated Successfully: ID=" + id;
	        System.out.println("Employee updated successfully.");
	        return ResponseEntity.ok("Employee Updated Successfully: ID=" + id);
	        
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to update Employee with ID=" + id);
	    }
//			finally {
//			if (session != null) {
//				session.clear();
//				session.close(); // Close the Hibernate session in a finally block
//			}
//		}
	}
	private Employee getEmpByid(int id, Session session) {
	    return session.get(Employee.class, id);
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable int id) {
		Session session = null;
		try {
			session = sessionfactory.openSession();
			org.hibernate.Transaction tx = session.beginTransaction();

			Employee employee = session.get(Employee.class, id);

			if (employee == null) {
				tx.rollback();
				session.close();
				return "Employee not found";
			}

			Country country = employee.getCountry();
			session.delete(country);

			session.delete(employee);

			tx.commit();

			return "Employee deleted successfully=" + employee.getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error deleting employee" + employee.getName();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@GetMapping("getAllEmployees")
	public List<Employee> getAllEmployees() {
		List<Employee> employees = null;
		Session session = null;
		try {
			session = sessionfactory.openSession();
			Query query = session.createQuery("FROM Employee");
			employees = query.getResultList();
			Iterator<Employee> itr = employees.iterator();
			while (itr.hasNext()) {
				itr.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return employees;
	}

	@GetMapping("getemployeebyStatus/{status}")
	public List<Employee> getEmployeesByStatus(@PathVariable String status) {
		Session session = sessionfactory.openSession();
		List<Employee> employees = null;
		try {
			Query query = session.createQuery("FROM Employee WHERE status = :status");
			query.setParameter("status", status);
			employees = query.getResultList();
			System.out.println(employees);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employees;
	}

	@GetMapping("getallcountry")
	List<Country> getallcountry() {
		Session session = null;
		List<Country> list = null;
		try {
			session = sessionfactory.openSession();
			Query query = session.createQuery("from Country");
			list = query.getResultList();
			list.forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;

	}

	@PostMapping("createCountry")
	public Object createCountry(@RequestBody Country country) {
		Session session = sessionfactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		String msg = null;
		try {
			session.save(country);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			msg = "Country not created";
		}

		return msg = "Country created successfully";
	}

	@PutMapping("updateCountry")
	public Object updateCountry(@RequestBody Country country) {
		Session session = sessionfactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		String msg = null;
		try {
			Country existingCountry = session.get(Country.class, country.getCid());
			if (existingCountry != null) {
				existingCountry.setCid(country.getCid());
				existingCountry.setCname(country.getCname());
				session.saveOrUpdate(existingCountry);
				tx.commit();
			}
		} catch (Exception e) {
			msg = "Country not updated ";
			e.printStackTrace();
		}

		return msg = "Country updated successfully";
	}

	@DeleteMapping("deleteCountrybyId/{cid}")
	public Object deleteCountry(@PathVariable int cid) {
		Session session = sessionfactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		Country country = null;
		String msg = null;
		try {
			country = session.get(Country.class, cid);
			if (country != null) {
				session.delete(country);
				tx.commit();

			} else {
				msg = "Country with id " + cid + " not found.";
			}
		} catch (Exception e) {

			e.printStackTrace();

		}
		return msg = "Country Deleted" + country.getCid();
	}

	@GetMapping("getCountryId/{cid}")
	public Country getCountryById(@PathVariable int cid) {
		Session session = sessionfactory.openSession();

		Country country = session.get(Country.class, cid);
		System.out.println(country);
		return country;
	}
	
	// API to update an employee's status
//    @PutMapping("updatebystatus/{id}/status")
//    public ResponseEntity<Object> updateEmployeeStatus(
//            @PathVariable("id") Integer employeeId,
//            @RequestParam("status") String newStatus
//    ) {
//        Employee updatedEmployee = employeeCriteriaHandler.updateEmployeeStatus(employeeId, newStatus);
//
//        if (updatedEmployee != null) {
//            return ResponseEntity.ok("Employee status updated successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or status cannot be updated to 'suspend'");
//        }
//    }
	 @GetMapping("updatebystatus/{id}")
	    public ResponseEntity<Object> updateEmployeeStatus(
	            @PathVariable("id") Integer employeeId
	           
	    ) {
	        Employee updatedEmployee = employeeCriteriaHandler.updateEmployeeStatus(employeeId);

	        if (updatedEmployee != null) {
	            return ResponseEntity.ok("Employee status updated successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or status cannot be updated to 'suspend'");
	        }
	    }
	 
    // API to find employees by status
    @GetMapping("/GetstatusEmp")
    public ResponseEntity<List<Employee>> findEmployeesByStatus(
            @RequestParam("status") String status
    ) {
        List<Employee> employees = employeeCriteriaHandler.findEmployeesByStatus(status);

        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //Upload Image
//    @PostMapping("/uploadImage")
//    public ResponseEntity<Object> uploadAndSaveImage(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Generate a unique filename using UUID
//            String originalFilename = file.getOriginalFilename();
//            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
//           // String imagePath = Paths.get(uploadDirectory, uniqueFileName).toString();
//            
//            // Construct the imagePath using the file separator
//            String fileSeparator = File.separator;
//            //String imagePath = uploadDirectory + fileSeparator + uniqueFileName;
//            String imagePath = uploadDirectory.replace("\\", "/") + "/" + uniqueFileName;
//            
//            // Save the image to the specified directory
////            File dest = new File(imagePath);
////            file.transferTo(dest);
//            
//            File directory = new File(uploadDirectory);
//            if (!directory.exists()) {
//                directory.mkdirs(); // Create the directory if it doesn't exist
//            }
//
//            long size = file.getSize();
//            
//            // You can also save the imagePath to a database if needed
//            ImageInfo imageinfo = new ImageInfo();
//            imageinfo.setDownloadUrl(imagePath);
//            imageinfo.setSize(size);
//            imageinfo.setImagePath(imagePath);
//            imageinfo.setMsg("File uploaded and saved successfully.");
//           
//           
//            
//            // Open a HibernateSession and save the entity
//            try (Session session = sessionfactory.openSession()) {
//                session.beginTransaction();
//                session.save(imageinfo);
//                session.getTransaction().commit();
//            }
//
//            return new ResponseEntity<>(imageinfo, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Failed to upload and save the file.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    
    @PostMapping("/uploadImageFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException{
    	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    	long size = multipartFile.getSize();
    	String fileCode =FileUploadService.saveFile(fileName, multipartFile);
    	
    	FileUploadResponse response=new FileUploadResponse();
    	response.setFileName(fileName);
    	response.setDownloadUrl("/DownLoadFile"+fileCode);
    	response.setSize(size);
    	response.setMsg("File uploaded and saved successfully.");
    	
		return new ResponseEntity<>(response,HttpStatus.OK);
    	
    }
}
