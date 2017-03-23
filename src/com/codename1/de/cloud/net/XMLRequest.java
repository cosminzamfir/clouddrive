package com.codename1.de.cloud.net;

import com.codename1.xml.Element;
import com.codename1.xml.XMLParser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * A {@link HttpRequest} which expects a XML document as response
 * @author Cosmin Zamfir
 *
 */
public class XMLRequest extends HttpRequest {

    public XMLRequest(String url, boolean post) {
        super(url, post);
    }

    public Element getXmlResponse(long timeout) {
        byte[] response = run(timeout);
        XMLParser parser = new XMLParser();
        Element e = parser.parse(new InputStreamReader(new ByteArrayInputStream(response)));
        return e;
    }

}
