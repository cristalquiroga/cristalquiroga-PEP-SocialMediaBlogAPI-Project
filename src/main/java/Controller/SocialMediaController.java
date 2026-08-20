package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);
        if (addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }
        else
            context.status(400);
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loggedIn = accountService.Login(account.getUsername(), account.getPassword());
        if (loggedIn != null){
            context.json(mapper.writeValueAsString(loggedIn));
            context.status(200);
        }
        else
            context.status(401);
    }

    private void createMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if (addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
            context.status(200);
        }
        else
            context.status(400);
    }

    private void getMessagesHandler(Context context){
        List<Message> messages = messageService.getMessages();
        context.json(messages);
        context.status(200);
    }

    private void getMessageHandler(Context context){
        Message message = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if (message != null)
            context.json(message);
        context.status(200);
    }

    private void deleteMessageHandler(Context context){
        Message message = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if (message != null){
            messageService.deleteMessage(message.getMessage_id());
            context.json(message);
        }
        context.status(200);
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        Message message = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if (message != null){
            ObjectMapper mapper = new ObjectMapper();
            Message messageBody = mapper.readValue(context.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(message.getMessage_id(), messageBody.getMessage_text());
            if (updatedMessage != null){
                context.json(mapper.writeValueAsString(updatedMessage));
                context.status(200);
            }
            else
                context.status(400);
        }
        else
            context.status(400);
    }

    private void getMessagesByAccountHandler(Context context){
        List<Message> messages = messageService.getMessagesByAccount(Integer.parseInt(context.pathParam("account_id")));
        context.json(messages);
        context.status(200);
    }
}