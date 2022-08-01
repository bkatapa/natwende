package com.mweka.natwende.message;

public class SystemMessage {

    private String message;
    private int severity;
    
    public static final int SEVERITY_INFO = 1;
    public static final int SEVERITY_WARN = 2;
    public static final int SEVERITY_ERROR = 3;

    public SystemMessage(String message) {
        this(message, SEVERITY_INFO);
    }

    public SystemMessage(String message, int severity) {
        this.message = message;
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    
}
