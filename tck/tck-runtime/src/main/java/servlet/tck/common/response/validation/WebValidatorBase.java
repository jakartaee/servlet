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

package servlet.tck.common.response.validation;

import servlet.tck.common.client.handler.Handler;
import servlet.tck.common.client.handler.HandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.tck.common.request.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base abstract class for WebTestCase validation.
 */
public class WebValidatorBase implements ValidationStrategy {

  private static final Logger logger = LoggerFactory.getLogger(WebValidatorBase.class);

  /**
   * Used to detect 4xx class HTTP errors to allow fail fast situations when 4xx
   * errors are not expected.
   */
  protected static final char CLIENT_ERROR = '4';

  /**
   * Used to detect 5xx class HTTP errors to allows fail fast situations when
   * 5xx errors are not expected.
   */
  protected static final char SERVER_ERROR = '5';

  /**
   * This test case's HttpResponse
   */
  protected HttpResponse _res = null;

  /**
   * This test case's HttpRequest
   */
  protected HttpExchange _req = null;

  /**
   * The test case being validated
   */
  protected WebTestCase _case = null;

  /**
   * <tt>validate</tt> Will validate the response against the configured
   * TestCase.
   *
   *
   * <ul>
   * <li>Check the HTTP status-code</li>
   * <li>Check the HTTP reason-phrase</li>
   * <li>Check for expected headers</li>
   * <li>Check from unexpected headers</li>
   * <li>Check expected search strings</li>
   * <li>Check unexpected search strings</li>
   * <li>Check the goldenfile</li>
   * </ul>
   */
  public boolean validate(WebTestCase testCase) throws Exception {
    _res = testCase.getResponse();
    _req = testCase.getRequest();
    _case = testCase;

    // begin the check
    try {
      if (!checkStatusCode() || !checkReasonPhrase() || !checkExpectedHeaders()
          || !checkUnexpectedHeaders() || !checkSearchStrings()
          || !checkSearchStringsNoCase() || !checkUnorderedSearchStrings()
          || !checkUnexpectedSearchStrings()) {
        logger.error("Cannot validate response:" + testCase.getResponse());
        return false;
      }
    } catch (IOException ioe) {
      logger.error(" Unexpected Exception: " + ioe.getMessage(), ioe);
      return false;
    }
    return true;
  }

  /**
   * <code>checkStatusCode</code> will perform status code comparisons based on
   * the algorithm below
   * <ul>
   * <li>Check the HTTP status code</li>
   * <ul>
   * <li>
   * <p>
   * If status code is -1, then return true
   * </p>
   * </li>
   * <li>
   * <p>
   * If test case status code null and response 4xx, return failure, print
   * error; return false
   * </p>
   * </li>
   * <li>
   * <p>
   * If test case status code null and response 5xx, return failure include
   * response body; return false
   * <p>
   * </li>
   * <li>
   * <p>
   * If test case status code null, and response not 4xx or 5xx, return true
   * </p>
   * </li>
   * <li>
   * <p>
   * Test case status code not null, compare it with the response status code;
   * return true if equal
   * <p>
   * <li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   * @throws IOException
   *           if an IO error occurs during validation
   */
  protected boolean checkStatusCode() throws IOException {
    String sCode = _case.getStatusCode();
    String resCode = _res.getStatusCode();
    if ("-1".equals(sCode))
      return true;

    if (sCode == null && resCode.charAt(0) == CLIENT_ERROR) {
      logger.error("Test {} Unexpected {} received from target server!  Request path: {}"
              , _case.getName(), resCode, _req.getRequestPath());
      return false;
    }

    if (sCode == null && (resCode.charAt(0) == SERVER_ERROR)) {
      String resBody = _res.getResponseBodyAsRawString();
      logger.error( " Test {} Unexpected '{}' received from target server!\n " +
                      "Error response received from server:\n" +
                      "------------------------------------------------\n" +
                      " {}"
              , _case.getName(), resCode, (resBody != null ? resBody : "NO RESPONSE"));
      return false;
    }

    if (sCode == null) {
      return true;
    }

    /*
     * Take sCode as a comma separated list of status codes.
     *
     * If prefixed by "!" the response status code must not match any in the list.
     *
     * Otherwise matching any in the list is accepted.
     */

    boolean exclusions = sCode.charAt(0) == '!';
    String[] sCodes = exclusions ? sCode.substring(1).split(",") : sCode.split(",");

    if (exclusions) {
        for (String current : sCodes) {
            if (current.equals(resCode)) {
                logger.error(" Test {} Unexpected Status Code "
                    + "received from server.  Expected any value except '{}', received '{}'",
                        _case.getName(), sCode, resCode);
                return false;
            }
        }
    } else {
        boolean found = false;
        for (String current : sCodes) {
            if (current.equals(resCode)) {
                logger.debug(" Test {} Expected Status Code '{}' found in response line!",
                        _case.getName(), current);
                found = true;
                break;
            }
        }

        if (!found) {
            logger.error(" Test {} Status Code '{}' not found in response line!",
                    _case.getName(), sCode);

            return false;
        }
    }

    return true;
  }

