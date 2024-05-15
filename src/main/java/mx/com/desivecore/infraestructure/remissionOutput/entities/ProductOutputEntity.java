package mx.com.desivecore.infraestructure.remissionOutput.entities;

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
@Table(name = "products_outputs")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductOutputEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "remission_output_id", unique = false, nullable = false)
	private Long remissionOutputId;
	
	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;
	
	@Column(name = "product_description", unique = false, nullable = false)
	private String productDescription;

	@Column(name = "unit_measure", unique = false, nullable = false)
	private String unitMeasure;

	@Column(name = "selling_unit_price", unique = false, nullable = false)
	private Double sellingPrice;

	@Column(name = "iva", unique = false, nullable = false)
	private Double iva;

	@Column(name = "net", unique = false, nullable = false)
	private Double net;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;

}
