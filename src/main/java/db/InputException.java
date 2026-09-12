package db;

import java.io.Serial;

public class InputException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public InputException(String msg) {
        super(msg);
    }

}
