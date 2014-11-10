package org.anc.processor.conll

import org.anc.processor.nltk.Annotation
import org.junit.*
import static org.junit.Assert.*

/**
 * Created by danmccormack on 11/3/14.
 */
class AnnotationTest
{
    /**
     * Cases to Test: Passing (1, all, a few)
     *                Failing (none, all wrong, one wrong in a list of accepted)
     */
    @Test
    void testValidAnnotations()
    {
        def testy = new Annotation()

        //PASSING ********
        //ONE
        def pass1 = ["PENN"]
        assertTrue(testy.validAnnotations(pass1))

        //FEW
        def pass2 = ["PENN", "FN", "PTB"]
        assertTrue(testy.validAnnotations(pass2))

        //ALL
        def pass3 = ["PENN", "FN", "FNTOK", "PTB", "PTBTOK"]
        assertTrue(testy.validAnnotations(pass3))

        //FAILING ********
        //NONE
        def fail1 = [] as ArrayList<String>
        assertFalse(testy.validAnnotations(fail1))

        //ALL
        def fail2 = ["C5", "BIBER", "NE", "MPQA"]
        assertFalse(testy.validAnnotations(fail2))

        //ONE WRONG IN LIST OF RIGHT
        def fail3 = ["PENN", "FN", "BIBER", "FNTOK"]
        assertFalse(testy.validAnnotations(fail3))
    }

    @Test
    /**
     *  Cases to test: empty string, one word, two words, n-words
     */
    void testParseAnnotations()
    {
        def testy = new Annotation()

        //EMPTY STRING
        assertTrue(testy.parseAnnotations("") == [])

        //ONE WORD
        assertTrue(testy.parseAnnotations("PENN").equals(["PENN"]))

        //TWO WORDS
        assertTrue(testy.parseAnnotations("PENN,C5").equals(["PENN","C5"]))

        //N WORDS
        def listy = []
        for (int i =1; i<10; i++)
        {
            listy.add(i.toString())
        }
        assertTrue(testy.parseAnnotations("1,2,3,4,5,6,7,8,9").equals(listy))
    }
}
