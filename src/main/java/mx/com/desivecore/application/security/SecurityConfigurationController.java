package mx.com.desivecore.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.security.models.Rol;
import mx.com.desivecore.domain.security.ports.SecurityServicePort;

@Log
@RestController
@RequestMapping("/security-configuration")
public class SecurityConfigurationController {

	@Autowired
	private SecurityServicePort securityServicePort;

	@PostMapping("/create-role")
	public ResponseEntity<?> createRole(@RequestBody Rol rol) {
		log.info("INIT createRole()");
		log.info(String.format("PARAMS: [%s]", rol.toString()));
		ResponseModel response = securityServicePort.createRole(rol);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-role/list")
	public ResponseEntity<?> viewAllRole() {
		log.info("INIT viewAllRole()");
		ResponseModel response = securityServicePort.viewAllRole();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-role/detail/{id}")
	public ResponseEntity<?> viewRoleDetailById(@PathVariable Long id) {
		log.info("INIT viewRoleDetailById()");
		log.info(String.format("PARAMS: [%s]", id.toString()));
		ResponseModel response = securityServicePort.viewRoleDetailById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update-role")
	public ResponseEntity<?> updateRoleById(@RequestBody Rol rol) {
		log.info("INIT updateRoleById()");
		log.info(String.format("PARAMS: [%s]", rol.toString()));
		ResponseModel response = securityServicePort.createRole(rol);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-permission/list")
	public ResponseEntity<?> viewAllPermissions() {
		log.info("INIT viewAllPermissions()");
		ResponseModel response = securityServicePort.viewPermissios();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
