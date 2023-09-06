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

import java.util.Collections;
import java.util.List;

/**
 * <PRE>
 * The default handler will handle any
 * header that doesn't have a configured handler.
 * </PRE>
 */
public class DefaultHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHandler.class);

  private static Handler handler = new DefaultHandler();

  /**
   * Creates new DefaultHandler
   */
  private DefaultHandler() {
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

    LOGGER.trace("[DefaulHandler] DefaultHandler invoked.");

    return areHeadersEqual(configuredHeader, responseHeader);
  }

  /**
   * Utility method to determine equality of two Header objects
   * 
   * @param h1
   *          first header
   * @param h2
   *          second header
   * @return true if the headers are equal, otherwise false
   */
  protected boolean areHeadersEqual(Header h1, Header h2) {

    List<String> h1Values = h1.getValues();
    List<String> h2Values = h2.getValues();

    Collections.sort(h1Values);
    Collections.sort(h2Values);

    if (h1Values.size() == h2Values.size()) {
      for (String h1Value : h1Values) {
        boolean found = false;
        for (String h2Value : h2Values) {
          if (h1Value.equals(h2Value)) {
            found = true;
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }
}
