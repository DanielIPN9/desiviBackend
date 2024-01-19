package mx.com.desivecore.domain.security.models;

import java.util.List;

public class Rol {

	private Long roleId;

	private String name;

	private String description;

	private List<Permission> permissions;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Rol [roleId=" + roleId + ", name=" + name + ", description=" + description + ", permissions="
				+ permissions + "]";
	}

}
