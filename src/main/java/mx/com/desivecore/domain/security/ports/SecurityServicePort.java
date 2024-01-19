package mx.com.desivecore.domain.security.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.security.models.Rol;

public interface SecurityServicePort {

	ResponseModel createRole(Rol rol);

	ResponseModel viewAllRole();

	ResponseModel viewRoleDetailById(Long roleId);

	ResponseModel updateRoleById(Rol rol);

	ResponseModel viewPermissios();

}
