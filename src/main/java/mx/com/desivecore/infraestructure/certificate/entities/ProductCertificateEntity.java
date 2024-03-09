package mx.com.desivecore.infraestructure.certificate.entities;

import java.util.Date;

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
@Table(name = "products_certificates")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductCertificateEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long certificateId;

	@Column(name = "remission_entry_id", unique = false, nullable = false)
	private Long remissionEntryId;

	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "product_name", unique = false, nullable = false, length = 100)
	private String productName;

	@Column(name = "sku", unique = false, nullable = false, length = 50)
	private String sku;

	@Column(name = "client_name", unique = false, nullable = false, length = 100)
	private String clientName;

	@Column(name = "lot", unique = false, nullable = false, length = 50)
	private String lot;

	@Column(name = "creation_date", unique = false, nullable = true)
	private Date creationDate;
	
}
