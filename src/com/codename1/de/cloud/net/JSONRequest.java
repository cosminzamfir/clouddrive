package com.codename1.de.cloud.net;

import com.codename1.io.JSONParser;
import com.codename1.io.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * A {@link HttpRequest} extension which expects a JSon formatted response
 * @author Cosmin Zamfir
 *
 */
public class JSONRequest extends HttpRequest {

    public JSONRequest(String url) {
        super(url);
    }

    public JSONRequest(String url, boolean post) {
        super(url, post);
    }

    public Hashtable getJsonResponse(long timeout) {
        byte[] response = run(timeout);
        if (response == null) {
            return null;
        }
        Log.p("JSon response received: " + new String(response));
        JSONParser parser = new JSONParser();
        try {
            return parser.parse(new InputStreamReader(new ByteArrayInputStream(response)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing json response. " + e.getMessage());
        }
    }

}
