package mx.com.desivecore.infraestructure.users.entities;

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
import mx.com.desivecore.infraestructure.security.entities.RolEntity;

@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name = "branch_id", unique = false, nullable = true)
	private Long branchId;
	
	@Column(name = "name", unique = false, nullable = false, length = 30)
	private String name;

	@Column(name = "first_surname", unique = false, nullable = false, length = 30)
	private String firstSurname;

	@Column(name = "second_surname", unique = false, nullable = false, length = 30)
	private String secondSurname;

	@Column(name = "email", unique = false, nullable = false, length = 40)
	private String email;

	@Column(name = "contact_number", unique = false, nullable = false, length = 15)
	private String contactNumber;

	@Column(name = "password", unique = false, nullable = false, length = 150)
	private String password;

	@Column(name = "status")
	private boolean status;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection<RolEntity> roles;

}
