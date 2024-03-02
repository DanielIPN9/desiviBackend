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
@Table(name = "chemical_specifications")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ChemicalSpecificationEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "security_data_sheet_id", unique = false, nullable = false)
	private Long securityDataSheetId;

	@Column(name = "name", unique = false, nullable = true, length = 30)
	private String name;

	@Column(name = "value", unique = false, nullable = true, length = 30)
	private String value;
}
