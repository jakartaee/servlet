/*
*
* The Apache Software License, Version 1.1
*
* Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
* Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
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
* 3. The end-user documentation included with the redistribution, if
*    any, must include the following acknowlegement:
*       "This product includes software developed by the
*        Apache Software Foundation (http://www.apache.org/)."
*    Alternately, this acknowlegement may appear in the software itself,
*    if and wherever such third-party acknowlegements normally appear.
*
* 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
*    Foundation" must not be used to endorse or promote products derived
*    from this software without prior written permission. For written
*    permission, please contact apache@apache.org.
*
* 5. Products derived from this software may not be called "Apache"
*    nor may "Apache" appear in their names without prior written
*    permission of the Apache Group.
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
*/

package servlet.tck.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class StaticLog {

  // each webapp running in the same JVM needs its own log
  // required when the TCK runs parallel test classes against an embedded container.
  // WeakHashMap so undeployed webapps be collected once their classloader is.
  private static final Map<ClassLoader, List<String>> LOGS =
      Collections.synchronizedMap(new WeakHashMap<>());

  private static List<String> log() {
    ClassLoader key = Thread.currentThread().getContextClassLoader();
    synchronized (LOGS) {
        List<String> list = LOGS.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>()));
        return list;
    }
  }

  public static void clear() {
    List<String> al = log();
    al.clear();
      // System.out.println("log is clear");

  }

  public static void add(String s) {
    // System.out.println("Adding the following item to the log:"+s);
    log().add(s);
  }

  // FIXME this naming looks really wrong as it is not clearing the list
  // but returning a List with the same content as the log.
  // The caller can then clear the log by clearing the returned list.
  public static ArrayList<String> getClear() {
    List<String> al = log();
    /*
     * Object[] o = al.toArray(); for (int i = 0;i<o.length;i++){
     * System.out.println("al - o["+i+"]="+(String)o[i]); }
     */
    // Create a new list which will be returned so that the old list can be
    // cleared
    ArrayList<String> tmp;
    synchronized (al) {
      tmp = new ArrayList<>(al);
    }
    /*
     * Object[] o1 = tmp.toArray(); for (int i = 0;i<o1.length;i++){
     * System.out.println("tmp - o1["+i+"]="+(String)o1[i]); }
     */
    // clear();
    return tmp;
  }

  public static List<String> get() {
    return log();
  }
}
