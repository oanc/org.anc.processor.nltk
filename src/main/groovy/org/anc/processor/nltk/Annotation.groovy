package org.anc.processor.nltk

import javax.ws.rs.GET
import javax.ws.rs.QueryParam
import javax.xml.ws.Response
/**
 * Created by danmccormack on 11/2/14.
 */
class Annotation
{
    @GET
    Response Process(@QueryParam('annotations') String annotations,
                     @QueryParam('docID') String docID)
    {
        def AnnotationsArray = parseAnnotations(annotations)

        if (validAnnotations(AnnotationsArray))
        {
            //call the processor with the selected annotations
        }
    }

    // nltk can only accept tokens with part-of-speech
    // So either Penn tokens, PTB tokens, or FrameNet Tokens

    def AcceptableAntns = ["PENN",
                           "FN", "FNTOK",
                           "PTB", "PTBTOK"]

    /**
     * Check if the annotations provided are acceptable for conll processing
     * @param antnArray - ArrayList<String> of annotations
     * @return A boolean response if the annotations passed in are acceptable for
     * conll processing.
     */
    def validAnnotations (ArrayList<String> antnArray)
    {
        boolean returnval = true
        if (antnArray == [])
        {
            return false
        }
        else {
            for (antn in antnArray)
            {
                // if any of the acceptable annotations equal the current annotation
                if (!AcceptableAntns.any { it.equals(antn) })
                {
                    returnval = false
                    break
                }
            }
            return returnval
        }
    }

    /**
     * Split the comma separated string into an ArrayList<String>
     * @param antnString - The comma separated string of selected annotations
     * @return An ArrayList<String> of the selected annotations
     */
    def parseAnnotations(String antnString)
    {
        if (antnString == "")
        {
            return []
        } else
        {
            def retArray = antnString.split(',')
            return retArray
        }
    }
}
