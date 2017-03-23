package com.codename1.de.cloud.drive.exception;

/**
 * 
 * @author Cosmin Zamfir
 *
 */
public class HttpErrorCodeException extends CloudSyncException {

	private int errorCode;
    private String responseMessage;

    public HttpErrorCodeException(int errorCode, String message) {
        super("Error code : " + errorCode + "; Error message : " + (message == null ? "n/a" : message));
		this.errorCode = errorCode;
		this.responseMessage = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

    public String getResponseMessage() {
        return responseMessage;
    }
}
