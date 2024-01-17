package mx.com.desivecore.infraestructure.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.security.models.Permission;
import mx.com.desivecore.domain.security.ports.SecurityPersistencePort;
import mx.com.desivecore.domain.users.models.User;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private SecurityPersistencePort securityPersistencePort;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userPersistencePort.findUserByEmail(email);
		List<GrantedAuthority> authorities;

		if (user == null) {
			log.info(String.format("USUARIO O CONTRASEÑA INVALIDOS: %s", email));
			throw new ValidationError("El usuario o contraseña son incorrectos");
		}
		List<Permission> permisions = securityPersistencePort.findPermissionListByUserId(user.getUserId());
		authorities = permisions.stream().map(permission -> new SimpleGrantedAuthority(permission.getName().toString()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), user.isStatus(), true,
				true, true, authorities);

	}

}
