package mx.com.desivecore.domain.certificate.models.document;

import mx.com.desivecore.domain.certificate.models.CertificateDetail;

public class CertificateDetailDocument {

	private String characteristic;

	private String unitMeasure;

	private String value;

	private String result;

	private String normative;

	public CertificateDetailDocument(CertificateDetail certificateDetail) {

		this.characteristic = certificateDetail.getCharacteristic();
		this.value = certificateDetail.getValue();
		this.unitMeasure = certificateDetail.getUnitMeasure();
		this.result = certificateDetail.getResult();
		this.normative = certificateDetail.getNormative();
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNormative() {
		return normative;
	}

	public void setNormative(String normative) {
		this.normative = normative;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	@Override
	public String toString() {
		return "CertificateDetailDocument [characteristic=" + characteristic + ", unitMeasure=" + unitMeasure
				+ ", value=" + value + ", result=" + result + ", normative=" + normative + "]";
	}

}
