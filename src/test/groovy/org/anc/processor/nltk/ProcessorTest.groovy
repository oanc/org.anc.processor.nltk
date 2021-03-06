package org.anc.processor.nltk

import org.anc.processor.nltk.i18n.Messages
import org.junit.*

import javax.naming.ConfigurationException
import javax.ws.rs.core.Response

import static org.junit.Assert.*

/**
 * Created by danmccormack on 11/3/14.
 */
class ProcessorTest
{
    private static final Messages MESSAGES = new Messages()

    NLTKProcessor processor

    @Before
    void setup(){
        processor = new NLTKProcessor()
    }

    @After
    void cleanup(){
        processor = null
    }

    /**
     * Cases to Test: Passing (1, all, a few)
     *                Failing (none, all wrong, one wrong in a list of accepted)
     */
    @Test
    void testValidAnnotations()
    {
        //PASSING ********
        //ONE
        def pass1 = ["f.fn"] as List<String>
        assertTrue(processor.validAnnotations(pass1))

        //FEW
        def pass2 = ["f.fn", "f.fntok"] as List<String>
        assertTrue(processor.validAnnotations(pass2))

        //ALL
        def pass3 = ["f.ptb", "f.ptbtok"] as List<String>
        assertTrue(processor.validAnnotations(pass3))

        //FAILING ********
        //NONE
        def fail1 = [] as List<String>
        assertFalse(processor.validAnnotations(fail1))

        //ALL
        def fail2 = ["f.c5", "f.biber", "f.ne", "f.mpqa"] as List<String>
        assertFalse(processor.validAnnotations(fail2))

        //ONE WRONG IN LIST OF RIGHT
        def fail3 = ["f.penn", "f.fn", "f.biber", "f.fntok"] as List<String>
        assertFalse(processor.validAnnotations(fail3))
    }

    @Test
    /**
     *  Cases to test: empty string, one word, two words, n-words
     */
    void testParseAnnotations()
    {
        //EMPTY STRING
        assertTrue("Empty string should return all acceptable annotations", processor.parseAnnotations("") == processor.Acceptable.toList() as List<String>)

        //ONE WORD
        List expected = ["f.penn"]
        List actual = processor.parseAnnotations("penn")
        assertTrue "Actual is " + actual, actual == expected

        //TWO WORDS
        expected = ["f.penn", "f.cb"]
        assertTrue(processor.parseAnnotations("penn,cb") == expected)

        //N WORDS
        // Use Groovy range operator to populate the collection, then add the 'f.' prefix.
        expected = (1..9).collect { "f." + it }
        actual = processor.parseAnnotations("1,2,3,4,5,6,7,8,9")
        assertTrue("Expected is " + expected + " Actual is " + actual, actual == expected)
    }

    @Test
    void testProcess() {
        Response response = processor.process("ptb", "MASC3-0202")
        assertTrue response.entity, response.status == 200
    }

    @Test
    void testInvalidDocId() {
        Response response = processor.process("penn", "Invalid ID")
        assertTrue response.entity, response.status == 500
        assertTrue "Wrong error message returned", response.entity == MESSAGES.INVALID_ID
    }

    @Test
    void testInvalidAnnotationType() {
        Response response = processor.process("invalid,annotations", "MASC3-0202")
        assertTrue response.entity, response.status == 500
        assertTrue "Wrong error message returned.", response.entity == MESSAGES.INVALID_TYPE
    }
}
