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
@Table(name = "products_availabilities")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProductAvailabilityEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;

}
