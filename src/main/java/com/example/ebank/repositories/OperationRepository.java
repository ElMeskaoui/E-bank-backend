package com.example.ebank.repositories;

import com.example.ebank.dtos.AccountHistoryDto;
import com.example.ebank.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(String accountId);
    Page<Operation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
