package mx.com.desivecore.infraestructure.branches.entities;

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
@Table(name = "branches")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class BranchEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;

	@Column(name = "name", unique = false, nullable = false, length = 30)
	private String name;

	@Column(name = "street", unique = false, nullable = true, length = 30)
	private String street;

	@Column(name = "external_number", unique = false, nullable = true, length = 15)
	private String externalNumber;

	@Column(name = "municipality", unique = false, nullable = true, length = 30)
	private String municipality;

	@Column(name = "colony", unique = false, nullable = true, length = 30)
	private String colony;

	@Column(name = "cp", unique = false, nullable = true, length = 10)
	private String cp;

}
