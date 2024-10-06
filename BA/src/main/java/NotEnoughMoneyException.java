public class NotEnoughMoneyException extends Exception{
    public NotEnoughMoneyException(){

    }

    public NotEnoughMoneyException(String message){
        super(message);
    }

    public NotEnoughMoneyException(String message, Throwable cause){
        super(message, cause);
    }
}
