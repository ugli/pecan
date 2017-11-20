package se.ugli.pecan;

public class PecanException extends RuntimeException {

    private static final long serialVersionUID = -5398966251122596874L;

    public PecanException(Exception e) {
        super(e);
    }

    public PecanException(String msg) {
        super(msg);
    }

}
