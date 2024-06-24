package mx.com.desivecore.domain.suppliers.models;

public class SupplierSummary {

	private Long supplierId;

	private String businessName;

	public SupplierSummary() {
		super();
	}

	public SupplierSummary(Long supplierId, String businessName) {
		super();
		this.supplierId = supplierId;
		this.businessName = businessName;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "SupplierSummary [supplierId=" + supplierId + ", businessName=" + businessName + "]";
	}

}
