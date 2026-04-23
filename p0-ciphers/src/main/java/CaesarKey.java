/**
 * Represents a Caesar key cipher that generates an encoding using a key prefix.
 *
 * <p>
 * This cipher builds an encoding string by placing the provided {@code key} at
 * the front, followed by all remaining valid characters from
 * {@link Cipher#VALID_CHARS} in their original order, excluding any characters
 * already present in the key. The resulting encoding is then used by the
 * {@link Substitution} cipher to perform encryption and decryption.
 */
public class CaesarKey extends Substitution {

    /**
     * Constructs a {@code CaesarKey} cipher using the specified key.
     *
     * <p>
     * The key is used to build the encoding string by placing it at the front and
     * appending all remaining valid characters not already present in the key. The
     * resulting encoding is passed to the {@link Substitution} constructor.
     *
     * @param key
     *            the key used to construct the encoding string.
     * @throws IllegalArgumentException
     *             if {@code key} is null, has the wrong length, contains an invalid
     *             character, or contains duplicate characters.
     */
    public CaesarKey(String key) {
        super(buildEncoding(key));
    }

    /**
     * Builds the encoding string for the caesar key cipher.
     *
     * <p>
     * This method first validates the provided key using
     * {@link Substitution#checkEncoding(String)}. It then constructs a new encoding
     * string by:
     * <ol>
     * <li>Starting with the key</li>
     * <li>Appending each character from {@link Cipher#VALID_CHARS} that does not
     * already appear in the key, preserving their original order</li>
     * </ol>
     *
     * @param key
     *            the key used as the prefix of the encoding.
     * @return a complete encoding string derived from the key and valid characters.
     * @throws IllegalArgumentException
     *             if {@code key} is null, has the wrong length, contains an invalid
     *             character, or contains duplicate characters.
     */
    private static String buildEncoding(String key) {
        checkUniqueValidChars(key);

        String valid = Cipher.VALID_CHARS;

        int size = valid.length() - key.length();
        char[] plainWithoutKey = new char[size];

        int j = 0;
        for (int i = 0; i < valid.length(); i++) {
            char cur = valid.charAt(i);

            if (key.indexOf(cur) == -1) {
                plainWithoutKey[j] = cur;
                j++;
            }
        }

        return key + new String(plainWithoutKey);
    }
}
