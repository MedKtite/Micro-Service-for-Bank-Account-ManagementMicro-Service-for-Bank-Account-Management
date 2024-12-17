package bank.account.service.services;

import bank.account.service.dto.BankAccountResponseDTO;
import bank.account.service.dto.BankAccountRequestDTO;

public interface AccountService  {
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);
}
