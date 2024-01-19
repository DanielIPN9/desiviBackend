package mx.com.desivecore.domain.security.ports;

import java.util.List;

import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.domain.security.models.Rol;

public interface SecurityPersistencePort {

	Rol saveRole(Rol rol);

	List<Rol> viewAllRole();

	Rol viewRoleDetailById(Long roleId);

	List<Permission> findPermissionListByUserId(Long userId);
	
	List<Permission> findAllPermissions();

}
