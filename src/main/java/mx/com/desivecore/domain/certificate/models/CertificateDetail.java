package mx.com.desivecore.domain.certificate.models;

public class CertificateDetail {

	private Long certificateId;

	private String characteristic;
	
	private String unitMeasure;

	private String value;
	
	private String result;

	private String normative;

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
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
		return "CertificateDetail [certificateId=" + certificateId + ", characteristic=" + characteristic + ", value="
				+ value + ", unitMeasure=" + unitMeasure + ", result=" + result + ", normative=" + normative + "]";
	}

}
