package ChristianAmorSecretSanta.exceptions;

public class PlayerShouldHaveNameException extends RuntimeException {
    public PlayerShouldHaveNameException(String mensaje) {
        super(mensaje);
    }
}
