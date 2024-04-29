package mx.com.desivecore.domain.certificate;

import java.util.List;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.certificate.models.CertificateDetail;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.certificate.ports.CertificatePersistencePort;

@Log
public class CertificateValidator {

	public String validOperativeDataToCreate(ProductCertificate productCertificate,
			CertificatePersistencePort certificatePersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		if (productCertificate == null)
			return "Datos invalidos";

		String validations = "";
		validations = validrequiredFields(productCertificate, validations);

		validations += productCertificate.getCertificateDetail() == null ? " -Lista de Características es requerida"
				: "";
		if (productCertificate.getCertificateDetail() != null) {
			validations += productCertificate.getCertificateDetail().size() == 0 ? " -Se require una línea como mínimo"
					: "";
			validations = validOperativeDetail(productCertificate.getCertificateDetail(), validations);
		}

		ProductCertificate productCertificateSearch = certificatePersistencePort
				.findCertificateIdByRemissionEntryIdAndProductId(productCertificate.getRemissionEntryId(),
						productCertificate.getProductId());

		validations += productCertificateSearch != null
				? " -Ya existe un certificado para la remisión y producto ingresados"
				: "";

		return validations;
	}

	public String validOperativeDataToUpdate(ProductCertificate productCertificate,
			CertificatePersistencePort certificatePersistencePort) {
		log.info("INIT validOperativeDataToUpdate()");

		if (productCertificate == null)
			return "Datos invalidos";

		String validations = "";
		validations = validrequiredFields(productCertificate, validations);

		validations += productCertificate.getCertificateDetail() == null ? " -Lista de Características es requerida"
				: "";
		if (productCertificate.getCertificateDetail() != null) {
			validations += productCertificate.getCertificateDetail().size() == 0 ? " -Se require una línea como mínimo"
					: "";
			validations = validOperativeDetail(productCertificate.getCertificateDetail(), validations);
		}

		ProductCertificate productCertificateSearch = certificatePersistencePort
				.findCertificateIdByRemissionEntryIdAndProductIdAndCertificateIdNot(
						productCertificate.getRemissionEntryId(), productCertificate.getProductId(),
						productCertificate.getCertificateId());

		validations += productCertificateSearch != null
				? " -Ya existe un certificado para la remisión y producto ingresados"
				: "";

		return validations;
	}

	private String validOperativeDetail(List<CertificateDetail> certificateDetail, String validations) {
		for (CertificateDetail certificateD : certificateDetail) {
			validations += validString("Características", certificateD.getCharacteristic());
			validations += validString("Valor", certificateD.getValue());
			validations += validString("UM", certificateD.getUnitMeasure());
			validations += validString("Resultado", certificateD.getResult());
			validations += validString("Referencia Normativa", certificateD.getNormative());
		}
		return validations;
	}

	private String validrequiredFields(ProductCertificate productCertificate, String validations) {
		validations += validString("Nombre de Producto", productCertificate.getProductName());
		validations += validString("SKU", productCertificate.getSku());
		validations += productCertificate.getCreationDate() == null ? " -Fecha de ingreso es requerida" : "";
		validations += productCertificate.getRemissionEntryId() == null
				? " -Identificador de Remisión de Entrada invalido"
				: "";
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "-El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "-El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}

}
