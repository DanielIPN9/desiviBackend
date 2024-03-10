package mx.com.desivecore.infraestructure.remissionOutput.entities;

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
@Table(name = "remission_outputs")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class RemissionOutputEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long remissionOutputId;

	@Column(name = "folio", unique = false, nullable = false, length = 20)
	private String folio;

	@Column(name = "creation_date", unique = false, nullable = true)
	private Date creationDate;

	@Column(name = "request_date", unique = false, nullable = true)
	private Date requestDay;

	@Column(name = "branch_id", unique = false, nullable = false)
	private Long branchId;

	@Column(name = "client_id", unique = false, nullable = false)
	private Long clientId;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;
	
	@Column(name = "iva_total", unique = false, nullable = false)
	private Double ivaTotal;

	@Column(name = "remission_total", unique = false, nullable = false)
	private Double remissionTotal;

}
