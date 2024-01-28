package mx.com.desivecore.infraestructure.users;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.models.UserSearchParams;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.security.converters.RoleConverter;
import mx.com.desivecore.infraestructure.security.entities.RolEntity;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;
import mx.com.desivecore.infraestructure.users.repositories.CustomDSLUserRepository;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;

@Log
@Service
public class UserPersistenceImpl implements UserPersistencePort {

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private RoleConverter roleConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private CustomDSLUserRepository cumCustomDSLUserRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	@Override
	public UserModel saveUser(UserModel user) {
		try {
			log.info("INIT saveUser()");
			UserEntity userEntity = userConverter.userToUserEntity(user);
			List<RolEntity> rolEntityList = roleConverter.rolListToRoleEntityList(user.getRoles());

			userEntity.setRoles(rolEntityList);
			if (user.getPassword() != "") {
				userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			} else {
				Optional<UserEntity> userSearch = userRepository.findById(user.getUserId());
				userEntity.setPassword(userSearch.get().getPassword());
			}
			userEntity = userRepository.save(userEntity);

			UserModel userSaved = userConverter.userEntityToUser(userEntity, true);

			if (userEntity.getBranchId() != null)
				userSaved.setBranch(branchConverter
						.branchEntityToBranch(branchReposirory.findById(userEntity.getBranchId()).get()));
			
			return userSaved;

		} catch (Exception e) {
			log.severe("EXCEPTION ON SAVE: " + e.getMessage());
			return null;
		}

	}

	@Transactional
	@Override
	public List<UserModel> viewUserListByParams(UserSearchParams userSearchParams) {
		try {
			log.info("INIT viewUserListByParams()");
			List<UserModel> userModelList = cumCustomDSLUserRepository.findUserListByParams(userSearchParams);
			return userModelList;
		} catch (Exception e) {
			log.severe("ESCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Transactional
	@Override
	public UserModel viewUserDetailById(Long userId) {
		try {
			log.info("INIT viewUserDetailById()");
			Optional<UserEntity> userOptional = userRepository.findById(userId);
			UserModel user = null;
			if (userOptional.isPresent())
				user = userConverter.userEntityToUser(userOptional.get(), true);
			return user;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}

	}

	@Transactional
	@Override
	public boolean changeUserStatusById(boolean status, Long userId) {
		try {
			log.info("INIT changeUserStatusById()");
			int updatedRow = userRepository.enableById(userId, status);
			if (updatedRow <= 0)
				return false;
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Transactional
	@Override
	public UserModel findUserByEmail(String email, boolean excludePassword) {
		try {
			log.info("INIT findUserByEmail()");
			Optional<UserEntity> userOptional = userRepository.findByEmail(email);
			UserModel user = null;
			if (userOptional.isPresent())
				user = userConverter.userEntityToUser(userOptional.get(), excludePassword);
			return user;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Transactional
	@Override
	public UserModel findUserByEmailAndIdNot(String email, Long userId) {
		try {
			log.info("INIT findUserByEmailAndIdNot()");
			Optional<UserEntity> userOptional = userRepository.findByEmailAndIdNot(email, userId);
			UserModel user = null;
			if (userOptional.isPresent())
				user = userConverter.userEntityToUser(userOptional.get(), true);
			return user;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<UserModel> viewAllUser() {
		try {
			log.info("INIT viewAllUser()");
			List<UserEntity> userEntityList = userRepository.findAll();
			List<UserModel> userList = null;
			if (userEntityList != null)
				userList = userConverter.userEntityListToUserList(userEntityList, true);
			return userList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
