package mx.com.desivecore.infraestructure.configuration.security;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import mx.com.desivecore.commons.constants.PermissionEnum;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.headers(headers -> headers.frameOptions().disable());

		/*
		 * Se deberan de registrar todas las url y el permiso correspondiente
		 */
		// ACCESOS PARA GESTION DE SUCURSALES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/branch/create").hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/branch/update").hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/branch/view-all").hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/branch/view-detail/{id}").hasAuthority(PermissionEnum.BRANCH.toString()));
		
		//ACCESOS PARA LA GESTION DE CONFIGURACION DE ROLES Y PERMISOS 
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/security-configuration/create-role").hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-role/list").hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-role/detail/{id}").hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/security-configuration/update-role").hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-permission/list").hasAuthority(PermissionEnum.ROLE.toString()));
		
		//ACCESOS PARA LA GESTION DE USUARIOS 
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/users/create").hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/users/search").hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/users/view-detail/{id}").hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/users/view-all").hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/users/update").hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/users/change-status/{status}/{id}").hasAuthority(PermissionEnum.USER.toString()));
		
		//ACCESOS PARA LA GESTION DE PRODUCTOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/product/create").hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product/view-all").hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/product/search").hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product/view-detail/**").hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/product/update").hasAuthority(PermissionEnum.PRODUCT.toString()));
		
		//ACCESOS PARA LA GESTION DE CLIENTES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/client/create").hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/client/view-all").hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/client/view-detail/**").hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/client/update").hasAuthority(PermissionEnum.CLIENT.toString()));
		
		//ACCESOS PARA LA GESTION DE PROVEEDORES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/supplier/create").hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/supplier/view-all").hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/supplier/view-detail/{supplierId}").hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/supplier/update").hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/supplier/change-status/{status}/{supplierId}").hasAuthority(PermissionEnum.SUPPLIER.toString()));
		
		//ACCESOS PARA LA GESTION DE PROVEEDORES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-entry/create")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-detail/{remissionEntryId}")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-entry/search")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/remission-entry/update")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-history/{remissionEntryId}")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/generate/document/{remissionEntryId}")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/sipplier")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/branch")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/product")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(), PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
				
		/*
		 * Se habiltan las para la obtención del token de acceso
		 */
		http.authorizeRequests(requests -> requests.antMatchers("/oauth/token", "/api/security/access/oauth/token")
				.permitAll().anyRequest().authenticated())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()));

	}

	@Primary
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("POST", "DELETE", "PUT", "GET", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filter;
	}

}
