package br.com.gabr.cm.exception;

import java.io.Serial;

public class ExplosionException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public String toString() {
        return "You Exploded!";
    }

}
