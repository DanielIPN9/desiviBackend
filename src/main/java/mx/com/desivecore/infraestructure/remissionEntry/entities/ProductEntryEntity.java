package mx.com.desivecore.infraestructure.remissionEntry.entities;

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
@Table(name = "products_entries")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductEntryEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "remission_entry_id", unique = false, nullable = false)
	private Long remissionEntryId;

	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;

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
