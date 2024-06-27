package mx.com.desivecore.infraestructure.payments.accountReceivable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalance;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputGlobalBalance;
import mx.com.desivecore.domain.payments.accountReceivable.ports.AccountReceivablePersistencePort;
import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.infraestructure.cash.converters.CashConverter;
import mx.com.desivecore.infraestructure.cash.entities.AccountingTypeEntity;
import mx.com.desivecore.infraestructure.cash.repositories.AccountingTypeRepository;
import mx.com.desivecore.infraestructure.clients.converters.ClientConverter;
import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;
import mx.com.desivecore.infraestructure.clients.repositories.ClientRepository;
import mx.com.desivecore.infraestructure.payments.accountReceivable.converters.AccountReceivableConverter;
import mx.com.desivecore.infraestructure.payments.accountReceivable.entities.AccountReceivableEntity;
import mx.com.desivecore.infraestructure.payments.accountReceivable.repositories.AccountingReceivableRepository;
import mx.com.desivecore.infraestructure.payments.accountReceivable.repositories.CustomDSLAccountingReceivableRepository;
import mx.com.desivecore.infraestructure.payments.converters.PaymentStateConverter;
import mx.com.desivecore.infraestructure.payments.entities.PaymentStateEntity;
import mx.com.desivecore.infraestructure.payments.repositories.PaymentStateRepository;
import mx.com.desivecore.infraestructure.remissionOutput.converters.RemissionOutputConverter;
import mx.com.desivecore.infraestructure.remissionOutput.repositories.RemissionOutputRepository;

@Log
@Service
public class AccountReceivablePersistenceImpl implements AccountReceivablePersistencePort {

	@Autowired
	private PaymentStateRepository paymentStateRepository;

	@Autowired
	private PaymentStateConverter paymentStateConverter;

	@Autowired
	private AccountingReceivableRepository accountingReceivableRepository;

	@Autowired
	private AccountReceivableConverter accountReceivableConverter;

	@Autowired
	private RemissionOutputRepository remissionOutputRepository;

	@Autowired
	private RemissionOutputConverter remissionOutputConverter;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientConverter clientConverter;

	@Autowired
	private CashConverter cashConverter;

	@Autowired
	private AccountingTypeRepository accountingTypeRepository;
	
	@Autowired
	private CustomDSLAccountingReceivableRepository customDSLAccountingReceivableRepository;

	@Override
	public List<RemissionOutputBalance> findAllByParams(RemissionOutputBalanceSearch remissionOutputBalanceSearch) {
		try {
			log.info("findAllByParams()");
			return customDSLAccountingReceivableRepository.searchROBalanceByParams(remissionOutputBalanceSearch);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public RemissionOutputBalance findRemissionOutputBalanceById(Long remissionOutputId) {
		try {
			log.info("findRemissionOutputBalanceById()");
			return remissionOutputRepository.findById(remissionOutputId).map(remissionOutputEntity -> {
				RemissionOutput remissionOutput = remissionOutputConverter
						.entityToRemissionOutput(remissionOutputEntity);
				ClientEntity clientEntity = clientRepository.findById(remissionOutputEntity.getClientId()).orElse(null);
				Client client = clientConverter.clientEntityToClient(clientEntity);
				return new RemissionOutputBalance(remissionOutput, client);
			}).orElse(null);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean createRecord(AccountReceivable accountReceivable) {
		try {
			log.info("createRecord()");
			AccountReceivableEntity accountReceivableEntity = accountReceivableConverter
					.accountReceivableToEntity(accountReceivable);
			accountingReceivableRepository.save(accountReceivableEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public RemissionOutputGlobalBalance viewRemissionOutputBalanceDetail(Long remissionOutputId) {
		try {
			log.info("viewRemissionOutputBalanceDetail()");
			return remissionOutputRepository.findById(remissionOutputId).map(remissionOutputEntity -> {
				RemissionOutput remissionOutput = remissionOutputConverter
						.entityToRemissionOutput(remissionOutputEntity);
				ClientEntity clientEntity = clientRepository.findById(remissionOutputEntity.getClientId()).orElse(null);
				Client client = clientConverter.clientEntityToClient(clientEntity);
				List<AccountingTypeEntity> accountingTypeEntities = accountingTypeRepository.findAll();
				List<AccountingType> accountingTypeList = accountingTypeEntities.stream()
						.map(accountingTypeEntity -> cashConverter.entityToAccountingType(accountingTypeEntity))
						.collect(Collectors.toList());
				List<AccountReceivable> paymentDetail = Optional.ofNullable(
						accountingReceivableRepository.findAllByRemissionOutputId(remissionOutputId).stream()
								.map(accountReceivable -> accountReceivableConverter
										.entityToAccountReceivable(accountReceivable, accountingTypeList))
								.collect(Collectors.toList()))
						.orElse(new ArrayList<>());
				return new RemissionOutputGlobalBalance(remissionOutput, client, paymentDetail);
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

}
