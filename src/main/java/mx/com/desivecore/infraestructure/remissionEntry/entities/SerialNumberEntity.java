package mx.com.desivecore.infraestructure.remissionEntry.entities;

import java.math.BigInteger;

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
@Table(name = "serial_numbers")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class SerialNumberEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", unique = true, nullable = false, length = 9)
	private String code;

	@Column(name = "number", unique = false, nullable = false)
	private BigInteger number;
}
