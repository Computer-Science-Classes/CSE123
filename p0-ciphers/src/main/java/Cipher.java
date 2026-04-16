import java.util.*;
import java.io.*;

// Represents a classical cipher that is able to encrypt a plaintext into a ciphertext, and
// decrypt a ciphertext into a plaintext. Also capable of encrypting and decrypting entire files
public abstract class Cipher {
    // The valid characters allowed to be encoded or decoded by our Cipher.
    // May or may not be in order. May or may not be contiguous.
    public static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // Here are some other VALID_CHARS Strings that you can play around with!
    // Uncomment or copy-paste the VALID_CHARS String you want to use, and comment
    // the one you don't want to use:

    /*
     * Spec example: public static final String VALID_CHARS = "RSACLVJ";
     *
     * A-Z, a-z public static final String VALID_CHARS =
     * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
     *
     * A-G public static final String VALID_CHARS = "ABCDEFG";
     *
     * ' ' - '}' public static final String VALID_CHARS =
     * " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
     * "bacdefghijklmnopqrstuvwxyz{|}";
     */

    // Behavior: Applies this Cipher's encryption scheme to the file with the
    // given 'fileName', creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - The name of the file to be encrypted. Should be
    // non-null.
    public void encryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, true, "-encrypted");
    }

    // Behavior: Applies the inverse of this Cipher's encryption scheme to the file
    // with the
    // given 'fileName' (reversing a single round of encryption if previously
    // applied)
    // creating a new file to store the results.
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - The name of the file to be decrypted. Should be
    // non-null.
    public void decryptFile(String fileName) throws FileNotFoundException {
        fileHelper(fileName, false, "-decrypted");
    }

    // Behavior: Reads from an input file with 'fileName', either encrypting or
    // decrypting
    // depending on 'encrypt', printing the results to a new file with 'suffix'
    // appended to the input file's name
    // Exceptions: Throws a FileNotFoundException if a file with the provided
    // 'fileName'
    // doesn't exist
    // Returns: None
    // Parameters: 'fileName' - the name of the file to be encrypted / decrypted.
    // Should be
    // non-null.
    // 'encrypt' - whether or not encryption should occur
    // 'suffix' - appended to the fileName when creating the output file. Should be
    // non-null.
    private void fileHelper(String fileName, boolean encrypt, String suffix)
            throws FileNotFoundException {
        Scanner sc = new Scanner(new File(fileName));
        String out = fileName.split("\\.txt")[0] + suffix + ".txt";
        PrintStream ps = new PrintStream(out);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            ps.println(encrypt ? encrypt(line) : decrypt(line));
        }
    }

    // Behavior: Returns whether the character is valid.
    // Exceptions: None
    // Returns: True if this character is valid. False otherwise.
    // Parameters: 'character' - The character to check
    public static boolean isCharValid(char character) {
        return VALID_CHARS.indexOf(character) != -1;
    }

    // Behavior: Applies this Cipher's encryption scheme to 'input', returning the
    // result
    // Exceptions: Throws an IllegalArgumentException if 'input' is null
    // Returns: The result of applying this Cipher's encryption scheme to `input`
    // Parameters: 'input' - the string to be encrypted. Should be non-null and all
    // characters of
    // 'input' should contain only valid characters.
    public abstract String encrypt(String input);

    // Behavior: Applies this inverse of this Cipher's encryption scheme to 'input'
    // (reversing
    // a single round of encryption if previously applied), returning the result
    // Exceptions: Throws an IllegalArgumentException if 'input' is null
    // Returns: The result of applying the inverse of this Cipher's encryption
    // scheme to `input`
    // Parameters: 'input' - the string to be encrypted. Should be non-null and all
    // characters of
    // 'input' should contain only valid characters.
    public abstract String decrypt(String input);
}
