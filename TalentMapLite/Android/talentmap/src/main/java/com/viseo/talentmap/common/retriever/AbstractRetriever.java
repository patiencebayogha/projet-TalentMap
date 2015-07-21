package com.viseo.talentmap.common.retriever;

import com.viseo.talentmap.common.utils.JSONStreamUtils;

public abstract class AbstractRetriever {

    /**
     * Le parser de flux JSON.
     */
    protected JSONStreamUtils jsonStream;


    public AbstractRetriever() {

        this.jsonStream = new JSONStreamUtils();
    }
}
