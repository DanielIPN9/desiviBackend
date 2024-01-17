package mx.com.desivecore.domain.security.ports;

import java.util.List;

import mx.com.desivecore.domain.security.models.Permission;

public interface SecurityPersistencePort {

	List<Permission> findPermissionListByUserId(Long userId);

}
