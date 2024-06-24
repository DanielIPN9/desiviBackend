package mx.com.desivecore.infraestructure.remissionEntry.entities;

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
@Table(name = "remissions_entries")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class RemissionEntryEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long remissionEntryId;

	@Column(name = "folio", unique = false, nullable = false, length = 20)
	private String folio;

	@Column(name = "creation_date", unique = false, nullable = true)
	private Date creationDate;

	@Column(name = "request_date", unique = false, nullable = true)
	private Date requestDate;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "supplier_id", unique = false, nullable = false)
	private Long supplierId;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

	@Column(name = "observations", unique = false, nullable = true, length = 100)
	private String observations;

	@Column(name = "conditions", unique = false, nullable = true, length = 100)
	private String conditions;

	@Column(name = "iva_total", unique = false, nullable = false)
	private Double ivaTotal;

	@Column(name = "remission_total", unique = false, nullable = false)
	private Double remissionTotal;
	
	@Column(name = "balance_due", unique = false, nullable = false)
	private Double balanceDue;
	
	@Column(name = "status", unique = false, nullable = false)
	private Boolean status;
	
	@Column(name = "payment_status", unique = false, nullable = true, length = 100)
	private String paymentStatus;
}
