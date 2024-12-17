package bank.account.service.web;

import bank.account.service.dto.BankAccountRequestDTO;
import bank.account.service.dto.BankAccountResponseDTO;
import bank.account.service.entities.BankAccount;
import bank.account.service.mappers.AccountMapper;
import bank.account.service.repositories.BankAccountRepository;
import bank.account.service.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountRestController {
    private AccountService accountService;
    private BankAccountRepository bankAccountRepository;
    private AccountMapper accountMapper;

    public AccountRestController(AccountService accountService, BankAccountRepository bankAccountRepository, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.bankAccountRepository = bankAccountRepository;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Account with id %s not found", id)));
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO bankAccountRequestDTO) {
        return accountService.addAccount(bankAccountRequestDTO);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount) {
        BankAccount account =bankAccountRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Account with id %s not found", id)));
        if (bankAccount.getBalance() != 0.0) account.setBalance(bankAccount.getBalance());
        if (bankAccount.getCreatedAt()!=null) account.setCreatedAt(bankAccount.getCreatedAt());
        if (bankAccount.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
        if (bankAccount.getType()!=null)account.setType(bankAccount.getType());
        return bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id) {
        bankAccountRepository.deleteById(id);
    }
}
