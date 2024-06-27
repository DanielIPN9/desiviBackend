package mx.com.desivecore.domain.payments.accountReceivable.models;

import java.util.Date;

import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;

public class RemissionOutputBalance {

	private Long remissionOutputId;

	private String folio;

	private String clientName;

	private Double amountTotal;

	private Double balanceDue;

	private String paymentState;

	private Date creationDate;

	public RemissionOutputBalance() {
		super();
	}

	public RemissionOutputBalance(RemissionOutput remissionOutput, Client client) {
		super();
		this.remissionOutputId = remissionOutput.getRemissionOutputId();
		this.folio = remissionOutput.getFolio();
		this.clientName = client.getBusinessName();
		this.amountTotal = remissionOutput.getRemissionTotal();
		this.balanceDue = remissionOutput.getBalanceDue();
		this.paymentState = remissionOutput.getPaymentStatus();
		this.creationDate = remissionOutput.getCreationDate();
	}

	public RemissionOutputBalance(Long remissionOutputId, String folio, String clientName, Double amountTotal,
			Double balanceDue, String paymentState, Date creationDate) {
		super();
		this.remissionOutputId = remissionOutputId;
		this.folio = folio;
		this.clientName = clientName;
		this.amountTotal = amountTotal;
		this.balanceDue = balanceDue;
		this.paymentState = paymentState;
		this.creationDate = creationDate;
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

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "RemissionOutputBalance [remissionOutputId=" + remissionOutputId + ", folio=" + folio + ", clientName="
				+ clientName + ", amountTotal=" + amountTotal + ", balanceDue=" + balanceDue + ", paymentState="
				+ paymentState + ", creationDate=" + creationDate + "]";
	}

}
