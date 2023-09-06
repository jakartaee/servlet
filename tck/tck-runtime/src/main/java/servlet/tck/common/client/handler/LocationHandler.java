/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * Portions of this software are based upon public domain software
 * originally written at the National Center for Supercomputing Applications,
 * University of Illinois, Urbana-Champaign.
 */

package servlet.tck.common.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.tck.common.request.Header;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <PRE>
 * Will handle headers for the following
 * cases:
 *   - Server is on port 80 and port value isn't
 *     propagated back to client (assumed)
 *   - Port value is in response
 * </PRE>
 */
public class LocationHandler implements Handler {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(LocationHandler.class);

  private static Handler handler = new LocationHandler();

  /**
   * Creates new ContentTypeHandler
   */
  private LocationHandler() {
  }

  /*
   * public methods
   * ========================================================================
   */

  /**
   * Returns an instance of this handler.
   */
  public static Handler getInstance() {
    return handler;
  }

  /**
   * Invokes handler logic.
   * 
   * @param configuredHeader
   *          the user configured header
   * @param responseHeader
   *          the response header from the server
   * @return True if the passed match, otherwise false
   */
  public boolean invoke(Header configuredHeader, Header responseHeader) {

    boolean pass = true;

    try {
      LOGGER.trace("[LocationHandler] LocationHandler invoked.");

      URL configURL = new URL(configuredHeader.getValue());
      URL responseURL = new URL(responseHeader.getValue());

      if (!(configURL.getProtocol().equals(responseURL.getProtocol()))) {
        pass = false;
        LOGGER.error("[LocationHandler] Mismatch between protocols:");
        LOGGER.error(
            "[LocationHandler] Configured value: {}", configURL.getProtocol());
        LOGGER.error(
            "[LocationHandler] Response value: {}", responseURL.getProtocol());
      }

      if (!(configURL.getPath().equals(responseURL.getPath()))) {
        pass = false;
        LOGGER.error("[LocationHandler] Mismatch between paths:");
        LOGGER.error(
            "[LocationHandler] Configured value: {}", configURL.getPath());
        LOGGER.error(
            "[LocationHandler] Response value: {}", responseURL.getPath());
      }

      if (configURL.getQuery() == null) {
        if (responseURL.getQuery() != null) {
          pass = false;
          LOGGER.error("[LocationHandler] Mismatch between querys:");
          LOGGER.error("[LocationHandler] Configured value is null");
          LOGGER.error("[LocationHandler] Response value is non-null");
        }
      } else if (!(configURL.getQuery().equals(responseURL.getQuery()))) {
        pass = false;
        LOGGER.error("[LocationHandler] Mismatch between querys:");
        LOGGER.error(
            "[LocationHandler] Configured value: {}", configURL.getQuery());
        LOGGER.error(
            "[LocationHandler] Response value: {}", responseURL.getQuery());
      }

    } catch (MalformedURLException mue) {
      pass = false;
      LOGGER.error("[LocationHandler] MalformedURLException", mue);
    }

    return pass;
  }
}
