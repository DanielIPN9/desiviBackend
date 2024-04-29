package mx.com.desivecore.infraestructure.certificate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "certificates_details")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class CertificateDetailEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "certificate_id", unique = false, nullable = false)
	private Long certificateId;

	@Column(name = "characteristic", unique = false, nullable = false, length = 200)
	private String characteristic;
	
	@Column(name = "unit_measure", unique = false, nullable = false, length = 20)
	private String unitMeasure;

	@Column(name = "value", unique = false, nullable = false, length = 100)
	private String value;

	@Column(name = "result", unique = false, nullable = false, length = 100)
	private String result;

	@Column(name = "normative", unique = false, nullable = false, length = 100)
	private String normative;

}
