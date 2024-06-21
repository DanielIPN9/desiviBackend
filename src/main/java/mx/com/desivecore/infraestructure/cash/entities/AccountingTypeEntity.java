package mx.com.desivecore.infraestructure.cash.entities;

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
@Table(name = "accounting_types")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class AccountingTypeEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "code", unique = false, nullable = true)
	private String code;

	@Column(name = "description", unique = false, nullable = true)
	private String description;
	
}
