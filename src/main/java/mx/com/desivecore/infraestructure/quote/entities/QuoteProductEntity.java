package mx.com.desivecore.infraestructure.quote.entities;

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
@Table(name = "quote_products")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class QuoteProductEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quote_order_id", unique = false, nullable = false)
	private Long quoteOrderId;

	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;

	@Column(name = "unit_measure", unique = false, nullable = false)
	private String unitMeasure;

	@Column(name = "selling_price", unique = false, nullable = false)
	private Double sellingPrice;

	@Column(name = "product_description", unique = false, nullable = true)
	private String productDescription;

	@Column(name = "iva", unique = false, nullable = false)
	private Double iva;

	@Column(name = "net", unique = false, nullable = false)
	private Double net;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;

}
