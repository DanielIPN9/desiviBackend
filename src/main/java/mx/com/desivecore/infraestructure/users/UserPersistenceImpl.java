package mx.com.desivecore.infraestructure.users;

import org.springframework.stereotype.Service;

import mx.com.desivecore.domain.users.models.User;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;

@Service
public class UserPersistenceImpl implements UserPersistencePort{

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
