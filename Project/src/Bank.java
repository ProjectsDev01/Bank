import java.util.ArrayList;
import java.util.List;

class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public long createAccount() {
        Account account = new Account();
        accounts.add(account);

        return account.getAccountNumber();
    }

    private Account findAccount(long accountNumber) throws AccountNotExists {
        for(Account account: accounts) {
            if(account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        throw new AccountNotExists();
    }

    public long withdraw(long accountNumber, long cash) throws AccountNotExists, OperationNotAllowed {
        Account account = findAccount(accountNumber);
        boolean isReduced = account.reduceBalance(cash);

        if(isReduced) {
            return account.getAccountBalance();
        }

        throw new OperationNotAllowed();
    }

    public long deposit(long accountNumber, long cash) throws AccountNotExists, OperationNotAllowed {
        Account account = findAccount(accountNumber);
        boolean isIncrease = account.increaseBalance(cash);

        if(isIncrease) {
            return account.getAccountBalance();
        }

        throw new OperationNotAllowed();
    }

    public boolean transfer(long sourceNumber, long destinationNumber, long cash) {

        try {
            withdraw(sourceNumber, cash);
            deposit(destinationNumber, cash);

        } catch (AccountNotExists | OperationNotAllowed e) {
            return false;
        }

        return true;
    }

    public long accountBalance(long accountNumber) throws AccountNotExists {
        Account account = findAccount(accountNumber);
        return account.getAccountBalance();
    }

    public long bankBalance() {
        long sumBalance = 0;

        for(Account account: accounts) {
            sumBalance += account.getAccountBalance();
        }

        return sumBalance;
    }
}