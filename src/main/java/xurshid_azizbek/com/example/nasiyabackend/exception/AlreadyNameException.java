package xurshid_azizbek.com.example.nasiyabackend.exception;

import lombok.Getter;

@Getter
public class AlreadyNameException extends RuntimeException {
    private final Integer personId;
    private final Integer number;

    public AlreadyNameException(String message) {
        this(message, null, null);
    }

    public AlreadyNameException(String message, Integer personId, Integer number) {
        super(message);
        this.personId = personId;
        this.number = number;
    }
}