package com.viseo.talentmap.common.exception;

/**
 * Created by jvarin on 26/02/15.
 */
public class TalentMapConnectionException extends TalentMapException {

    public TalentMapConnectionException() {
        super();
    }

    public TalentMapConnectionException(String msg) {
        super(msg);
    }

    public TalentMapConnectionException(Throwable throwable) {
        super(throwable);
    }

    public TalentMapConnectionException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
