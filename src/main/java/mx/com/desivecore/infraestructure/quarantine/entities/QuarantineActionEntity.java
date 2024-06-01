package mx.com.desivecore.infraestructure.quarantine.entities;

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
@Table(name = "quarantine_actions")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class QuarantineActionEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "action_code", unique = false, nullable = false)
	private String actionCode;

	@Column(name = "description", unique = false, nullable = false)
	private String description;
}
