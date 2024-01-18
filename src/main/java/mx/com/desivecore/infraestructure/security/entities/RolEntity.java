package mx.com.desivecore.infraestructure.security.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;

@Entity
@Table(name = "roles")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class RolEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(name = "description", nullable = false, length = 50)
	private String description;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<UserEntity> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "roles_permissions",
			joinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "permission_id", referencedColumnName = "id"))
	private Collection<PermissionEntity> permissions;
	
	public RolEntity(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
