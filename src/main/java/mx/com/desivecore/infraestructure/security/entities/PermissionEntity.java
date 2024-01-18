package mx.com.desivecore.infraestructure.security.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private Long id;

	@Column(name = "name", unique = true, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PermissionEnum name;

	@ManyToMany(mappedBy = "permissions")
	private Collection<RolEntity> roles;

	public PermissionEntity(Long id, PermissionEnum name) {
		super();
		this.id = id;
		this.name = name;
	}

}
