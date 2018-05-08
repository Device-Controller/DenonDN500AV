package vislab.no.ntnu.denon.exception;

public class DN500AVException extends Exception{
    private final String message;

    public DN500AVException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
