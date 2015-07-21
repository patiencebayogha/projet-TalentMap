package com.viseo.talentmap.common.exception;

/**
 * Created by patiencebayogha on 18/02/15.
 * This class is a Exception Class for AuthService.java
 */
public class TalentMapAuthenticateException extends TalentMapException {

    public TalentMapAuthenticateException() {
        super();
    }

    public TalentMapAuthenticateException(String msg) {
        super(msg);
    }

    public TalentMapAuthenticateException(Throwable throwable) {
        super(throwable);
    }

    public TalentMapAuthenticateException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
