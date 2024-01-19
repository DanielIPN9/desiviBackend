package mx.com.desivecore.infraestructure.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.domain.security.models.Rol;
import mx.com.desivecore.domain.security.ports.SecurityPersistencePort;
import mx.com.desivecore.infraestructure.security.converters.PermissionConverter;
import mx.com.desivecore.infraestructure.security.converters.RoleConverter;
import mx.com.desivecore.infraestructure.security.entities.PermissionEntity;
import mx.com.desivecore.infraestructure.security.entities.RolEntity;
import mx.com.desivecore.infraestructure.security.repositories.PermissionRepository;
import mx.com.desivecore.infraestructure.security.repositories.RoleRepository;

@Log
@Service
public class SecurityPersistenceService implements SecurityPersistencePort {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RoleConverter roleConverter;

	@Autowired
	private PermissionConverter permissionConverter;

	@Override
	public Rol saveRole(Rol rol) {
		try {
			log.info("INIT saveRole()");
			RolEntity rolEntity = roleConverter.rolToRoleEntity(rol);
			List<PermissionEntity> permissionEntityList = permissionConverter
					.permissionListToPermisionEntityList(rol.getPermissions());
			rolEntity.setPermissions(permissionEntityList);
			rolEntity = roleRepository.save(rolEntity);
			return roleConverter.rolEntityToRol(rolEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Rol> viewAllRole() {
		try {
			log.info("INIT viewAllRole()");
			List<RolEntity> rolEntityList = roleRepository.findAll();
			return roleConverter.rolEntityListToRolList(rolEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Rol viewRoleDetailById(Long roleId) {
		try {
			log.info("INIT viewRoleDetailById()");
			Optional<RolEntity> rolOptional = roleRepository.findById(roleId);
			Rol rol = null;
			if (rolOptional.isPresent())
				rol = roleConverter.rolEntityToRol(rolOptional.get());
			return rol;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Permission> findPermissionListByUserId(Long userId) {
		try {
			log.info("INIT findPermissionListByUserId()");
			List<PermissionEntity> permissionEntityList = permissionRepository.findAllByUserId(userId);
			return permissionConverter.permissionEntityListToPermissionList(permissionEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Permission> findAllPermissions() {
		try {
			log.info("INIT findAllPermissions()");
			List<PermissionEntity> permissionEntityList = permissionRepository.findAll();
			return permissionConverter.permissionEntityListToPermissionList(permissionEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}
}
