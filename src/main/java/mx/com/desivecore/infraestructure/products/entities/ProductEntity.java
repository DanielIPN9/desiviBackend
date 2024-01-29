package mx.com.desivecore.infraestructure.products.entities;

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
@Table(name = "products")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(name = "sku", unique = true, nullable = false, length = 30)
	private String sku;

	@Column(name = "name", unique = false, nullable = false, length = 30)
	private String name;

	@Column(name = "unit_measure", unique = false, nullable = false, length = 5)
	private String unitMeasure;

	@Column(name = "iva", unique = false, nullable = false)
	private Double iva;

	@Column(name = "unit_selling_price", unique = false, nullable = false)
	private Double unitSellingPrice;

	@Column(name = "unit_purchase_price", unique = false, nullable = false)
	private Double unitPurchasePrice;

}
