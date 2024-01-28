package mx.com.desivecore.infraestructure.configuration.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;

@Log
@Component
public class AditionalInformationToken implements TokenEnhancer {

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		log.info("Additional information by user: " + authentication.getName());
		Map<String, Object> mapInfo = new HashMap<String, Object>();

		UserModel user = userPersistencePort.findUserByEmail(authentication.getName(), false);

		mapInfo.put("email", user.getEmail());
		mapInfo.put("firstname", user.getName() + " " + user.getFirstSurname() + " " + user.getSecondSurname());

		authentication.setDetails(mapInfo);

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(mapInfo);
		return accessToken;
	}

}
