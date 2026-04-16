import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.*;

public class Testing {

    @Test
    @DisplayName("EXAMPLE TEST CASE - Non-continuous Spec Example")
    public void subSpecTest() {
        // Remember that you can change VALID_CHARS
        // in Cipher.java to make testing easier! For this
        // example test, we are using characters RSACLVJ

        // If this is false (i.e. the valid characters are not RSACLVJ), the test will
        // halt and
        // be ignored (aborted). This doesn't mean that the code is wrong! It just means
        // that
        // the test won't produce any meaningful information if the assumption is not
        // met.
        assumeTrue(Cipher.VALID_CHARS.equals("RSACLVJ"));

        Cipher testSubstitution = new Substitution("JLCASVR");
        assertEquals("JCL", testSubstitution.encrypt("RAS"));
        assertEquals("RVL", testSubstitution.decrypt("JVS"));

        // Per the spec, we should throw an IllegalArgumentException if
        // the length of the shifter doesn't match the number of valid characters
        assertThrows(IllegalArgumentException.class, () -> {
            new Substitution("SAV");
        });
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'Z', 'a'-'z' Shifter")
    public void subAZazTest() {
        // If this is false (i.e. the valid characters are not 'A'-'Z, 'a'-'z'),
        // the test will halt and be ignored (aborted). This doesn't mean that
        // the code is wrong! It just means that the test won't produce any
        // meaningful information if the assumption is not met.
        assumeTrue(
                Cipher.VALID_CHARS.equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));

        // Reverse alphabetic
        Cipher testSubstitution = new Substitution(
                "zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA");
        assertEquals("UZW", testSubstitution.encrypt("fad"));
        assertEquals("bAd", testSubstitution.decrypt("YzW"));
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - ' '-'}' Shifter")
    public void subComplexTest() {
        // If this is false (i.e. the valid characters are not ' '-'}'), the test will
        // halt and
        // be ignored (aborted). This doesn't mean that the code is wrong! It just means
        // that
        // the test won't produce any meaningful information if the assumption is not
        // met.
        assumeTrue(Cipher.VALID_CHARS
                .equals(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
                        + "abcdefghijklmnopqrstuvwxyz{|}"));

        // Swapping lowercase a<->b
        Cipher testSubstitution = new Substitution(
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`"
                        + "bacdefghijklmnopqrstuvwxyz{|}");
        assertEquals("FAD", testSubstitution.encrypt("FAD"));
        assertEquals("fbd", testSubstitution.encrypt("fad"));
        assertEquals("BAD", testSubstitution.decrypt("BAD"));
        assertEquals("bad", testSubstitution.decrypt("abd"));
    }

    @Test
    @DisplayName("TODO: CaesarKey - 'A'-'Z', 'a'-'z'")
    public void keyAZazOne() {
        // If this is false (i.e. the valid characters are not 'A'-'Z, 'a'-'z'),
        // the test will halt and be ignored (aborted). This doesn't mean that
        // the code is wrong! It just means that the test won't produce any
        // meaningful information if the assumption is not met.
        assumeTrue(
                Cipher.VALID_CHARS.equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));

        Cipher testKey = new CaesarKey("tiN");
        String encrypted = testKey.encrypt("HEllO");

        assertEquals("EBkkL", encrypted);
        assertEquals("HEllO", testKey.decrypt(encrypted));
    }

    @Test
    @DisplayName("TODO: CaesarShift - 'A'-'Z', 'a'-'z' Shifter")
    public void shiftAZazOne() {
        // If this is false (i.e. the valid characters are not 'A'-'Z, 'a'-'z'),
        // the test will halt and be ignored (aborted). This doesn't mean that
        // the code is wrong! It just means that the test won't produce any
        // meaningful information if the assumption is not met.
        assumeTrue(
                Cipher.VALID_CHARS.equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));

        Cipher testShift = new CaesarShift(6);
        String encrypted = testShift.encrypt("HEllO");

        assertEquals("NKrrU", encrypted);
        assertEquals("HEllO", testShift.decrypt(encrypted));
    }

    @Test
    @DisplayName("TODO: MultiCipher - 'A'-'Z', 'a'-'z' Shifter")
    public void multiAZazOne() {
        // If this is false (i.e. the valid characters are not 'A'-'Z, 'a'-'z'),
        // the test will halt and be ignored (aborted). This doesn't mean that
        // the code is wrong! It just means that the test won't produce any
        // meaningful information if the assumption is not met.
        assumeTrue(
                Cipher.VALID_CHARS.equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));

        List<Cipher> ciphers = new ArrayList<>();
        ciphers.add(new CaesarKey("tiN"));
        ciphers.add(new CaesarShift(6));

        Cipher testMulti = new MultiCipher(ciphers);
        String encrypted = testMulti.encrypt("HEllO");

        assertEquals("KHqqR", encrypted);
        assertEquals("HEllO", testMulti.decrypt(encrypted));
    }
}
