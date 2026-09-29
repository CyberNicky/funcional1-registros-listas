package br.ufpe.cin.lf1.prototipo.typechecker;

public class TypeErrorException extends RuntimeException {
    public TypeErrorException(String message) {
        super(message);
    }
}