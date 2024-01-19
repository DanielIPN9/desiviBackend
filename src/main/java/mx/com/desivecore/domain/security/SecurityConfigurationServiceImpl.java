package mx.com.desivecore.domain.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.domain.security.models.Rol;
import mx.com.desivecore.domain.security.ports.SecurityPersistencePort;
import mx.com.desivecore.domain.security.ports.SecurityServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class SecurityConfigurationServiceImpl implements SecurityServicePort {

	@Autowired
	private SecurityPersistencePort securityPersistencePort;

	private RoleValidator roleValidator = new RoleValidator();

	@Override
	public ResponseModel createRole(Rol rol) {
		log.info("INIT createRole()");
		String validations = roleValidator.validOperativeDataToCreate(rol, securityPersistencePort);
		if (!validations.isEmpty()) {
			log.warning(String.format("VALIDATION: %s", validations));
			throw new ValidationError(validations);
		}
		Rol rolCreated = securityPersistencePort.saveRole(rol);
		if (rolCreated == null)
			throw new InternalError();
		return new ResponseModel(rolCreated);
	}

	@Override
	public ResponseModel viewAllRole() {
		log.info("INIT viewAllRole()");
		List<Rol> rolList = securityPersistencePort.viewAllRole();
		if (rolList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(rolList);
	}

	@Override
	public ResponseModel viewRoleDetailById(Long roleId) {
		log.info("INIT viewRoleDetailById()");
		Rol rol = securityPersistencePort.viewRoleDetailById(roleId);
		if (rol == null) {
			log.warning("DATA NOT FOUND: " + roleId.toString());
			throw new ValidationError("Registro no encontrado");
		}
		return new ResponseModel(rol);
	}

	@Override
	public ResponseModel updateRoleById(Rol rol) {
		log.info("INIT updateRoleById()");
		String validations = roleValidator.validOperativeDataToUpdate(rol, securityPersistencePort);
		if (!validations.isEmpty()) {
			log.warning(String.format("VALIDATION: %s", validations));
			throw new ValidationError(validations);
		}
		Rol rolCreated = securityPersistencePort.saveRole(rol);
		if (rolCreated == null)
			throw new InternalError();
		return new ResponseModel(rolCreated);
	}

	@Override
	public ResponseModel viewPermissios() {
		log.info("INIT viewPermissios()");
		List<Permission> permissionList = securityPersistencePort.findAllPermissions();
		if (permissionList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(permissionList);
	}

}
