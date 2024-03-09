package mx.com.desivecore.domain.certificate.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.certificate.models.RemissionSummary;
import mx.com.desivecore.domain.certificate.models.document.CertificateDocument;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;

public interface CertificatePersistencePort {

	List<RemissionSummary> findCertificateByRemissionEntryList(List<RemissionEntrySummary> remissionEntrySummaryList);

	ProductCertificate findCertificateIdByRemissionEntryIdAndProductId(Long remissionEntryId, Long productId);

	ProductCertificate findCertificateIdByRemissionEntryIdAndProductIdAndCertificateIdNot(Long remissionEntryId,
			Long productId, Long certificateId);

	ProductCertificate findCertificateIdById(Long certificateId);

	ProductCertificate saveProductCertificate(ProductCertificate productCertificate);

	ResponseModel generateDocumentCertificate(CertificateDocument certificateDocument);

}
