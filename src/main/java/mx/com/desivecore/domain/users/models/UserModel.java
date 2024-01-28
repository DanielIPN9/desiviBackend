package mx.com.desivecore.domain.users.models;

import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.security.models.Rol;

public class UserModel {

	private Long userId;

	private Branch branch;

	private List<Rol> roles;

	private String name;

	private String firstSurname;

	private String secondSurname;

	private String email;

	private String contactNumber;

	private String password;

	private boolean status;

	public UserModel() {
		super();
	}

	public UserModel(Long userId, Branch branch, List<Rol> roles, String name, String firstSurname,
			String secondSurname, String email, String contactNumber, String password, boolean status) {
		super();
		this.userId = userId;
		this.branch = branch;
		this.roles = roles;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.email = email;
		this.contactNumber = contactNumber;
		this.password = password;
		this.status = status;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", branch=" + branch + ", roles=" + roles + ", name=" + name
				+ ", firstSurname=" + firstSurname + ", secondSurname=" + secondSurname + ", email=" + email
				+ ", contactNumber=" + contactNumber + ", password=" + password + ", status=" + status + "]";
	}

}
