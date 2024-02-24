package mx.com.desivecore.infraestructure.productTag.entities;

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
@Table(name = "products_tags")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductTagEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	
	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;
	
	@Column(name = "lot", unique = false, nullable = false, length = 30)
	private String lot;

	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;

	@Column(name = "net_weight", unique = false, nullable = false)
	private Double netWeight;
	
	@Column(name = "um", unique = false, nullable = false, length = 6)
	private String um;

	@Column(name = "full_address", unique = false, nullable = false, length = 300)
	private String fullAddress;

	@Column(name = "url_site", unique = false, nullable = false, length = 40)
	private String urlSite;

	@Column(name = "phone_number", unique = false, nullable = false, length = 15)
	private String phoneNumber;

}
