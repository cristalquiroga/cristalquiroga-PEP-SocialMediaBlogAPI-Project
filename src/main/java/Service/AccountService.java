package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService(){
    accountDAO = new AccountDAO();
  }

  public Account createAccount(Account account){
    if (account.getUsername() != null && !account.getUsername().isBlank() && account.getPassword().length() >= 4 && AccountExists(account.getUsername()) == false)
      return accountDAO.createAccount(account);
    else
      return null;
  }

  public Account Login(String username, String password){
    return accountDAO.Login(username, password);
  }

  public Boolean AccountExists(String username){
    return accountDAO.AccountExistsByUsername(username);
  }

  public Boolean AccountExists(int account_id){
    return accountDAO.AccountExistsByAccountId(account_id);
  }
}