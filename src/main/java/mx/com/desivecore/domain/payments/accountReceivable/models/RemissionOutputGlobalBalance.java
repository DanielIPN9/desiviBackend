package mx.com.desivecore.domain.payments.accountReceivable.models;

import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;

public class RemissionOutputGlobalBalance {

	private Long remissionOutputId;

	private String folio;

	private String clientName;

	private Double amountTotal;

	private Double balanceDue;

	private String paymentSate;

	private Date creationDate;

	private List<AccountReceivable> paymentList;

	public RemissionOutputGlobalBalance() {
		super();
	}

	public RemissionOutputGlobalBalance(RemissionOutput remissionOutput, Client client,
			List<AccountReceivable> paymentList) {
		super();
		this.remissionOutputId = remissionOutput.getRemissionOutputId();
		this.folio = remissionOutput.getFolio();
		this.clientName = client.getBusinessName();
		this.amountTotal = remissionOutput.getRemissionTotal();
		this.balanceDue = remissionOutput.getBalanceDue();
		this.paymentSate = remissionOutput.getPaymentStatus();
		this.creationDate = remissionOutput.getCreationDate();
		this.paymentList = paymentList;
	}

	public Long getRemissionOutputId() {
		return remissionOutputId;
	}

	public void setRemissionOutputId(Long remissionOutputId) {
		this.remissionOutputId = remissionOutputId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(Double balanceDue) {
		this.balanceDue = balanceDue;
	}

	public String getPaymentSate() {
		return paymentSate;
	}

	public void setPaymentSate(String paymentSate) {
		this.paymentSate = paymentSate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<AccountReceivable> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<AccountReceivable> paymentList) {
		this.paymentList = paymentList;
	}

	@Override
	public String toString() {
		return "RemissionOutputGlobalBalance [remissionOutputId=" + remissionOutputId + ", folio=" + folio
				+ ", clientName=" + clientName + ", amountTotal=" + amountTotal + ", balanceDue=" + balanceDue
				+ ", paymentSate=" + paymentSate + ", creationDate=" + creationDate + ", paymentList=" + paymentList
				+ "]";
	}

}
