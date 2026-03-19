package ru.sivak.domain.exceptions;

public class DomainValidationException extends DomainException {
    public DomainValidationException(String message) {
        super(message);
    }

    public static DomainValidationException missingNode(String node) {
        return new DomainValidationException("Node " + node + " is missing");
    }


}
