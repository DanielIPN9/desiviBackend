package mx.com.desivecore.infraestructure.securityDataSheet.entities;

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
@Table(name = "security_data_sheets")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class SecurityDataSheetEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long securityDataSheetId;

	@Column(name = "flammability", unique = false, nullable = false)
	private Integer flammability;

	@Column(name = "reactivity", unique = false, nullable = false)
	private Integer reactivity;

	@Column(name = "health", unique = false, nullable = false)
	private Integer health;

	@Column(name = "special_hazard", unique = false, nullable = false)
	private Integer specialHazard;
	
	@Column(name = "product_id", unique = false, nullable = false)
	private Long productId;
	
	@Column(name = "trade_name", unique = false, nullable = true, length = 30)
	private String tradeName;

	@Column(name = "chemical_family", unique = false, nullable = true, length = 30)
	private String chemicalFamily;

	@Column(name = "chemical_formula", unique = false, nullable = true, length = 30)
	private String chemicalFormula;

	@Column(name = "synonym", unique = false, nullable = true, length = 50)
	private String synonym;

	@Column(name = "molecular_weight", unique = false, nullable = true, length = 50)
	private String molecularWeight;
	
	@Column(name = "description", unique = false, nullable = true, length = 1000)
	private String description;

	@Column(name = "application", unique = false, nullable = true, length = 1000)
	private String application;

	@Column(name = "commercial_use", unique = false, nullable = true, length = 1000)
	private String commercialUse;

}
