package mx.com.desivecore.infraestructure.returnRemissionOutput.entities;

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
@Table(name = "return_products_outputs")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReturnProductOutputEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "return_ro_id", unique = false, nullable = false)
	private Long returnROId;
	
	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount_return", unique = false, nullable = false)
	private Double amountReturn;

	@Column(name = "unit_measure", unique = false, nullable = false)
	private String unitMeasure;

	@Column(name = "selling_unit_price", unique = false, nullable = false)
	private Double sellingUnitPrice;

	@Column(name = "iva", unique = false, nullable = false)
	private Double iva;

	@Column(name = "net", unique = false, nullable = false)
	private Double net;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;
	
	
}
