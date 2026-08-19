package Service;
import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;

public class MessageService {
  private MessageDAO messageDAO;
  private AccountService accountService;

  public MessageService(){
    messageDAO = new MessageDAO();
    accountService = new AccountService();
  }

  public Message createMessage(Message message){
    if (message.getMessage_text() != "" && message.getMessage_text().length() <= 255 && accountService.AccountExists(message.getPosted_by()) == true) 
      return messageDAO.createMessage(message);
    else
      return null;
  }
}