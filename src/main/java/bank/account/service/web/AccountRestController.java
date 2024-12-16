package bank.account.service.web;

import bank.account.service.entities.BankAccount;
import bank.account.service.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountRestController {

    private final BankAccountRepository bankAccountRepository;

    public AccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
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
    public BankAccount save(@RequestBody BankAccount bankAccount) {
        if (bankAccount.getId()==null)bankAccount.setId(UUID.randomUUID().toString());
        return bankAccountRepository.save(bankAccount);
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