  /**
   * <code>checkSearchStrings</code> will scan the response for the configured
   * strings that are to be expected in the response.
   * <ul>
   * <li>Check search strings</li>
   * <ul>
   * <li>
   * <p>
   * If list of Strings is null, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If list of Strings is not null, scan response body. If string is found,
   * return true, otherwise display error and return false.
   * </p>
   * </li>
   * </ul>
   * </ul>
   * <em>NOTE:</em> If there are multiple search strings, the search will be
   * performed as such to preserve the order. For example, if the list of search
   * strings contains two entities, the search for the second entity will be
   * started after the index if the first match.
   *
   * @return boolen result of check
   * @throws IOException
   *           if an IO error occurs during validation
   */
  protected boolean checkSearchStrings() throws Exception {
    List<String> list = _case.getSearchStrings();
    boolean found = true;
    if (list != null && !list.isEmpty()) {
      String responseBody = _res.getResponseBodyAsRawString();

      for (int i = 0, n = list.size(), startIdx = 0, bodyLength = responseBody
          .length(); i < n; i++) {

        // set the startIdx to the same value as the body length
        // and let the test fail (prevents index based runtime
        // exceptions).
        if (startIdx >= bodyLength) {
          startIdx = bodyLength;
        }

        String search = list.get(i);
        int searchIdx = responseBody.indexOf(search, startIdx);

        logger.debug(
            " Test {} Scanning response for search string: '{}' starting at index location: {}",
                _case.getName(), search, startIdx);
        if (searchIdx < 0) {
          found = false;
          String sb = " Test %s Unable to find the following " +
                  "search string in the server's response: \n'%s'\n at index: %s" +
                  "\n Server's response:\n" +
                  "-------------------------------------------\n" +
                  "%s" +
                  "\n-------------------------------------------\n";
          String result = String.format(sb, _case.getName(), search, startIdx, responseBody);
          logger.error(result);
          throw new Exception(result);
        }

        logger.debug(" Test {} Found search string: '{}' at index '{}' in the server's response",
                _case.getName(), search, searchIdx);
        // the new searchIdx is the old index plus the lenght of the
        // search string.
        startIdx = searchIdx + search.length();
      }
    }
    return found;
  }

  /**
   * <code>checkSearchStringsNoCase</code> will scan the response for the
   * configured case insensitive strings that are to be expected in the
   * response.
   * <ul>
   * <li>Check search strings</li>
   * <ul>
   * <li>
   * <p>
   * If list of Strings is null, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If list of Strings is not null, scan response body. If string is found,
   * return true, otherwise display error and return false.
   * </p>
   * </li>
   * </ul>
   * </ul>
   * <em>NOTE:</em> If there are multiple search strings, the search will be
   * performed as such to preserve the order. For example, if the list of search
   * strings contains two entities, the search for the second entity will be
   * started after the index if the first match.
   *
   * @return boolen result of check
   * @throws IOException
   *           if an IO error occurs during validation
   */
  protected boolean checkSearchStringsNoCase() throws Exception {
    List<String> list = _case.getSearchStringsNoCase();
    boolean found = true;
    if (list != null && !list.isEmpty()) {
      String responseBody = _res.getResponseBodyAsRawString();

      for (int i = 0, n = list.size(), startIdx = 0, bodyLength = responseBody
          .length(); i < n; i++) {

        // set the startIdx to the same value as the body length
        // and let the test fail (prevents index based runtime
        // exceptions).
        if (startIdx >= bodyLength) {
          startIdx = bodyLength;
        }

        String search = list.get(i);
        int searchIdx = responseBody.toLowerCase().indexOf(search.toLowerCase(),
            startIdx);

        logger.debug(
            " Test {} Scanning response for search string: '{}' starting at index location: {}",
                _case.getName(), search, startIdx);
        if (searchIdx < 0) {
          found = false;
          String sb = " Test %s Unable to find the following search string in the server's " +
                  "response: \n'%s'\n at index: %s" +
                  "\n Server's response:\n" +
                  "-------------------------------------------\n" +
                  "%s" +
                  "\n-------------------------------------------\n";
          String result = String.format(sb, _case.getName(), search, searchIdx, responseBody);
          logger.error(result);
          throw new Exception(result);
        }

        logger.debug(" Test {} Found search string: '{}' at index '{}' in the server's response",
                _case.getName(), search, searchIdx);
        // the new searchIdx is the old index plus the lenght of the
        // search string.
        startIdx = searchIdx + search.length();
      }
    }
    return found;
  }

