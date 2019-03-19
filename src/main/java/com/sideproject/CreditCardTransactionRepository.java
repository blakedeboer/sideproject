package com.sideproject;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface CreditCardTransactionRepository extends CrudRepository<CreditCardTransaction, UUID> {
}
