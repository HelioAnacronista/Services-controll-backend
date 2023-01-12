package io.helioanacronista.servicescontroll.services.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String msg) {
        super(msg);
    }
}