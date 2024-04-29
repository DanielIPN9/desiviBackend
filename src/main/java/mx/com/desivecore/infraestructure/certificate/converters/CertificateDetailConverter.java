package mx.com.desivecore.infraestructure.certificate.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.certificate.models.CertificateDetail;
import mx.com.desivecore.infraestructure.certificate.entities.CertificateDetailEntity;

@Component
public class CertificateDetailConverter {

	public CertificateDetailEntity certificateDetailToEntity(CertificateDetail certificateDetail, Long certificateId) {

		CertificateDetailEntity certificateDetailEntity = new CertificateDetailEntity();

		certificateDetailEntity.setCertificateId(certificateId);
		certificateDetailEntity.setCharacteristic(certificateDetail.getCharacteristic());
		certificateDetailEntity.setValue(certificateDetail.getValue());
		certificateDetailEntity.setResult(certificateDetail.getValue());
		certificateDetailEntity.setResult(certificateDetail.getResult());
		certificateDetailEntity.setNormative(certificateDetail.getNormative());
		certificateDetailEntity.setUnitMeasure(certificateDetail.getUnitMeasure());

		return certificateDetailEntity;
	}

	public CertificateDetail entityToCertificateDetail(CertificateDetailEntity certificateDetailEntity) {

		CertificateDetail certificateDetail = new CertificateDetail();

		certificateDetail.setCertificateId(certificateDetailEntity.getCertificateId());
		certificateDetail.setCharacteristic(certificateDetailEntity.getCharacteristic());
		certificateDetail.setValue(certificateDetailEntity.getValue());
		certificateDetail.setResult(certificateDetailEntity.getResult());
		certificateDetail.setNormative(certificateDetailEntity.getNormative());
		certificateDetail.setUnitMeasure(certificateDetailEntity.getUnitMeasure());

		return certificateDetail;
	}

	public List<CertificateDetailEntity> certificateDetailListToEntityList(
			List<CertificateDetail> certificateDetailList, Long certificateId) {
		List<CertificateDetailEntity> certificateDetailEntityList = new ArrayList<>();
		for (CertificateDetail certificateDetail : certificateDetailList) {
			certificateDetailEntityList.add(certificateDetailToEntity(certificateDetail, certificateId));
		}
		return certificateDetailEntityList;
	}

	public List<CertificateDetail> entityListToCertificateDetailList(
			List<CertificateDetailEntity> certificateDetailEntityList) {
		List<CertificateDetail> certificateDetailList = new ArrayList<>();
		for (CertificateDetailEntity certificateDetailEntity : certificateDetailEntityList) {
			certificateDetailList.add(entityToCertificateDetail(certificateDetailEntity));
		}
		return certificateDetailList;
	}
}
