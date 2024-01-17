package mx.com.desivecore.infraestructure.security;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.domain.security.ports.SecurityPersistencePort;

@Service
public class SecurityPersistenceService implements SecurityPersistencePort {

	@Override
	public List<Permission> findPermissionListByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
