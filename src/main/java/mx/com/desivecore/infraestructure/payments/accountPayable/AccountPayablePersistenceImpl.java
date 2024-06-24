package mx.com.desivecore.infraestructure.payments.accountPayable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalance;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryGlobalBalance;
import mx.com.desivecore.domain.payments.accountPayable.ports.AccountPayablePersistencePort;
import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.infraestructure.cash.converters.CashConverter;
import mx.com.desivecore.infraestructure.cash.entities.AccountingTypeEntity;
import mx.com.desivecore.infraestructure.cash.repositories.AccountingTypeRepository;
import mx.com.desivecore.infraestructure.payments.accountPayable.converters.AccountPayableConverter;
import mx.com.desivecore.infraestructure.payments.accountPayable.entities.AccountPayableEntity;
import mx.com.desivecore.infraestructure.payments.accountPayable.repositories.AccountingPayableRepository;
import mx.com.desivecore.infraestructure.payments.accountPayable.repositories.CustomDSLAccountingPayableRepository;
import mx.com.desivecore.infraestructure.payments.converters.PaymentStateConverter;
import mx.com.desivecore.infraestructure.payments.entities.PaymentStateEntity;
import mx.com.desivecore.infraestructure.payments.repositories.PaymentStateRepository;
import mx.com.desivecore.infraestructure.remissionEntry.converters.RemissionEntryConverter;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.RemissionEntryRepository;
import mx.com.desivecore.infraestructure.suppliers.converters.SupplierConverter;
import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;
import mx.com.desivecore.infraestructure.suppliers.repositories.SupplierRepository;

@Log
@Service
public class AccountPayablePersistenceImpl implements AccountPayablePersistencePort {

	@Autowired
	private PaymentStateRepository paymentStateRepository;

	@Autowired
	private PaymentStateConverter paymentStateConverter;

	@Autowired
	private RemissionEntryRepository remissionEntryRepository;

	@Autowired
	private RemissionEntryConverter remissionEntryConverter;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierConverter supplierConverter;

	@Autowired
	private CustomDSLAccountingPayableRepository customDSLAccountingPayableRepository;

	@Autowired
	private AccountPayableConverter accountPayableConverter;

	@Autowired
	private AccountingPayableRepository accountingPayableRepository;
	
	@Autowired
	private CashConverter cashConverter;
	
	@Autowired
	private AccountingTypeRepository accountingTypeRepository;

	@Override
	public List<RemissionEntryBalance> findAllByParams(RemissionEntryBalanceSearch remissionEntryBalanceSearch) {
		try {
			log.info("findAllByParams()");
			return customDSLAccountingPayableRepository.searchREBalanceByParams(remissionEntryBalanceSearch);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean createRecord(AccountPayable accountPayable) {
		try {
			log.info("createRecord()");
			AccountPayableEntity accountPayableEntity = accountPayableConverter.accountPayableToEntity(accountPayable);
			accountingPayableRepository.save(accountPayableEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public RemissionEntryGlobalBalance viewRemissionEntryBalanceDetail(Long remissionEntryId) {
		try {
			log.info("viewRemissionEntryBalanceDetail()");
			return remissionEntryRepository.findById(remissionEntryId).map(remissionEntryEntity -> {
				RemissionEntry remissionEntry = remissionEntryConverter.entityToRemissionEntry(remissionEntryEntity);
				SupplierEntity supplierEntity = supplierRepository.findById(remissionEntryEntity.getSupplierId())
						.orElse(null);
				Supplier supplier = supplierConverter.supplierEntityToSupplier(supplierEntity);
				List<AccountingTypeEntity> accountingTypeEntities = accountingTypeRepository.findAll();
				List<AccountingType> accountingTypeList = accountingTypeEntities.stream()
						.map(accountingTypeEntity -> cashConverter.entityToAccountingType(accountingTypeEntity))
						.collect(Collectors.toList());
				List<AccountPayable> paymentDetail = Optional
						.ofNullable(accountingPayableRepository.findAllByRemissionEntryId(remissionEntryId).stream()
								.map(accountPayableEntity -> accountPayableConverter
										.entityToAccountPayable(accountPayableEntity, accountingTypeList))
								.collect(Collectors.toList()))
						.orElse(new ArrayList<>());
				
				return new RemissionEntryGlobalBalance(remissionEntry, supplier, paymentDetail);
			}).orElse(null);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<PaymentState> viewAllPaymentState() {
		try {
			log.info("viewAllPaymentState()");
			List<PaymentStateEntity> paymentStateEntityList = paymentStateRepository.findAll();
			List<PaymentState> paymentStateList = paymentStateEntityList.stream()
					.map(paymentSateEntity -> paymentStateConverter.entityToPaymentState(paymentSateEntity))
					.collect(Collectors.toList());
			return paymentStateList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public RemissionEntryBalance findRemissionEntryBalanceById(Long remissionEntryId) {
		try {
			log.info("findRemissionEntryBalanceById()");
			return remissionEntryRepository.findById(remissionEntryId).map(remissionEntryEntity -> {
				RemissionEntry remissionEntry = remissionEntryConverter.entityToRemissionEntry(remissionEntryEntity);
				SupplierEntity supplierEntity = supplierRepository.findById(remissionEntryEntity.getSupplierId())
						.orElse(null);
				Supplier supplier = supplierConverter.supplierEntityToSupplier(supplierEntity);
				return new RemissionEntryBalance(remissionEntry, supplier);
			}).orElse(null);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
