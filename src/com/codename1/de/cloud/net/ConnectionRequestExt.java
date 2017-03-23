package com.codename1.de.cloud.net;

import com.codename1.io.ConnectionRequest;

/**
 * Override {@link ConnectionRequest#handleException} to do nothing
 * <p>
 * Exception handling happens in the {@link HttpRequest} wraper
 * 
 * @author Cosmin Zamfir
 * 
 */
public class ConnectionRequestExt extends ConnectionRequest {

	private Exception err;
	private int responseErrorCode;
    private String responseMessage;
	
	protected void handleException(Exception err) {
		this.err = err;
	}
	
	protected void handleErrorResponseCode(int code, String message) {
		responseErrorCode = code;
	}
	
	public Exception getErr() {
		return err;
	}
	
	public int getResponseErrorCode() {
		return responseErrorCode;
	}

    public void setResponseErrorCode(int responseErrorCode) {
        this.responseErrorCode = responseErrorCode;
    }

    public void setErrorMessage(String message) {
        this.responseMessage = message;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
