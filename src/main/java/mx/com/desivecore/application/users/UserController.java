package mx.com.desivecore.application.users;

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
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;
import mx.com.desivecore.domain.users.ports.UserServicePort;

@Log
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServicePort userServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody UserModel user) {
		log.info("INIT createUser()");
		log.info(String.format("PARAMS:[%s]", user.toString()));
		ResponseModel responseModel = userServicePort.createUser(user);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllUsers() {
		log.info("INIT viewAllUsers()");
		ResponseModel responseModel = userServicePort.viewAllUsers();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<?> viewUsersByParams(@RequestBody UserSearchParams userSearchParams) {
		log.info("INIT viewUsersByParams()");
		log.info(String.format("PARAMS:[%s]", userSearchParams.toString()));
		ResponseModel responseModel = userServicePort.viewUserListByParams(userSearchParams);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{id}")
	public ResponseEntity<?> viewUserDetailById(@PathVariable Long id) {
		log.info("INIT viewUserDetailById()");
		log.info(String.format("PARAMS:[%s]", id.toString()));
		ResponseModel responseModel = userServicePort.viewUserDetailById(id);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUserById(@RequestBody UserModel user) {
		log.info("INIT updateUserById()");
		log.info(String.format("PARAMS:[%s]", user.toString()));
		ResponseModel responseModel = userServicePort.updateUserById(user);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/change-status/{status}/{id}")
	public ResponseEntity<?> changeUserStatusById(@PathVariable String status, @PathVariable Long id) {
		log.info("INIT changeUserStatusById()");
		log.info(String.format("PARAMS:[status: %s, id: %s]", status, id.toString()));
		ResponseModel responseModel = userServicePort.changeUserStatusById(status, id);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
