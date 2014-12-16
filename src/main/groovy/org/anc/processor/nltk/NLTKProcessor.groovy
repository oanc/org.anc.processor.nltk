package org.anc.processor.nltk

import org.anc.index.core.IndexImpl
import org.xces.graf.io.dom.ResourceHeader

import javax.ws.rs.Path

/**
 * Created by danmccormack on 12/12/14.
 */
@Path("/nltk")
class NLTKProcessor extends AbstractProcessor{

    // nltk can only accept tokens with part-of-speech
    // So either Penn tokens, PTB tokens, or FrameNet Tokens
    public static final Set<String> ACCEPTABLE  =
            ["f.penn",
             "f.fn", "f.fntok",
             "f.ptb", "f.ptbtok"] as HashSet<String>


    public NLTKProcessor() {
        processor = new org.anc.tool.nltk.NLTKProcessor();
        //TODO This path should not be hard coded.
        header = new ResourceHeader(new File("/var/corpora/MASC-3.0.0/resource-header.xml"))
        index = new IndexImpl().loadMasc3Index()
        setAcceptable(ACCEPTABLE)
    }
}
