package mx.com.desivecore.domain.payments.accountReceivable.models;

import java.util.Date;

import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.payments.models.PaymentState;

public class RemissionOutputBalanceSearch {

	private String folio;

	private ClientSummary client;

	private PaymentState paymentState;

	private Date dateFrom;

	private Date dateTo;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public ClientSummary getClient() {
		return client;
	}

	public void setClient(ClientSummary client) {
		this.client = client;
	}

	public PaymentState getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(PaymentState paymentState) {
		this.paymentState = paymentState;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "RemissionOutputBalanceSearch [folio=" + folio + ", client=" + client + ", paymentState=" + paymentState
				+ ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}

}
