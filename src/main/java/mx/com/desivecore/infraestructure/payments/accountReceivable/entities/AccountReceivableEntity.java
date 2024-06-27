package mx.com.desivecore.infraestructure.payments.accountReceivable.entities;

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
@Table(name = "account_receivable")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class AccountReceivableEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@Column(name = "remission_output_id", unique = false, nullable = false)
	private Long remissionOutputId;

	@Column(name = "account_type", unique = false, nullable = false, length = 20)
	private String accountType;

	@Column(name = "payment_amount", unique = false, nullable = false)
	private Double paymentAmount;

	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;
}
