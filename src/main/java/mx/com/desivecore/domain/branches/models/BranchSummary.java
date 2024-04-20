package mx.com.desivecore.domain.branches.models;

public class BranchSummary {

	private Long branchId;

	private String name;

	public BranchSummary(Long branchId, String name) {
		super();
		this.branchId = branchId;
		this.name = name;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BranchSummary [branchId=" + branchId + ", name=" + name + "]";
	}

}
