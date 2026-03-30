package team7.exeption;

public class NonTrovato extends RuntimeException {
    public NonTrovato(String message) {
        super(message);
    }
}
