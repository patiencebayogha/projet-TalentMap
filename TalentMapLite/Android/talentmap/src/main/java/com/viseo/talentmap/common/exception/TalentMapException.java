package com.viseo.talentmap.common.exception;

/**
 * Created by patiencebayogha on 18/02/15.
 * This class is Exception
 */
public class TalentMapException extends Exception {
    public TalentMapException() {
        super();
    }

    public TalentMapException(String msg) {
        super(msg);
    }

    public TalentMapException(Throwable throwable) {
        super(throwable);
    }

    public TalentMapException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
