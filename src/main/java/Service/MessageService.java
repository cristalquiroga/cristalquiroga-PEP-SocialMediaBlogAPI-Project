package Service;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.List;

public class MessageService {
  private MessageDAO messageDAO;
  private AccountDAO accountDAO;

  public MessageService(){
    messageDAO = new MessageDAO();
    accountDAO = new AccountDAO();
  }

  public Message createMessage(Message message){
    if (message.getMessage_text() != "" && message.getMessage_text().length() <= 255 && accountDAO.AccountExistsByAccountId(message.getPosted_by()) == true) 
      return messageDAO.createMessage(message);
    else
      return null;
  }

  public List<Message> getMessages(){
    return messageDAO.getMessages();
  }

  public Message getMessage(int message_id){
    return messageDAO.getMessage(message_id);
  }

  public Message deleteMessage(int message_id){
    Message message = messageDAO.getMessage(message_id);
    if (message != null)
      messageDAO.deleteMessage(message_id);
    return message;
  }

  public Message updateMessage(int message_id, String message_text){
    Message message = messageDAO.getMessage(message_id);
    if (message != null && message_text != "" && message_text.length() <= 255){
      messageDAO.UpdateMessage(message_id, message_text);
      Message updatedMessage = messageDAO.getMessage(message_id);
      return updatedMessage;
    }
    return null;
  }

  public List<Message> getMessagesByAccount(int account_id){
    return messageDAO.getMessages();
  }
}