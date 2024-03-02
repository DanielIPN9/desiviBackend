package mx.com.desivecore.infraestructure.securityDataSheet;

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
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.domain.securityDataSheet.models.document.SecurityDataSheetDocument;
import mx.com.desivecore.domain.securityDataSheet.ports.SecurityDataSheetPersistencePort;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.securityDataSheet.converters.ChemicalIdentificationlConverter;
import mx.com.desivecore.infraestructure.securityDataSheet.converters.ChemicalSpecificationConverter;
import mx.com.desivecore.infraestructure.securityDataSheet.converters.SecurityDataSheetConverter;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalIdentificationEntity;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalSpecificationEntity;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.SecurityDataSheetEntity;
import mx.com.desivecore.infraestructure.securityDataSheet.repositories.ChemicalIdentificationRepository;
import mx.com.desivecore.infraestructure.securityDataSheet.repositories.ChemicalSpecificationlRepository;
import mx.com.desivecore.infraestructure.securityDataSheet.repositories.SecurityDataSheetRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class SecurityDataSheetPersistenceImpl implements SecurityDataSheetPersistencePort {
	
	private static final String SDS_TEMPLATE = "/securityDataSheet/SecDataSheetDocument.jasper";
	private static final String FIGURE_REPORT ="/securityDataSheet/rombo_seg.png";
	private static final String LOGO_REPORT = "/securityDataSheet/logo.jpg";

	@Autowired
	private SecurityDataSheetRepository securityDataSheetRepository;

	@Autowired
	private SecurityDataSheetConverter securityDataSheetConverter;

	@Autowired
	private ChemicalSpecificationlRepository chemicalSpecificationlRepository;

	@Autowired
	private ChemicalSpecificationConverter chemicalSpecificationConverter;

	@Autowired
	private ChemicalIdentificationRepository chemicalIdentificationRepository;

	@Autowired
	private ChemicalIdentificationlConverter chemicalIdentificationlConverter;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	@Override
	public SecurityDataSheet saveSecurityDataSheet(SecurityDataSheet securityDataSheet) {
		try {
			SecurityDataSheetEntity securityDataSheetEntity = securityDataSheetConverter
					.securityDataSheetToEntity(securityDataSheet);
			securityDataSheetEntity = securityDataSheetRepository.save(securityDataSheetEntity);
			deleteSavedToUpdate(securityDataSheet);
			List<ChemicalIdentificationEntity> chemicalIdentificationEntityList = chemicalIdentificationlConverter
					.chemicalIdentificationListToEntityList(securityDataSheet.getChIdentifications(),
							securityDataSheetEntity.getSecurityDataSheetId());
			for (ChemicalIdentificationEntity chemicalIdentificationEntity : chemicalIdentificationEntityList) {
				chemicalIdentificationRepository.save(chemicalIdentificationEntity);
			}
			List<ChemicalSpecificationEntity> chemicalSpecificationEntityList = chemicalSpecificationConverter
					.chemicalSpecificationListToEntityList(securityDataSheet.getChSpecifications(),
							securityDataSheetEntity.getSecurityDataSheetId());
			for (ChemicalSpecificationEntity chemicalSpecificationEntity : chemicalSpecificationEntityList) {
				chemicalSpecificationlRepository.save(chemicalSpecificationEntity);
			}
			return securityDataSheetConverter.entityToSecurityDataSheet(securityDataSheetEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	private void deleteSavedToUpdate(SecurityDataSheet securityDataSheet) {
		if (securityDataSheet.getSecurityDataSheetId() != null) {
			List<ChemicalIdentificationEntity> chemicalIdentificationSaved = chemicalIdentificationRepository
					.findAllBySecDataSheetId(securityDataSheet.getSecurityDataSheetId());
			for (ChemicalIdentificationEntity chemicalIdentificationEntity : chemicalIdentificationSaved) {
				chemicalIdentificationRepository.deleteById(chemicalIdentificationEntity.getId());
			}

			List<ChemicalSpecificationEntity> chemicalSpecificationSaved = chemicalSpecificationlRepository
					.findAllBySecDataSheetId(securityDataSheet.getSecurityDataSheetId());
			for (ChemicalSpecificationEntity chemicalSpecificationEntity : chemicalSpecificationSaved) {
				chemicalSpecificationlRepository.deleteById(chemicalSpecificationEntity.getId());
			}

		}
	}

	@Override
	public SecurityDataSheet viewSecurityDataSheetById(Long secDataSheetId) {
		try {
			Optional<SecurityDataSheetEntity> secDataSheetOptional = securityDataSheetRepository
					.findById(secDataSheetId);
			SecurityDataSheet securityDataSheet = null;
			if (secDataSheetOptional.isPresent()) {
				securityDataSheet = securityDataSheetConverter.entityToSecurityDataSheet(secDataSheetOptional.get());
				Optional<ProductEntity> productOptional = productRepository
						.findById(secDataSheetOptional.get().getProductId());
				securityDataSheet.setProduct(productConverter.productEntityToProduct(productOptional.get()));

				List<ChemicalIdentificationEntity> chemicalIdentificationEntityList = chemicalIdentificationRepository
						.findAllBySecDataSheetId(secDataSheetId);
				securityDataSheet.setChIdentifications(chemicalIdentificationlConverter
						.entityListToChemicalIdentificationList(chemicalIdentificationEntityList));

				List<ChemicalSpecificationEntity> chemicalSpecificationEntityList = chemicalSpecificationlRepository
						.findAllBySecDataSheetId(secDataSheetId);
				securityDataSheet.setChSpecifications(chemicalSpecificationConverter
						.entityListToChemicalSpecificationList(chemicalSpecificationEntityList));
			}
			return securityDataSheet;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public SecurityDataSheet viewSecurityDataSheetByProductId(Long productId) {
		try {
			Optional<SecurityDataSheetEntity> secDatSheetOptional = securityDataSheetRepository
					.findByProductId(productId);
			if (secDatSheetOptional.isPresent())
				return securityDataSheetConverter.entityToSecurityDataSheet(secDatSheetOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public SecurityDataSheet viewSecurityDataSheetByProductIdAndSecDataSheetIdNot(Long secDataSheetId, Long productId) {
		try {
			Optional<SecurityDataSheetEntity> secDatSheetOptional = securityDataSheetRepository
					.findByProductIdAndSecDataSheetIdNot(productId, secDataSheetId);
			if (secDatSheetOptional.isPresent())
				return securityDataSheetConverter.entityToSecurityDataSheet(secDatSheetOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<SecurityDataSheet> viewAllSecurityDataSheet() {
		try {
			List<SecurityDataSheetEntity> securityDataSheetEntityList = securityDataSheetRepository.findAll();
			List<SecurityDataSheet> securityDataSheetList = new ArrayList<>();
			for (SecurityDataSheetEntity securityDataSheetEntity : securityDataSheetEntityList) {
				Optional<ProductEntity> productOptional = productRepository
						.findById(securityDataSheetEntity.getProductId());
				SecurityDataSheet securityDataSheet = securityDataSheetConverter
						.entityToSecurityDataSheet(securityDataSheetEntity);
				securityDataSheet.setProduct(productConverter.productEntityToProduct(productOptional.get()));
				securityDataSheetList.add(securityDataSheet);
			}
			return securityDataSheetList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateDocumentSecurityDataSheet(SecurityDataSheetDocument securityDataSheetDocument) {
		try {
			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(SDS_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			securityDataSheetDocument.setLogo(logoImage);
			
			InputStream figureImage = this.getClass().getResourceAsStream(FIGURE_REPORT);
			securityDataSheetDocument.setFigure(figureImage);
			
			Collection<SecurityDataSheetDocument> collection = Collections.singletonList(securityDataSheetDocument);

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
