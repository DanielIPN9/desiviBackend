package mx.com.desivecore.infraestructure.security.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.desivecore.commons.constants.PermissionEnum;


@Entity
@Table(name = "permissions")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class PermissionEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", unique = true, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PermissionEnum name;
	
	public PermissionEntity(int id, PermissionEnum name) {
		super();
		this.id = id;
		this.name = name;
	}

}
