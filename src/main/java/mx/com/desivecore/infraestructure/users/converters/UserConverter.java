package mx.com.desivecore.infraestructure.users.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.infraestructure.security.converters.RoleConverter;
import mx.com.desivecore.infraestructure.security.entities.RolEntity;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;

@Component
public class UserConverter {

	@Autowired
	private RoleConverter roleConverter;

	public UserEntity userToUserEntity(UserModel user) {
		UserEntity userEntity = new UserEntity();

		userEntity.setUserId(user.getUserId());
		userEntity.setBranchId(user.getBranch().getBranchId());
		userEntity.setName(user.getName());
		userEntity.setFirstSurname(user.getFirstSurname());
		userEntity.setSecondSurname(user.getSecondSurname());
		userEntity.setEmail(user.getEmail());
		userEntity.setContactNumber(user.getContactNumber());
		userEntity.setPassword(user.getPassword());
		userEntity.setStatus(user.isStatus());

		return userEntity;
	}

	public UserModel userEntityToUser(UserEntity userEntity, boolean excludePassword) {

		UserModel user = new UserModel();

		user.setUserId(userEntity.getUserId());
		if (userEntity.getRoles() != null) {
			List<RolEntity> rolEntityList = new ArrayList<>(userEntity.getRoles());
			user.setRoles(roleConverter.rolEntityListToRolList(rolEntityList));
		}
		user.setName(userEntity.getName());
		user.setFirstSurname(userEntity.getFirstSurname());
		user.setSecondSurname(userEntity.getSecondSurname());
		user.setEmail(userEntity.getEmail());
		user.setContactNumber(userEntity.getContactNumber());
		user.setStatus(userEntity.isStatus());
		if(excludePassword) {
			user.setPassword("");
		}else {
			user.setPassword(userEntity.getPassword());
		}
			

		return user;
	}

	public List<UserEntity> userListToUserEntityList(List<UserModel> userList) {
		List<UserEntity> userEntityList = new ArrayList<>();
		for (UserModel user : userList) {
			userEntityList.add(userToUserEntity(user));
		}
		return userEntityList;
	}

	public List<UserModel> userEntityListToUserList(List<UserEntity> userEntityList, boolean excludePassword) {
		List<UserModel> userList = new ArrayList<>();
		for (UserEntity userEntity : userEntityList) {
			userList.add(userEntityToUser(userEntity, excludePassword));
		}
		return userList;
	}
}
