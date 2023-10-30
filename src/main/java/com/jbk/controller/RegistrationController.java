package com.jbk.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@CrossOrigin // use for back-end connection
public class RegistrationController {
	@Autowired
	private ServiceRegistration sreg;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Registration registration) {

		Map<String, Object> user = sreg.login(registration);
		if (user != null) {
			return new ResponseEntity(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, Object>>(user, HttpStatus.NOT_FOUND);
		}

	}
}
