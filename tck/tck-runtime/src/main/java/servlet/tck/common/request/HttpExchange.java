/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.*;

/**
 * Represents an HTTP client Request
 */

public class HttpExchange {


  private static final Logger LOGGER = LoggerFactory.getLogger(HttpExchange.class);

  /**
   * Default HTTP port.
   */
  public static int DEFAULT_HTTP_PORT = 80;

  /**
   * Default HTTP SSL port.
   */
  public static final int DEFAULT_SSL_PORT = 443;

  /**
   * No authentication
   */
  public static final int NO_AUTHENTICATION = 0;

  /**
   * Basic authentication
   */
  public static final int BASIC_AUTHENTICATION = 1;

  /**
   * Digest authenctication
   */
  public static final int DIGEST_AUTHENTICATION = 2;

  /**
   * Method representation of request.
   */
  private String _method;

  /**
   * Target web container host
   */
  private String _host;

  /**
   * Target web container port
   */
  private int _port = DEFAULT_HTTP_PORT;

  /**
   * Is the request going over SSL
   */
  private boolean _isSecure = false;

  /**
   * Original request line for this request.
   */
  private String _requestLine = null;

  /**
   * Authentication type for current request
   */
  private int _authType = NO_AUTHENTICATION;

  /**
   * Flag to determine if session tracking will be used or not.
   */
  private boolean _useCookies = false;

  /**
   * FollowRedirects
   */
  private boolean _redirect = false;

  List<HttpHeaders> headers = null;

  private boolean state;

  private String uri;

  private String query;

  private String content;

  private String username;

  private String password;

  // TODO change to enum?
  private int authType;

  private String realm;

  private Map<String, String> requestHeaders = new HashMap<>();

  private boolean followRedirect;

