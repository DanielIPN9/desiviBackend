package mx.com.desivecore.infraestructure.payments.entities;

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
@Table(name = "payments_states")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class PaymentStateEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", unique = false, nullable = false, length = 15)
	private String code;

	@Column(name = "description", unique = false, nullable = false, length = 50)
	private String description;

}