  /**
   * <code>checkUnorderedSearchStrings</code> will scan the response for the
   * configured strings that are to be expected in the response.
   * <ul>
   * <li>Check search strings</li>
   * <ul>
   * <li>
   * <p>
   * If list of Strings is null, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If list of Strings is not null, scan response body. If string is found,
   * return true, otherwise display error and return false.
   * </p>
   * </li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   * @throws IOException
   *           if an IO error occurs during validation
   */
  protected boolean checkUnorderedSearchStrings() throws Exception {
    List<String> list = _case.getUnorderedSearchStrings();
    boolean found = true;
    if (list != null && !list.isEmpty()) {
      String responseBody = _res.getResponseBodyAsRawString();

      for (String search : list) {
        int searchIdx = responseBody.indexOf(search);
        logger.debug(" Test {} Scanning response for search string: '{}'...",
                _case.getName(), search);
        if (searchIdx < 0) {
          found = false;
          String sb = " Test %s Unable to find the following " +
                  "search string in the server's " +
                  "response: \n' %s" +
                  "\n Server's response:\n" +
                  "-------------------------------------------\n" +
                  "%s" +
                  "\n-------------------------------------------\n";
          String result = String.format(sb, _case.getName(), search, responseBody);
          logger.error(result);
          throw new Exception(result);
        }

        logger.debug(" Test {} Found search string: '{}' at index '{}' in the server's response",
                _case.getName(), search, searchIdx);
      }
    }
    return found;
  }

  /**
   * <code>checkUnexpectedSearchStrings</code> will scan the response for the
   * configured strings that are not expected in the response.
   * <ul>
   * <li>Check unexpected search strings</li>
   * <ul>
   * <li>
   * <p>
   * If list of Strings is null, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If list of Strings is not null, scan response body. If string is not found,
   * return true, otherwise display error and return false.
   * <p>
   * </li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   * @throws IOException
   *           if an IO error occurs during validation
   */
  protected boolean checkUnexpectedSearchStrings() throws IOException {
    List<String> list = _case.getUnexpectedSearchStrings();
    if (list != null && !list.isEmpty()) {
      String responseBody = _res.getResponseBodyAsRawString();
      for (String search : list) {
        logger.debug(" Test {} Scanning response. The following string should not be present in the response: '{}'",
                _case.getName(), search);
        if (responseBody.contains(search)) {
          String sb = " Test {} Found the following unexpected " +
                  "search string in the server's " +
                  "response: '{}'" +
                  "\n Server's response:\n" +
                  "-------------------------------------------\n" +
                  "{}}" +
                  "\n-------------------------------------------\n";
          logger.error(sb, _case.getName(), search, responseBody);
          return false;
        }
      }
    }
    return true;
  }

