package mx.com.desivecore.infraestructure.cash.entities;

import java.util.Date;

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
@Table(name = "opening_cash")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class OpeningCashEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long openingCashId;

	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "user_email", unique = false, nullable = false)
	private String userEmail;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

	@Column(name = "currency", unique = false, nullable = false, length = 10)
	private String currency;

	@Column(name = "amount", unique = false, nullable = false)
	private Double amount;

	@Column(name = "is_active", unique = false, nullable = true)
	private Boolean isActive;

	@Column(name = "closing_cash_id", unique = false, nullable = true)
	private Long closingCashId;

}
