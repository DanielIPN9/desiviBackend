package mx.com.desivecore.domain.users.ports;

import java.util.List;

import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;

public interface UserPersistencePort {

	UserModel saveUser(UserModel user);

	List<UserModel> viewUserListByParams(UserSearchParams userSearchParams);
	
	List<UserModel> viewAllUser();

	UserModel viewUserDetailById(Long userId);

	boolean changeUserStatusById(boolean status, Long userId);

	UserModel findUserByEmail(String email, boolean excludePassword);
	
	UserModel findUserByEmailAndIdNot(String email, Long userId);
}