  /**
   * <code>checkReasonPhrase</code> will perform comparisons between the
   * configued reason-phrase and that of the response.
   * <ul>
   * <li>Check reason-phrase</li>
   * <ul>
   * <li>
   * <p>
   * If configured reason-phrase is null, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If configured reason-phrase is not null, compare the reason-phrases with
   * the response. If equal, return true, otherwise display error and return
   * false.
   * <p>
   * </li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   */
  protected boolean checkReasonPhrase() {
    String sReason = _case.getReasonPhrase();
    String resReason = _res.getReasonPhrase();

    if (sReason == null) {
      return true;
    }
    boolean check = sReason.equalsIgnoreCase(resReason);
    if (check) {
      logger.debug("Test {}, found expected reasonPhrase is '{}'",_case.getName(), resReason);
      return true;
    }
    logger.error("Test {}, reasonPhrase is '{}' but not '{}'", _case.getName(), resReason, sReason);
    return false;
  }

  /**
   * <code>checkExpectedHeaders</code> will check the response for the
   * configured expected headers.
   * <ul>
   * <li>Check expected headers</li>
   * <ul>
   * <li>
   * <p>
   * If there are no expected headers, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If there are expected headers, scan the response for the expected headers.
   * If all expected headers are found, return true, otherwise display an error
   * and return false.
   * <p>
   * </li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   */
  protected boolean checkExpectedHeaders() throws Exception {
    Header[] expected = _case.getExpectedHeaders();
    if (isEmpty(expected)) {
      return true;
    }
    boolean found = true;
    Header currentHeader = null;
    for (Header header : expected) {
      currentHeader = header;
      Optional<Header> resHeader = _res.getResponseHeader(currentHeader.getName());
      if (resHeader.isPresent()) {
        Handler handler = HandlerFactory.getHandler(currentHeader.getName());
        if (!handler.invoke(currentHeader, resHeader.get())) {
          found = false;
          break;
        }
      } else {
        found = false;
        break;
      }
    }
    if (!found) {
      StringBuilder sb = new StringBuilder(255);
      sb.append(" Test %s Unable to find the following header");
      sb.append(" in the server's response: ");
      sb.append("%s").append("\n");
      sb.append(" Response headers received from server:");

      List<Header> resHeaders = _res.getResponseHeaders();
      sb.append(resHeaders.stream()
              .map(header -> "ResponseHeader ->'" + header.getName() + "';'" + String.join(",", header.getValues()) + "'")
              .collect(Collectors.joining("\n\t")));

      sb.append("\n");
      String result = String.format(sb.toString(), _case.getName(), currentHeader);
      logger.error(result);
      throw new Exception(result);
    }
    logger.debug(" Test {} Found expected header: {}", _case.getName(), currentHeader.toString());
    return true;
  }

  /**
   * <code>checkUnexpectedHeaders</code> will check the response for the
   * configured unexpected expected headers.
   * <ul>
   * <li>Check unexpected headers</li>
   * <ul>
   * <li>
   * <p>
   * If there are no configured unexpected headers, return true.
   * </p>
   * </li>
   * <li>
   * <p>
   * If there are configured unexpected headers, scan the response for the
   * unexpected headers. If the headers are not found, return true, otherwise
   * display an error and return false.
   * <p>
   * </li>
   * </ul>
   * </ul>
   *
   * @return boolen result of check
   */
  protected boolean checkUnexpectedHeaders() {
    Header[] unexpected = _case.getUnexpectedHeaders();
    if (isEmpty(unexpected)) {
      return true;
    } else {
      for (Header currentHeader : unexpected) {
        String currName = currentHeader.getName();
        String currValue = currentHeader.getValue();
        Optional<Header> resHeader = _res.getResponseHeader(currName);
        if (resHeader.isPresent()) {
          if (resHeader.get().getValue().equals(currValue)) {
            StringBuilder sb = new StringBuilder(255);
            sb.append(" Test {} Unexpected header found in the ");
            sb.append("server's response: ");
            sb.append("{}").append("\n");
            sb.append(" Response headers recieved from");
            sb.append("server:");

            List<Header> resHeaders = _res.getResponseHeaders();
            sb.append(resHeaders.stream()
                    .map(header -> "ResponseHeader ->" + header.getName() + ":" + String.join(",", header.getValues()))
                    .collect(Collectors.joining("\n\t")));
            logger.error(sb.toString(), _case.getName(), currentHeader);

            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Utility method to determine of the expected or unexpected headers are empty
   * or not.
   */
  protected boolean isEmpty(Header[] headers) {
    return headers == null || headers.length == 0;
  }
}
