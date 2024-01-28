package mx.com.desivecore.domain.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.domain.users.ports.UserServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class UserServiceImpl implements UserServicePort {

	@Autowired
	private UserPersistencePort userPersistencePort;

	private UserValidator userValidator = new UserValidator();

	@Override
	public ResponseModel createUser(UserModel user) {
		log.info("INIT createUser()");
		String validations = userValidator.validOperativeDataToCreate(user, userPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		UserModel userCreated = userPersistencePort.saveUser(user);
		if (userCreated == null) {
			log.severe("ERROR");
			throw new InternalError();
		}
		return new ResponseModel(userCreated);
	}

	@Override
	public ResponseModel viewUserListByParams(UserSearchParams userSearchParams) {
		log.info("INIT viewUserListByParams()");
		List<UserModel> userList = userPersistencePort.viewUserListByParams(userSearchParams);
		if (userList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(userList);
	}

	@Override
	public ResponseModel viewUserDetailById(Long userId) {
		log.info("INIT viewUserDetailById");
		UserModel user = userPersistencePort.viewUserDetailById(userId);
		if (user == null) {
			log.warning("USER NOT FOUND: " + userId);
			throw new ValidationError("Usuario no existe");
		}
		return new ResponseModel(user);
	}

	@Override
	public ResponseModel updateUserById(UserModel user) {
		log.info("INIT updateUserById()");
		String validations = userValidator.validOperativeDataToUpdate(user, userPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		UserModel userUpdated = userPersistencePort.saveUser(user);
		if (userUpdated == null) {
			log.severe("ERROR");
			throw new InternalError();
		}
		return new ResponseModel(userUpdated);
	}

	@Override
	public ResponseModel changeUserStatusById(String status, Long id) {
		log.info("INIT changeUserStatusById()");
		boolean userStatus = status.equals("ACTIVE") ? true : false;
		boolean changeStatus = userPersistencePort.changeUserStatusById(userStatus, id);
		if (!changeStatus)
			throw new InternalError();
		return new ResponseModel(changeStatus);
	}

	@Override
	public ResponseModel viewAllUsers() {
		log.info("INIT changeUserStatusById()");
		List<UserModel> userList = userPersistencePort.viewAllUser();
		if (userList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(userList);
	}

}
