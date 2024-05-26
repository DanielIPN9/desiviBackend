package mx.com.desivecore.infraestructure.returnRemissionEntry.entities;

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
@Table(name = "return_products_entries")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReturnProductEntryEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "return_re_id", unique = false, nullable = false)
	private Long returnREId;

	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount_return", unique = false, nullable = false)
	private Double amountReturn;

	@Column(name = "unit_measure", unique = false, nullable = false)
	private String unitMeasure;

	@Column(name = "purchase_unit_price", unique = false, nullable = false)
	private Double purchaseUnitPrice;

	@Column(name = "iva", unique = false, nullable = false)
	private Double iva;

	@Column(name = "net", unique = false, nullable = false)
	private Double net;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;
}
