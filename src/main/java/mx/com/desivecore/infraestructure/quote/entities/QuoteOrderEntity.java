package mx.com.desivecore.infraestructure.quote.entities;

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
@Table(name = "quote_orders")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class QuoteOrderEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quoteOrderId;

	@Column(name = "folio", unique = false, nullable = false, length = 20)
	private String folio;

	@Column(name = "creation_date", unique = false, nullable = true)
	private Date creationDate;

	@Column(name = "effective_date", unique = false, nullable = true)
	private Date effectiveDate;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "client_id", unique = false, nullable = false)
	private Long clientId;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

	@Column(name = "sub_total", unique = false, nullable = false)
	private Double subTotal;

	@Column(name = "iva_total", unique = false, nullable = false)
	private Double ivaTotal;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;

	@Column(name = "observation", unique = false, nullable = true)
	private String observation;

	@Column(name = "is_converter", unique = false, nullable = false)
	private Boolean isConverter;

	@Column(name = "is_effective", unique = false, nullable = false)
	private Boolean isEffective;

}
