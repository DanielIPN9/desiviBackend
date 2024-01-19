package mx.com.desivecore.infraestructure.security.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.commons.constants.PermissionEnum;
import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.infraestructure.security.entities.PermissionEntity;

@Component
public class PermissionConverter {

	public PermissionEntity permissionToPermisionEntity(Permission permission) {

		PermissionEntity permissionEntity = new PermissionEntity();

		permissionEntity.setId(permission.getId());
		permissionEntity.setName(PermissionEnum.valueOf(permission.getName()));

		return permissionEntity;
	}

	public Permission permissionEntityToPermission(PermissionEntity permissionEntity) {

		Permission permission = new Permission();

		permission.setId(permissionEntity.getId());
		permission.setName(permissionEntity.getName().toString());

		return permission;
	}

	public List<PermissionEntity> permissionListToPermisionEntityList(List<Permission> permissionList) {
		List<PermissionEntity> permissionEntityList = new ArrayList<>();
		for (Permission permission : permissionList) {
			permissionEntityList.add(permissionToPermisionEntity(permission));
		}
		return permissionEntityList;
	}

	public List<Permission> permissionEntityListToPermissionList(List<PermissionEntity> permissionEntityList) {
		List<Permission> permissioList = new ArrayList<>();
		for (PermissionEntity permissionEntity : permissionEntityList) {
			permissioList.add(permissionEntityToPermission(permissionEntity));
		}
		return permissioList;
	}
}