  /**
   * Creates new HttpRequest based of the passed request line. The request line
   * provied must be in the form of:<br>
   * 
   * <pre>
   *     METHOD PATH HTTP-VERSION
   *     Ex.  GET /index.html HTTP/1.0
   * </pre>
   */
  public HttpExchange(String requestLine, String host, int port) {

    StringTokenizer st = new StringTokenizer(requestLine);
    String method;
    String version;
    try {
      this._method = st.nextToken();
      uri = st.nextToken();
      version = st.nextToken();
    } catch (NoSuchElementException nsee) {
      throw new IllegalArgumentException(
              "Request provided: " + requestLine + " is malformed.");
    }

    _host = host;
    _port = port;

    if (port == DEFAULT_SSL_PORT) {
      _isSecure = true;
    }

    // If we got this far, the request line is in the proper
    // format
    _requestLine = requestLine;

    // check to see if there is a query string appended
    // to the URI
    int queryStart = uri.indexOf('?');
    if (queryStart != -1) {
      query = uri.substring(queryStart + 1);
      uri = uri.substring(0, queryStart);
    }
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * <code>getRequestPath</code> returns the request path for this particular
   * request.
   *
   * @return String request path
   */
  public String getRequestPath() {
    return uri;
  }

  /**
   * <code>getRequestMethod</code> returns the request type, i.e., GET, POST,
   * etc.
   *
   * @return String request type
   */
  public String getRequestMethod() {
    return _method;
  }

  /**
   * <code>isSecureConnection()</code> indicates if the Request is secure or
   * not.
   *
   * @return boolean whether Request is using SSL or not.
   */
  public boolean isSecureRequest() {
    return _isSecure;
  }

  /**
   * <code>setSecureRequest</code> configures this request to use SSL.
   *
   * @param secure
   *          - whether the Request uses SSL or not.
   */
  public void setSecureRequest(boolean secure) {
    _isSecure = secure;
  }

  /**
   * <code>setContent</code> will set the body for this request. Note, this is
   * only valid for POST and PUT operations, however, if called and the request
   * represents some other HTTP method, it will be no-op'd.
   *
   * @param content
   *          request content
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * <code>setAuthenticationCredentials configures the request to
   * perform authentication.
   *
   * <p><code>username</code> and <code>password</code> cannot be null.
   * </p>
   *
   * <p>
   * It is legal for <code>realm</code> to be null.
   * </p>
   *
   * @param username
   *          the user
   * @param password
   *          the user's password
   * @param authType
   *          authentication type
   * @param realm
   *          authentication realm
   */
  public void setAuthenticationCredentials(String username, String password,
      int authType, String realm) {
    if (username == null) {
      throw new IllegalArgumentException("Username cannot be null");
    }

    if (password == null) {
      throw new IllegalArgumentException("Password cannot be null");
    }

    this.username = username;
    this.password = password;
    this.realm = realm;
    LOGGER.debug("Added credentials for '{}' with password '{}' in realm '{}'", username, password, realm);

    _authType = authType;
  }

  /**
   * <code>addRequestHeader</code> adds a request header to this request. If a
   * request header of the same name already exists, the new value, will be
   * added to the set of already existing values.
   *
   * <strong>NOTE:</strong> that header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void addRequestHeader(String headerName, String headerValue) {
    requestHeaders.put(headerName, headerValue);
    LOGGER.debug("Added request header: {}:{}", headerName, headerValue);
  }

  public void addRequestHeader(String header) {
    StringTokenizer st = new StringTokenizer(header, "|");
    while (st.hasMoreTokens()) {
      String h = st.nextToken();
      if (h.toLowerCase().startsWith("cookie")) {
        createCookie(h);
        continue;
      }
      int col = h.indexOf(':');
      addRequestHeader(h.substring(0, col).trim(), h.substring(col + 1).trim());
    }
  }

  /**
   * <code>setRequestHeader</code> sets a request header for this request
   * overwritting any previously existing header/values with the same name.
   *
   * <strong>NOTE:</strong> Header names are not case-sensitive.
   *
   * @param headerName
   *          request header name
   * @param headerValue
   *          request header value
   */
  public void setRequestHeader(String headerName, String headerValue) {
    requestHeaders.put(headerName, headerValue);
    LOGGER.trace("Set request header: {}:{}", headerName, headerValue);

  }

  /**
   * <code>setFollowRedirects</code> indicates whether HTTP redirects are
   * followed. By default, redirects are not followed.
   */
  public void setFollowRedirects(boolean followRedirects) {
    this.followRedirect = followRedirects;
  }

  /**
   * <code>getFollowRedirects</code> indicates whether HTTP redirects are
   * followed.
   */
  public boolean getFollowRedirects() {
    return this.followRedirect;
  }


  private static final ThreadLocal<HttpClient> httpClientThreadLocal = new ThreadLocal<>();
  /**
   * <code>execute</code> will dispatch the current request to the target
   * server.
   *
   * @return HttpResponse the server's response.
   * @throws IOException
   *           if an I/O error occurs during dispatch.
   */
  public HttpResponse execute() throws IOException {
    String method;
    int defaultPort;

    HttpClient.Builder builder = HttpClient.newBuilder().followRedirects(followRedirect?HttpClient.Redirect.ALWAYS:HttpClient.Redirect.NEVER)
            .version(HttpClient.Version.HTTP_1_1)
            .cookieHandler(new CookieManager());

    StringBuilder url = new StringBuilder();

    if(isSecureRequest()){
      url.append("https://");
    } else {
      url.append("http://");
    }
    url.append(_host).append(':').append(_port);
    url.append(uri);
    if(query!=null){
      url.append('?').append(query);
    }

    switch (_authType) {
      case NO_AUTHENTICATION:
        break;
      case BASIC_AUTHENTICATION:
        builder.authenticator(new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password.toCharArray());
          }
        });
        break;
      case DIGEST_AUTHENTICATION:
        throw new UnsupportedOperationException("Digest Authentication is not currently " + "supported");
    }

    HttpClient httpClient = null;

    if(state) {
      httpClient = httpClientThreadLocal.get();
    } else {
      httpClientThreadLocal.set(null);
    }

    if (httpClient == null) {
      httpClient = builder.build();
    }

    if (state) {
      httpClientThreadLocal.set(httpClient);
    }



    HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().uri(URI.create(url.toString()));

    switch (_method) {
      case "GET":
        httpRequestBuilder.GET();
        break;
      case "HEAD":
        // from java 18 only
        //httpRequestBuilder.HEAD();
        httpRequestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
        break;
      case "POST":
        httpRequestBuilder.POST(this.content == null ?
                HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(this.content));
        break;
      default:
        throw new RuntimeException("unknow method " + _method);
    }

    for(Map.Entry<String, String> entry : requestHeaders.entrySet()) {
      httpRequestBuilder.header(entry.getKey(), entry.getValue());
    }

    try {
      java.net.http.HttpResponse<String> response = httpClient.send(httpRequestBuilder.build(), java.net.http.HttpResponse.BodyHandlers.ofString());
      return new HttpResponse(_host, _port, _isSecure, _method, response);
    } catch (InterruptedException e) {
      throw new IOException(e.getMessage(), e);
    }


    /*
    ProtocolSocketFactory factory;

    if (_method.getFollowRedirects()) {


      if (_isSecure) {
        method = "https";
        defaultPort = DEFAULT_SSL_PORT;
        factory = new SSLProtocolSocketFactory();
      } else {
        method = "http";
        defaultPort = DEFAULT_HTTP_PORT;
        factory = new DefaultProtocolSocketFactory();
      }

      Protocol protocol = new Protocol(method, factory, defaultPort);
      HttpConnection conn = new HttpConnection(_host, _port, protocol);

      if (conn.isOpen()) {
        throw new IllegalStateException("Connection incorrectly opened");
      }

      conn.open();

      LOGGER.info("[HttpRequest] Dispatching request: '{}' to target server at '{}:{}'", _requestLine , _host, _port);

      addSupportHeaders();
      _headers = _method.getRequestHeaders();

      LOGGER.debug(
          "########## The real value set: {}", _method.getFollowRedirects());

      client.getHostConfiguration().setHost(_host, _port, protocol);

      client.executeMethod(_method);

      return new HttpResponse(_host, _port, _isSecure, _method, getState());
    } else {
      if (_isSecure) {
        method = "https";
        defaultPort = DEFAULT_SSL_PORT;
        factory = new SSLProtocolSocketFactory();
      } else {
        method = "http";
        defaultPort = DEFAULT_HTTP_PORT;
        factory = new DefaultProtocolSocketFactory();
      }

      Protocol protocol = new Protocol(method, factory, defaultPort);
      HttpConnection conn = new HttpConnection(_host, _port, protocol);

      if (conn.isOpen()) {
        throw new IllegalStateException("Connection incorrectly opened");
      }

      conn.open();

      LOGGER.info("Dispatching request: '{}' to target server at '{}:{}'", _requestLine, _host, _port);

      addSupportHeaders();
      _headers = _method.getRequestHeaders();

      LOGGER.debug("########## The real value set: {}", _method.getFollowRedirects());

      _method.execute(getState(), conn);

      return new HttpResponse(_host, _port, _isSecure, _method, getState());
    }
    */
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(255);
    sb.append("[REQUEST LINE] -> ").append(_requestLine).append('\n');

    for (Map.Entry<String,String> header : requestHeaders.entrySet()) {
        sb.append("[REQUEST HEADER] -> ");
        sb.append(header.getKey()).append(':').append(header.getValue()).append(System.lineSeparator());
    }


    if (content != null) {
      sb.append("[REQUEST BODY LENGTH] -> ").append(content.length());
      sb.append('\n');
    }

    return sb.toString();

  }

  public boolean isState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }

  /*
   * private methods
   * ========================================================================
   */

  private void createCookie(String cookieHeader) {
//    String cookieLine = cookieHeader.substring(cookieHeader.indexOf(':') + 1)
//        .trim();
//    StringTokenizer st = new StringTokenizer(cookieLine, " ;");
//    Cookie cookie = new Cookie();
//    cookie.setVersion(1);
//
//    getState();
//
//    if (cookieLine.indexOf("$Version") == -1) {
//      cookie.setVersion(0);
//      _method.getParams().setCookiePolicy(CookiePolicy.NETSCAPE);
//    }
//
//    while (st.hasMoreTokens()) {
//      String token = st.nextToken();
//
//      if (token.charAt(0) != '$' && !token.startsWith("Domain")
//          && !token.startsWith("Path")) {
//        cookie.setName(token.substring(0, token.indexOf('=')));
//        cookie.setValue(token.substring(token.indexOf('=') + 1));
//      } else if (token.indexOf("Domain") > -1) {
//        cookie.setDomainAttributeSpecified(true);
//        cookie.setDomain(token.substring(token.indexOf('=') + 1));
//      } else if (token.indexOf("Path") > -1) {
//        cookie.setPathAttributeSpecified(true);
//        cookie.setPath(token.substring(token.indexOf('=') + 1));
//      }
//    }
//    _state.addCookie(cookie);

  }

}
