package mx.com.desivecore.infraestructure.branches.entities;

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
@Table(name = "branches_phones")
@ToString
@NoArgsConstructor
@Getter
@Setter
public class BranchPhoneEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "branch_id", nullable = false)
	private Long branchId;

	@Column(name = "phone", unique = false, nullable = true, length = 15)
	private String phone;

	@Column(name = "extension", unique = false, nullable = true, length = 10)
	private String extension;

}
