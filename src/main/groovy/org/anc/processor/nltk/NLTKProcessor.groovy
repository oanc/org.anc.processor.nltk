package org.anc.processor.nltk

import org.anc.index.core.IndexImpl
import org.anc.processor.Abstract.AbstractProcessor
import org.xces.graf.io.dom.ResourceHeader
import javax.ws.rs.Path


/**
 * Created by danmccormack on 12/12/14.
 */
@Path("/nltk")
class NLTKProcessor extends AbstractProcessor {

    public NLTKProcessor() {
        super(["f.fn", "f.fntok", "f.ptb", "f.ptbtok"] as List<String>)
        processor = new org.anc.tool.nltk.NLTKProcessor();

    }


}
