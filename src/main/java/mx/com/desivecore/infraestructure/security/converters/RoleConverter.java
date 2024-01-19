package mx.com.desivecore.infraestructure.security.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.security.models.Rol;
import mx.com.desivecore.infraestructure.security.entities.PermissionEntity;
import mx.com.desivecore.infraestructure.security.entities.RolEntity;

@Component
public class RoleConverter {

	@Autowired
	private PermissionConverter permissionConverter;

	public RolEntity rolToRoleEntity(Rol rol) {
		RolEntity rolEntity = new RolEntity();

		rolEntity.setId(rol.getRoleId());
		rolEntity.setName(rol.getName());
		rolEntity.setDescription(rol.getDescription());

		return rolEntity;
	}

	public Rol rolEntityToRol(RolEntity rolEntity) {
		Rol rol = new Rol();

		rol.setRoleId(rolEntity.getId());
		rol.setName(rolEntity.getName());
		rol.setDescription(rolEntity.getDescription());
		List<PermissionEntity> permissionEntityList = new ArrayList<>(rolEntity.getPermissions());
		rol.setPermissions(permissionConverter.permissionEntityListToPermissionList(permissionEntityList));

		return rol;
	}

	public List<RolEntity> rolListToRoleEntityList(List<Rol> rolList) {
		List<RolEntity> rolEntityList = new ArrayList<>();
		for (Rol rol : rolList) {
			rolEntityList.add(rolToRoleEntity(rol));
		}
		return rolEntityList;
	}

	public List<Rol> rolEntityListToRolList(List<RolEntity> rolEntityList) {
		List<Rol> rolList = new ArrayList<>();
		for (RolEntity rolEntity : rolEntityList) {
			rolList.add(rolEntityToRol(rolEntity));
		}
		return rolList;
	}
}
