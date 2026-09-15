package br.ufpe.cin.lf1.typechecker;

public class TypeErrorException extends RuntimeException {
    public TypeErrorException(String message) {
        super(message);
    }
}