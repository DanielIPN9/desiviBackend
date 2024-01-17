package mx.com.desivecore.domain.users.ports;

import mx.com.desivecore.domain.users.models.User;

public interface UserPersistencePort {

	User findUserByEmail(String email);
}
