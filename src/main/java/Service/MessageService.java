package Service;
import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;
import java.util.List;

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

  public Message updateMessage(int message_id, int posted_by, String message_text, long time_posted_epoch){
    Message message = messageDAO.getMessage(message_id);
    if (message != null && message_text != "" && message_text.length() <= 255){
      messageDAO.UpdateMessage(message_id, posted_by, message_text, time_posted_epoch);
      Message updatedMessage = messageDAO.getMessage(message_id);
      return updatedMessage;
    }
    return null;
  }
}