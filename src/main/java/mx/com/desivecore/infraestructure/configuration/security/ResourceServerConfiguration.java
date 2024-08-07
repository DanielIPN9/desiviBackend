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
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/branch/create")
				.hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/branch/update")
				.hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/branch/view-all")
				.hasAuthority(PermissionEnum.BRANCH.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/branch/view-detail/{id}")
				.hasAuthority(PermissionEnum.BRANCH.toString()));

		// ACCESOS PARA LA GESTION DE CONFIGURACION DE ROLES Y PERMISOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/security-configuration/create-role")
				.hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-role/list")
						.hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-role/detail/{id}")
						.hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/security-configuration/update-role")
				.hasAuthority(PermissionEnum.ROLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/security-configuration/view-permission/list")
						.hasAuthority(PermissionEnum.ROLE.toString()));

		// ACCESOS PARA LA GESTION DE USUARIOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/users/create")
				.hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/users/search")
				.hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/users/view-detail/{id}")
				.hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/users/view-all")
				.hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/users/update")
				.hasAuthority(PermissionEnum.USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/users/change-status/{status}/{id}")
				.hasAuthority(PermissionEnum.USER.toString()));

		// ACCESOS PARA LA GESTION DE PRODUCTOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/product/create")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product/view-all")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/product/search")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product/view-detail/**")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/product/update")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/product/change-status/**")
				.hasAuthority(PermissionEnum.PRODUCT.toString()));

		// ACCESOS PARA LA GESTION DE CLIENTES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/client/create")
				.hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/client/view-all")
				.hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/client/view-detail/**")
				.hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/client/update")
				.hasAuthority(PermissionEnum.CLIENT.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/client/change-status/{status}/{clientId}")
						.hasAuthority(PermissionEnum.CLIENT.toString()));

		// ACCESOS PARA LA GESTION DE PROVEEDORES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/supplier/create")
				.hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/supplier/view-all")
				.hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/supplier/view-detail/{supplierId}")
				.hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/supplier/update")
				.hasAuthority(PermissionEnum.SUPPLIER.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/supplier/change-status/{status}/{supplierId}")
						.hasAuthority(PermissionEnum.SUPPLIER.toString()));

		// ACCESOS PARA LA GESTION DE REMISIÓN DE ENTRADA
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-entry/create")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-detail/{remissionEntryId}")
						.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
								PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/remission-entry/cancel/{remissionEntryId}")
						.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
								PermissionEnum.REMISSION_ENTRY_CANCEL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-entry/search")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/remission-entry/update")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString()));

		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-history/{remissionEntryId}")
						.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString()));

		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/remission-entry/generate/document/{remissionEntryId}")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/sipplier")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/branch")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-entry/view-all/product")
				.hasAnyAuthority(PermissionEnum.REMISSION_ENTRY_GENERAL.toString(),
						PermissionEnum.REMISSION_ENTRY_OPERATIONAL.toString()));

		// ACCESOS PARA LA GESTION DE ETIQUETAS DE PRODUCTOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/product-tag/create")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/view-all")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/view-detail/{tagId}")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/product-tag/update")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.DELETE, "/product-tag/remove/{tagId}")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/view-all/product")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/view-all/branch")
				.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/view-all/branch/phone/{branchId}")
						.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/product-tag/generate/document/{tagId}")
						.hasAuthority(PermissionEnum.PRODUCT_TAG.toString()));

		// ACCESOS PARA LA GESTION DE HOJAS DE SEGURIDAD
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/security-data-sheet/create")
				.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/security-data-sheet/view-all/product")
				.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/security-data-sheet/view-detail/{secDataSheetId}")
						.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/security-data-sheet/view-all")
				.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/security-data-sheet/update")
				.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/security-data-sheet/generate-document/{secDataSheetId}")
				.hasAuthority(PermissionEnum.SECURITY_DATA_SHEET.toString()));

		// ACCESOS PARA LA GESTION DE CERTIFICADOS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/certificate/view/remission-summary")
				.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/certificate/view-detail/{remissionId}/{productId}")
						.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/certificate/create")
				.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/certificate/update")
				.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/certificate/generate-document/{certificateId}")
						.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/certificate/view-all/sipplier")
				.hasAuthority(PermissionEnum.CERTIFICATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/certificate/view-all/branch")
				.hasAuthority(PermissionEnum.CERTIFICATE.toString()));

		// ACCESOS PARA LA GESTION DE REMISION DE SALIDA
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-output/create")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/remission-output/view-detail/{remissionOutputId}")
						.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
								PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/remission-output/cancel/{remissionOutputId}").hasAnyAuthority(
						PermissionEnum.REMISSION_OUTPUT.toString(), PermissionEnum.REMISSION_OUTPUT_CANCEL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/remission-output/search")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/remission-output/update")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString()));

		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/remission-output/generate/document/{remissionOutputId}")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-output/view-all/client")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-output/view-all/branch")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/remission-output/view-all/product/{branchId}")
						.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
								PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/remission-output/view-all")
				.hasAnyAuthority(PermissionEnum.REMISSION_OUTPUT.toString(),
						PermissionEnum.REMISSION_OUTPUT_OPERATIONAL.toString()));

		// ACCESOS PARA LA GESTION DE REPORTES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report/remission-entry")
				.hasAuthority(PermissionEnum.REMISSION_ENTRY_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report/remission-output")
				.hasAuthority(PermissionEnum.REMISSION_OUTPUT_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report/inventory")
				.hasAuthority(PermissionEnum.INVENTORY_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report/accounting-balance")
				.hasAuthority(PermissionEnum.GLOBAL_BALANCE_REPORT.toString()));

		// ACCESOS PARA LA GESTION DE COTIZACIONES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/quote/create")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/quote/view-detail/{quoteId}")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/quote/update")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/quote/convert/{quoteId}")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/quote/search")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/quote/view-all/client")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/quote/view-all/branch")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/quote/view-all/product")
				.hasAuthority(PermissionEnum.QUOTE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/quote/document/{quoteId}")
				.hasAuthority(PermissionEnum.QUOTE.toString()));

		// ACCESOS PARA LA GESTION DE DEVOLUCION DE REMISIONES DE ENTRADA
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-entry/search-re/{folio}")
						.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-entry/create")
				.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-entry/view-detail/{returnREId}")
						.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-entry/document/{returnREId}")
						.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-entry/view-all")
				.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-entry/search")
				.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-entry/view-all/sipplier")
						.hasAuthority(PermissionEnum.RETURN_RE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-entry/view-all/branch")
						.hasAuthority(PermissionEnum.RETURN_RE.toString()));

		// ACCESOS PARA LA GESTION DE PRODUCTOS EN CUARENTENA
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-all")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cuarantine/search")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-detail/{productId}")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cuarantine/change/location")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-all/actions")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-all/branch")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-all/product")
				.hasAuthority(PermissionEnum.QUARANTINE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/cuarantine/view-movement/{quarantineId}")
						.hasAuthority(PermissionEnum.QUARANTINE.toString()));

		// ACCESOS PARA LA GESTION DE DEVOLUCION DE REMISIONES DE ENTRADA
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-output/search-ro/{folio}")
						.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-output/create")
				.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-output/view-detail/{returnROId}")
						.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-output/document/{returnROId}")
						.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-output/view-all")
				.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/return-remission-output/search")
				.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-output/view-all/client")
						.hasAuthority(PermissionEnum.RETURN_RO.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/return-remission-output/view-all/branch")
						.hasAuthority(PermissionEnum.RETURN_RO.toString()));

		// ACCESOS PARA LA GESTION CAJA EN EL SISTEMA
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cash/find-active")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/new-opening")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/new-entry/movement")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/new-exit/movement")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/new-paymenmt/movement")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cash/summary-closing")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/new-closing")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/search-ro/{folio}")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cash/view-all/branch")
				.hasAnyAuthority(PermissionEnum.CASH_OPERATIONAL.toString(), PermissionEnum.CASH_MANAGMENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cash/view-all/accounting-type")
				.hasAuthority(PermissionEnum.CASH_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/cash/search")
				.hasAuthority(PermissionEnum.CASH_MANAGMENT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/cash/view-detail/{openingCashId}")
				.hasAuthority(PermissionEnum.CASH_MANAGMENT.toString()));

		// ACCESOS PARA LA GESTION DE PAGO A PROVEEDORES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/account-payable/view-month")
				.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/account-payable/search")
				.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/account-payable/new-record")
				.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-payable/view-detail/{remissionEntryId}")
						.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/account-payable/view-all/supplier")
				.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-payable/view-all/payment-state")
						.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-payable/view-all/accounting-type")
						.hasAuthority(PermissionEnum.ACCOUNT_PAYABLE.toString()));

		// ACCESOS PARA LA GESTION DE COBRO A CLIENTES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/account-receivable/view-month")
				.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/account-receivable/search")
				.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/account-receivable/new-record")
				.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-receivable/view-detail/{remissionEntryId}")
						.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/account-receivable/view-all/client")
				.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-receivable/view-all/payment-state")
						.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/account-receivable/view-all/accounting-type")
						.hasAuthority(PermissionEnum.ACCOUNT_RECEIVABLE.toString()));

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
