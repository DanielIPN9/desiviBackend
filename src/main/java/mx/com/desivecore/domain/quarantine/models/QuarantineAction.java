package mx.com.desivecore.domain.quarantine.models;

public class QuarantineAction {

	private Long id;

	private String actionCode;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "QuarantineAction [id=" + id + ", actionCode=" + actionCode + ", description=" + description + "]";
	}

}
