package mx.com.desivecore.infraestructure.returnRemissionEntry.entities;

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
@Table(name = "return_remissions_entries")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReturnRemissionEntryEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long returnREId;

	@Column(name = "folio", unique = false, nullable = false, length = 20)
	private String folio;

	@Column(name = "creation_date", unique = false, nullable = true)
	private Date creationDate;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "supplier_id", unique = false, nullable = false)
	private Long supplierId;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

	@Column(name = "observations", unique = false, nullable = true, length = 100)
	private String observations;

	@Column(name = "iva_total", unique = false, nullable = false)
	private Double ivaTotal;

	@Column(name = "sub_total", unique = false, nullable = false)
	private Double subTotal;

	@Column(name = "total", unique = false, nullable = false)
	private Double total;
}
