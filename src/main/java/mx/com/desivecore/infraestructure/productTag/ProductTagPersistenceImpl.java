package mx.com.desivecore.infraestructure.productTag;

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
import mx.com.desivecore.domain.productTag.models.ProductTag;
import mx.com.desivecore.domain.productTag.models.ProductTagDocument;
import mx.com.desivecore.domain.productTag.ports.ProductTagPersistencePort;
import mx.com.desivecore.infraestructure.productTag.converters.ProductTagConverter;
import mx.com.desivecore.infraestructure.productTag.entities.ProductTagEntity;
import mx.com.desivecore.infraestructure.productTag.repositories.ProductTagRepository;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class ProductTagPersistenceImpl implements ProductTagPersistencePort {

	private static final String RE_TEMPLATE = "/tag/ProductTagV2.jasper";
	private static final String TAG_1 = "/tag/tag_fondo_v2_1.png";
	private static final String TAG_2 = "/tag/tag_fondo_v2_2.png";
	private static final String TAG_3 = "/tag/tag_fondo_v2_3.png";
	private static final String TAG_4 = "/tag/tag_fondo_v2_4.png";

	@Autowired
	private ProductTagRepository productTagRepository;

	@Autowired
	private ProductTagConverter productTagConverter;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	@Override
	public ProductTag saveProductTag(ProductTag productTag) {
		try {
			log.info("INIT saveProductTag()");
			ProductTagEntity productTagEntity = productTagConverter.productTagToProductTagEntity(productTag);
			productTagEntity = productTagRepository.save(productTagEntity);
			productTag.setTagId(productTagEntity.getTagId());
			return productTag;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductTag viewProductTagById(Long tagId) {
		try {
			log.info("INIT viewProductTagById()");
			Optional<ProductTagEntity> productTagOptional = productTagRepository.findById(tagId);
			ProductTag productTag = null;
			if (productTagOptional.isPresent()) {
				Optional<ProductEntity> product = productRepository.findById(productTagOptional.get().getProductId());
				productTag = productTagConverter.productTagEntityToProductTag(productTagOptional.get());
				productTag.setProduct(productConverter.productEntityToProduct(product.get()));
			}
			return productTag;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductTag> viewAllProductTag() {
		try {
			log.info("INIT viewAllProductTag()");
			List<ProductTagEntity> productTagEntityList = productTagRepository.findAll();
			List<ProductTag> productTagList = new ArrayList<>();
			for (ProductTagEntity productTagEntity : productTagEntityList) {
				ProductTag productTag = productTagConverter.productTagEntityToProductTag(productTagEntity);
				Optional<ProductEntity> product = productRepository.findById(productTagEntity.getProductId());
				productTag.setProduct(productConverter.productEntityToProduct(product.get()));
				productTagList.add(productTag);
			}
			return productTagList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean deleteProductTagById(Long tagId) {
		try {
			log.info("INIT deleteProductTagById()");
			productTagRepository.deleteById(tagId);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public ResponseModel generateDocumentTag(ProductTagDocument productTagDocument) {
		try {
			log.info("INIT generateDocumentTag()");
			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(RE_TEMPLATE);

			log.info(String.format("LOAD TAG BASE "));
			InputStream logoImage = this.getClass().getResourceAsStream(TAG_1);
			productTagDocument.setTag1(logoImage);
			log.info(String.format("LOAD TAG BASE "));
			InputStream logoImage2 = this.getClass().getResourceAsStream(TAG_2);
			productTagDocument.setTag2(logoImage2);
			log.info(String.format("LOAD TAG BASE "));
			InputStream logoImage3 = this.getClass().getResourceAsStream(TAG_3);
			productTagDocument.setTag3(logoImage3);
			log.info(String.format("LOAD TAG BASE "));
			InputStream logoImage4 = this.getClass().getResourceAsStream(TAG_4);
			productTagDocument.setTag4(logoImage4);

			Collection<ProductTagDocument> collection = Collections.singletonList(productTagDocument);

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
