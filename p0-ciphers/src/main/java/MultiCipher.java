import java.util.*;

/**
 * Represents a composite cipher that applies multiple ciphers in sequence.
 *
 * <p>
 * A {@code MultiCipher} chains together a list of {@link Cipher} instances.
 * Encryption is performed by passing the input through each cipher in the order
 * they appear in the list. Decryption is performed by applying each cipher's
 * decryption in reverse order.
 */
public class MultiCipher extends Cipher {
    private final List<Cipher> ciphers;

    /**
     * Constructs a {@code MultiCipher} with the provided list of ciphers.
     *
     * @param ciphers
     *            the list of ciphers to apply in sequence.
     * @throws IllegalArgumentException
     *             if {@code ciphers} is null.
     */
    public MultiCipher(List<Cipher> ciphers) {
        if (ciphers == null) {
            throw new IllegalArgumentException();
        }

        this.ciphers = ciphers;
    }

    /**
     * Applies each cipher's encryption in order to the input string.
     *
     * @param input
     *            the string to be encrypted; must be non-null and contain only
     *            valid characters.
     * @return the fully encrypted string after applying all ciphers.
     * @throws IllegalArgumentException
     *             if {@code input} is null.
     */
    @Override
    public String encrypt(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        String out = input;
        for (Cipher c : ciphers) {
            out = c.encrypt(out);
        }

        return out;
    }

    /**
     * Applies each cipher's decryption in reverse order to the input string.
     *
     * @param input
     *            input the string to be decrypted; must be non-null and contain
     *            only valid characters.
     * @return the fully decrypted string.
     * @throws IllegalArgumentException
     *             if {@code input} is null.
     */
    @Override
    public String decrypt(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        String out = input;
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            out = ciphers.get(i).decrypt(out);
        }

        return out;
    }
}
