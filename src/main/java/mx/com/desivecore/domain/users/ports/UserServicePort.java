package mx.com.desivecore.domain.users.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;

public interface UserServicePort {

	ResponseModel createUser(UserModel user);
	
	ResponseModel viewAllUsers();

	ResponseModel viewUserListByParams(UserSearchParams userSearchParams);

	ResponseModel viewUserDetailById(Long userId);

	ResponseModel updateUserById(UserModel user);

	ResponseModel changeUserStatusById(String status, Long id);

}
