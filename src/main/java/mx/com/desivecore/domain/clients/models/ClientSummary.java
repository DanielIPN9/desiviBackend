package mx.com.desivecore.domain.clients.models;

public class ClientSummary {

	private Long clientId;

	private String businessName;

	public ClientSummary(Long clientId, String businessName) {
		super();
		this.clientId = clientId;
		this.businessName = businessName;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getBusinessName() {
		return businessName;
	}

	@Override
	public String toString() {
		return "ClientSummary [clientId=" + clientId + ", businessName=" + businessName + "]";
	}

}
