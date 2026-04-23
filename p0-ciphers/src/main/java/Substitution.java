import java.util.*;

/**
 * Represents a substitution cipher that maps each valid character to a unique
 * replacement character from {@link Cipher#VALID_CHARS}.
 *
 * <p>
 * A substitution cipher is configured by an encoding string whose characters
 * give the replacement for each corresponding character in
 * {@link Cipher#VALID_CHARS}. For this, the character at index {@code i} in
 * {@code VALID_CHARS} is encrypted as the character at index {@code i} in the
 * encoding string.
 */
public class Substitution extends Cipher {
    private Map<Character, Character> encodingMap;
    private Map<Character, Character> reverseEncodingMap;

    /**
     * Constructs a substitution cipher with no encoding set.
     *
     * <p>
     * The encoding must be set with {@link #setEncoding(String)} before this cipher
     * can be used to encrypt or decrypt text.
     */
    public Substitution() {
    }

    /**
     * Constructs a substitution cipher with the given encoding.
     *
     * @param encoding
     *            the encoding string that defines this substitution cipher; must be
     *            non-null, must have the same length as {@link Cipher#VALID_CHARS},
     *            must contain only valid characters, and must not contain duplicate
     *            characters.
     * @throws IllegalArgumentException
     *             if {@code encoding} is invalid.
     */
    public Substitution(String encoding) {
        setEncoding(encoding);
    }

    /**
     * Sets the encoding used by this substitution cipher.
     *
     * <p>
     * The encoding must contain only valid characters, with no duplicates, and must
     * have the same length as {@code VALID_CHARS}.
     *
     * <p>
     * If the new encoding is the same as this encoding then no-op.
     *
     * @param encoding
     *            the new encoding string for this cipher.
     * @throws IllegalArgumentException
     *             if {@code encoding} is null, has the wrong length, contains an
     *             invalid character, or contains duplicate characters.
     */
    public void setEncoding(String encoding) {
        checkEncoding(encoding);
        initEncodingMap(encoding);
    }

    /**
     * Applies Substitution Cipher's encryption scheme to {@code input}, returning
     * the result.
     *
     * @param input
     *            the string to be encrypted. Should be non-null and all characters
     *            of {@code input} should contain only valid characters.
     * @return the result of applying Substitution Cipher's encryption scheme to
     *         {@code input}.
     * @throws IllegalStateException
     *             if {@code encoding} is null.
     * @throws IllegalArgumentException
     *             if {@code input} is null or contains an invalid character.
     */
    @Override
    public String encrypt(String input) {
        if (encodingMap == null) {
            throw new IllegalStateException();
        }
        checkValidChars(input);

        String out = "";
        for (int i = 0; i < input.length(); i++) {
            out += encodingMap.get(input.charAt(i));
        }

        return out;
    }

    /**
     * Applies this inverse of Substitution Cipher's encryption scheme to
     * {@code input} (reversing a single round of encryption if previously applied),
     * returning the result.
     *
     * @param input
     *            the string to be encrypted. Should be non-null and all characters
     *            of {@code input} should contain only valid characters.
     * @return the result of applying the inverse of Substitution Cipher's
     *         encryption scheme to {@code input}.
     * @throws IllegalStateException
     *             if {@code encoding} is null.
     * @throws IllegalArgumentException
     *             if {@code input} is null or contains an invalid character.
     */
    @Override
    public String decrypt(String input) {
        if (reverseEncodingMap == null) {
            throw new IllegalStateException();
        }
        checkValidChars(input);

        String out = "";
        for (int i = 0; i < input.length(); i++) {
            out += reverseEncodingMap.get(input.charAt(i));
        }

        return out;
    }

    /**
     * Verifies that an encoding string is valid for a substitution cipher.
     *
     * <p>
     * A valid encoding must be non-null, must have the same length as
     * {@link Cipher#VALID_CHARS}, must contain only valid characters, and must not
     * contain duplicate characters.
     *
     * @param encoding
     *            the encoding string to validate.
     * @throws IllegalArgumentException
     *             if {@code encoding} is null, has the wrong length, contains an
     *             invalid character, or contains duplicate characters.
     */
    private static void checkEncoding(String encoding) {
        if (encoding == null || encoding.length() != Cipher.VALID_CHARS.length()) {
            throw new IllegalArgumentException();
        }

        checkUniqueValidChars(encoding);
    }

    /**
     * Verifies that the given string is non-null and contains only valid cipher
     * characters.
     *
     * @param text
     *            the string being verified.
     * @throws IllegalArgumentException
     *             if {@code text} is null or contains an invalid character.
     */
    protected static void checkValidChars(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < text.length(); i++) {
            char cur = text.charAt(i);
            if (!Cipher.isCharValid(cur)) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Verifies that the given string is non-null, contains only valid cipher
     * characters, and contains no duplicate characters.
     *
     * @param text
     *            the string being verified.
     * @throws IllegalArgumentException
     *             if {@code text} is null, contains an invalid character, or
     *             contains duplicate characters.
     */
    protected static void checkUniqueValidChars(String text) {
        checkValidChars(text);

        boolean[] seen = new boolean[128];

        for (int i = 0; i < text.length(); i++) {
            char cur = text.charAt(i);
            if (seen[cur]) {
                throw new IllegalArgumentException();
            }
            seen[cur] = true;
        }
    }

    /**
     * Initializes the forward and reverse character mappings for the current
     * encoding.
     *
     * <p>
     * {@code encodingMap} maps each valid plaintext character to its encoded
     * character, and {@code reverseEncodingMap} maps each encoding character back
     * to its plaintext character.
     *
     * @param encoding
     *            the encoding string used to init the maps.
     */
    private void initEncodingMap(String encoding) {
        Map<Character, Character> forward = new HashMap<>();
        Map<Character, Character> reverse = new HashMap<>();

        for (int i = 0; i < encoding.length(); i++) {
            char plain = Cipher.VALID_CHARS.charAt(i);
            char encoded = encoding.charAt(i);

            forward.put(plain, encoded);
            reverse.put(encoded, plain);
        }

        this.encodingMap = forward;
        this.reverseEncodingMap = reverse;
    }
}
