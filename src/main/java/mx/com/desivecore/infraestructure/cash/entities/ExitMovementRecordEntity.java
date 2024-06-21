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
@Table(name = "exit_movements")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ExitMovementRecordEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exitMovementId;
	
	@Column(name = "opening_cash_id", unique = false, nullable = false)
	private Long openingCashId;

	@Column(name = "accounting_type", unique = false, nullable = false)
	private String accountingType;

	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Column(name = "authority_user", unique = false, nullable = false)
	private String authorityUser;

	@Column(name = "currency", unique = false, nullable = false)
	private String currency;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;

}
