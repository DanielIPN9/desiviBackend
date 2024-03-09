package mx.com.desivecore.domain.certificate.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;

public interface CertificateServicePort {

	ResponseModel viewRemissionEntrySummary(RemissionSearchParams remissionSearchParams);

	ResponseModel viewCertificateByRemissionAndProduct(Long remissionId, Long productId);

	ResponseModel createCertificate(ProductCertificate productCertificate);

	ResponseModel updateCertificateById(ProductCertificate productCertificate);

	ResponseModel generateDocumentCertificateById(Long certificateId);
	
	ResponseModel viewSupplierActiveList();
	
	ResponseModel viewBranchActiveList();

}
