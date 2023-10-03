/*
 * Copyright (c) 2006, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package servlet.tck.common.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents an HTTP response from the server.
 */

public class HttpResponse {

  /**
   * Default encoding based on Servlet Specification
   */
  private static final String DEFAULT_ENCODING = "ISO-8859-1";

  /**
   * Content-Type header
   */
  private static final String CONTENT_TYPE = "Content-Type";


  /**
   * Charset encoding returned in the response
   */
  private String _encoding = DEFAULT_ENCODING;

  /**
   * The response body. Initialized after first call to one of the
   * getResponseBody methods and cached for subsequent calls.
   */
  private String _responseBody = null;

  /**
   * Host name used for processing request
   */
  private String _host;

  /**
   * Port number used for processing request
   */
  private int _port;

  /**
   * Issecure
   */
  private boolean _isSecure;

  private java.net.http.HttpResponse<String> response;

  /** Creates new HttpResponse */
  public HttpResponse(String host, int port, boolean isSecure, String method, java.net.http.HttpResponse<String> response) {

    _host = host;
    _port = port;
    _isSecure = isSecure;
    /**
     * Wrapped HttpMethod used to pull response info from.
     */
    this.response = response;
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * Returns the HTTP status code returned by the server
   *
   * @return HTTP status code
   */
  public String getStatusCode() {
    return Integer.toString(response.statusCode());
  }

  /**
   * Returns the HTTP reason-phrase returned by the server
   *
   * @return HTTP reason-phrase
   */
  public String getReasonPhrase() {
    return "";
  }

  /**
   * Returns the headers received in the response from the server.
   *
   * @return response headers
   */
  public List<Header> getResponseHeaders() {
    return response.headers().map().entrySet()
            .stream().map(stringListEntry -> new Header(stringListEntry.getKey(), stringListEntry.getValue()))
            .collect(Collectors.toList());
  }

  /**
   * Returns the headers designated by the name provided.
   *
   * @return response headers
   */
  public List<String> getResponseHeaders(String headerName) {
    return response.headers().allValues(headerName);
  }

  /**
   * Returns the response header designated by the name provided.
   *
   * @return a specfic response header or null if the specified header doesn't
   *         exist.
   */
  public Optional<Header> getResponseHeader(String headerName) {
    List<String> s = response.headers().allValues(headerName);
    return s.isEmpty()?Optional.empty() : Optional.of(new Header(headerName, String.join(", ", s)));
  }

  /**
   * Returns the response body as a byte array using the charset specified in
   * the server's response.
   *
   * @return response body as an array of bytes.
   */
  public byte[] getResponseBodyAsBytes() throws IOException {
    return getEncodedResponse().getBytes();
  }

  /**
   * Returns the response as bytes (no encoding is performed by client.
   * 
   * @return the raw response bytes
   * @throws IOException
   *           if an error occurs reading from server
   */
  public byte[] getResponseBodyAsRawBytes() throws IOException {
    return response.body().getBytes();
  }

  /**
   * Returns the response body as a string using the charset specified in the
   * server's response.
   *
   * @return response body as a String
   */
  public String getResponseBodyAsString() throws IOException {
    return getEncodedResponse();
  }

  /**
   * Returns the response body of the server without being encoding by the
   * client.
   * 
   * @return an unecoded String representation of the response
   * @throws IOException
   *           if an error occurs reading from the server
   */
  public String getResponseBodyAsRawString() throws IOException {
    return response.body();
  }

  /**
   * Returns the response body as an InputStream using the encoding specified in
   * the server's response.
   *
   * @return response body as an InputStream
   */
  public InputStream getResponseBodyAsStream() throws IOException {
    return new ByteArrayInputStream(getEncodedResponse().getBytes());
  }


  /**
   * Returns the charset encoding for this response.
   *
   * @return charset encoding
   */
  public String getResponseEncoding() {
    Optional<String> content = response.headers().firstValue(CONTENT_TYPE);
    if (content.isPresent()) {
      String headerVal = content.get();
      int idx = headerVal.indexOf(";charset=");
      if (idx > -1) {
        // content encoding included in response
        _encoding = headerVal.substring(idx + 9);
      }
    }
    return _encoding;
  }

  /**
   * Returns the post-request state.
   *
   * @return an HttpState object
   */
//  public HttpState getState() {
//    return _state;
//  }

  /**
   * Displays a String representation of the response.
   *
   * @return string representation of response
   */
  public String toString() {
    StringBuilder sb = new StringBuilder(255);

    sb.append("[RESPONSE STATUS LINE] -> ");
    sb.append(response.version().toString());
    sb.append(response.statusCode()).append(' ');
    sb.append(System.lineSeparator());
    sb.append("       [RESPONSE HEADER] -> ");
    for (Map.Entry<String, List<String>> header : response.headers().map().entrySet()){
        sb.append(header.getKey()).append(':').append(header.getValue()).append(System.lineSeparator());
    }

    String resBody = response.body();

    if (resBody != null && !resBody.isEmpty()) {
      sb.append("------ [RESPONSE BODY] ------").append(System.lineSeparator());
      sb.append(resBody);
      sb.append("\n-----------------------------\n\n");
    }
    return sb.toString();
  }

  /*
   * Eventually they need to come from _method
   */

  public String getHost() {
    return _host;
  }

  public int getPort() {
    return _port;
  }

  public String getProtocol() {
    return _isSecure ? "https" : "http";
  }
//
//  public String getPath() {
//    return _method.getPath();
//  }

  /*
   * Private Methods
   * ==========================================================================
   */

  /**
   * Returns the response body using the encoding returned in the response.
   *
   * @return encoded response String.
   */
  private String getEncodedResponse() throws IOException {
    if (_responseBody == null) {
      _responseBody = response.body();
    }
    return _responseBody;
  }

  public static String getEncodedStringFromStream(InputStream in, String enc)
          throws IOException {
    BufferedReader bin = new BufferedReader(new InputStreamReader(in, enc));
    StringBuilder sb = new StringBuilder(128);
    for (int ch = bin.read(); ch != -1; ch = bin.read()) {
      sb.append((char) ch);
    }
    return sb.toString();
  }

}
