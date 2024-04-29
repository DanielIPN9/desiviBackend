package mx.com.desivecore.infraestructure.certificate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.certificate.models.CertificateDetail;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.certificate.models.RemissionSummary;
import mx.com.desivecore.domain.certificate.models.document.CertificateDocument;
import mx.com.desivecore.domain.certificate.ports.CertificatePersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.infraestructure.certificate.converters.CertificateDetailConverter;
import mx.com.desivecore.infraestructure.certificate.converters.ProductCertificateConverter;
import mx.com.desivecore.infraestructure.certificate.entities.CertificateDetailEntity;
import mx.com.desivecore.infraestructure.certificate.entities.ProductCertificateEntity;
import mx.com.desivecore.infraestructure.certificate.repositories.CertificateDetailRepository;
import mx.com.desivecore.infraestructure.certificate.repositories.ProductCertificateRepository;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.remissionEntry.entities.ProductEntryEntity;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.ProductEntryRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class CertificatePersistenceImpl implements CertificatePersistencePort {

	private static final String RE_TEMPLATE = "/certificate/CertificateDocument.jasper";
	private static final String LOGO_REPORT = "/certificate/logo.jpg";
	private static final String SING_REPORT = "/certificate/sing_ov.jpg";

	@Autowired
	private ProductCertificateRepository productCertificateRepository;

	@Autowired
	private ProductCertificateConverter productCertificateConverter;

	@Autowired
	private CertificateDetailRepository certificateDetailRepository;

	@Autowired
	private CertificateDetailConverter certificateDetailConverter;

	@Autowired
	private ProductEntryRepository productEntryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	@Override
	public List<RemissionSummary> findCertificateByRemissionEntryList(
			List<RemissionEntrySummary> remissionEntrySummaryList) {
		try {
			List<RemissionSummary> remissionSummaryList = new ArrayList<>();
			log.info("INIT findCertificateByRemissionEntryList()");
			for (RemissionEntrySummary remissionEntrySummary : remissionEntrySummaryList) {
				List<ProductEntryEntity> productEntryEntityList = productEntryRepository
						.findAllByRemissionId(remissionEntrySummary.getRemissionEntryId());
				for (ProductEntryEntity productEntryEntity : productEntryEntityList) {

					Optional<ProductCertificateEntity> productCertificateOptional = productCertificateRepository
							.findByRemissionIdAndProductId(remissionEntrySummary.getRemissionEntryId(),
									productEntryEntity.getProductId());
					Long certificateId = productCertificateOptional.isPresent()
							? productCertificateOptional.get().getCertificateId()
							: 0L;
					Optional<ProductEntity> productOptional = productRepository
							.findById(productEntryEntity.getProductId());
					Product product = productConverter.productEntityToProduct(productOptional.get());

					RemissionSummary remissionSummary = new RemissionSummary(remissionEntrySummary, product,
							certificateId);
					remissionSummaryList.add(remissionSummary);
				}
			}
			return remissionSummaryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductCertificate findCertificateIdByRemissionEntryIdAndProductId(Long remissionEntryId, Long productId) {
		try {
			log.info("INIT findCertificateIdByRemissionEntryIdAndProductId()");
			Optional<ProductCertificateEntity> productCertificateOptional = productCertificateRepository
					.findByRemissionIdAndProductId(remissionEntryId, productId);
			if (!productCertificateOptional.isPresent())
				return null;
			ProductCertificate productCertificate = productCertificateConverter
					.entityToProductCertificate(productCertificateOptional.get());
			List<CertificateDetail> certificateDetailList = certificateDetailConverter
					.entityListToCertificateDetailList(
							certificateDetailRepository.findAllByCertificateId(productCertificate.getCertificateId()));
			productCertificate.setCertificateDetail(certificateDetailList);
			return productCertificate;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductCertificate findCertificateIdByRemissionEntryIdAndProductIdAndCertificateIdNot(Long remissionEntryId,
			Long productId, Long certificateId) {
		try {
			log.info("INIT findCertificateIdByRemissionEntryIdAndProductIdAndCertificateIdNot()");
			Optional<ProductCertificateEntity> productCertificateOptional = productCertificateRepository
					.findByRemissionIdAndProductIdAndCertificateIdNot(remissionEntryId, productId, certificateId);
			if (!productCertificateOptional.isPresent())
				return null;
			return productCertificateConverter.entityToProductCertificate(productCertificateOptional.get());
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductCertificate findCertificateIdById(Long certificateId) {
		try {
			log.info("INIT findCertificateIdById()");
			Optional<ProductCertificateEntity> productCertificateOptional = productCertificateRepository
					.findById(certificateId);
			if (!productCertificateOptional.isPresent())
				return null;
			ProductCertificate productCertificate = productCertificateConverter
					.entityToProductCertificate(productCertificateOptional.get());
			List<CertificateDetail> certificateDetailList = certificateDetailConverter
					.entityListToCertificateDetailList(
							certificateDetailRepository.findAllByCertificateId(productCertificate.getCertificateId()));
			productCertificate.setCertificateDetail(certificateDetailList);
			return productCertificate;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductCertificate saveProductCertificate(ProductCertificate productCertificate) {
		try {
			log.info("INIT saveProductCertificate()");

			ProductCertificateEntity productCertificateEntity = productCertificateConverter
					.productCertificateToEntity(productCertificate);
			productCertificateEntity = productCertificateRepository.save(productCertificateEntity);

			deleteSavedDataToUpdate(productCertificate);
			log.info("SAVED: " + productCertificateEntity.toString());
			List<CertificateDetailEntity> certificateDetailEntityList = certificateDetailConverter
					.certificateDetailListToEntityList(productCertificate.getCertificateDetail(),
							productCertificateEntity.getCertificateId());
			for (CertificateDetailEntity certificateDetailEntity : certificateDetailEntityList) {
				certificateDetailRepository.save(certificateDetailEntity);
			}

			return productCertificateConverter.entityToProductCertificate(productCertificateEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	private void deleteSavedDataToUpdate(ProductCertificate productCertificate) {
		if (productCertificate.getCertificateDetail() != null) {
			List<CertificateDetailEntity> certificateDetailSavedList = certificateDetailRepository
					.findAllByCertificateId(productCertificate.getCertificateId());
			for (CertificateDetailEntity certificateDetailEntity : certificateDetailSavedList) {
				certificateDetailRepository.deleteById(certificateDetailEntity.getId());
			}
		}
	}

	@Override
	public ResponseModel generateDocumentCertificate(CertificateDocument certificateDocument) {
		try {
			log.info("INIT generateDocumentCertificate()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(RE_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			certificateDocument.setLogo(logoImage);

			InputStream logoSing = this.getClass().getResourceAsStream(SING_REPORT);
			certificateDocument.setSing(logoSing);

			Collection<CertificateDocument> collection = Collections.singletonList(certificateDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);

		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
