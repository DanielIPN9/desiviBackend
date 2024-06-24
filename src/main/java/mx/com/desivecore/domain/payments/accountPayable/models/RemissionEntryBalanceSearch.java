package mx.com.desivecore.domain.payments.accountPayable.models;

import java.util.Date;

import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.domain.suppliers.models.SupplierSummary;

public class RemissionEntryBalanceSearch {

	private String folio;

	private PaymentState paymentState;

	private Date dateFrom;

	private Date dateTo;

	private SupplierSummary supplier;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public SupplierSummary getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSummary supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "RemissionEntryBalanceSearch [folio=" + folio + ", paymentState=" + paymentState + ", dateFrom="
				+ dateFrom + ", dateTo=" + dateTo + ", supplier=" + supplier + "]";
	}

}
