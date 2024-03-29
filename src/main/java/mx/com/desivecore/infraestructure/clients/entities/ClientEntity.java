package mx.com.desivecore.infraestructure.clients.entities;

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
@Table(name = "clients")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ClientEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clientId;

	@Column(name = "business_name", unique = false, nullable = false, length = 150)
	private String businessName;

	@Column(name = "rfc", unique = false, nullable = false, length = 15)
	private String rfc;

	@Column(name = "contact_name", unique = false, nullable = false, length = 150)
	private String contactName;

	@Column(name = "contact_number", unique = false, nullable = false, length = 20)
	private String contactNumber;

	@Column(name = "email", unique = false, nullable = false, length = 65)
	private String email;

	@Column(name = "street", unique = false, nullable = false, length = 30)
	private String street;

	@Column(name = "external_number", unique = false, nullable = false, length = 10)
	private String externalNumber;

	@Column(name = "internal_number", unique = false, nullable = false, length = 10)
	private String internalNumber;

	@Column(name = "municipality", unique = false, nullable = false, length = 20)
	private String municipality;

	@Column(name = "colony", unique = false, nullable = false, length = 20)
	private String colony;

	@Column(name = "cp", unique = false, nullable = false, length = 5)
	private String cp;

}
