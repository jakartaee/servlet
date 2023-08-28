<%--

    Copyright (c) 2006, 2020 Oracle and/or its affiliates. All rights reserved.

    This program and the accompanying materials are made available under the
    terms of the Eclipse Public License v. 2.0, which is available at
    http://www.eclipse.org/legal/epl-2.0.

    This Source Code may also be made available under the following Secondary
    Licenses when the conditions for such availability set forth in the
    Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
    version 2 with the GNU Classpath Exception, which is available at
    https://www.gnu.org/software/classpath/license.html.

    SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

--%>

<%@ page import="com.sun.ts.tests.jsp.common.util.JspTestUtil,
                 java.io.IOException,
                 jakarta.servlet.http.* " %>
<%@ page contentType="text/plain" %>

<%-- Begin test definitions --%>

<%!
    public void includeJSPServlet(HttpServletRequest req,
                                 HttpServletResponse res,
                                 JspWriter out)
    throws ServletException, IOException {
        String path_context = "/servlet_spec_crosscontext2_web";
        String path_servlet = "/include/IncludedServlet?testname=session";
        HttpSession ses = null;
        boolean pass = false;
        RequestDispatcher rd = null;

        ses = req.getSession(true);
        if (ses != null) {
            ServletContext context = getServletConfig().getServletContext().getContext(path_context);
            if ( context !=null ) {
                rd = context.getRequestDispatcher(path_servlet);

                if (rd != null) {
                    ses.setAttribute("crosscontext_cts", "Special");
                    rd.include(req, res);

                    ses = req.getSession();
                    if (ses != null) {
                        String tmp  = (String)ses.getAttribute("crosscontext_cts");
                        String tmp1 = (String)ses.getAttribute("crosscontext_cts.include");
                        if ( tmp != null ) 
                            if ( tmp.equals("Special") ) 
                                if ( tmp1 == null )
                                    pass = true;
                    
                        if ( pass != true ) {
                            out.println("Expect attribute crosscontext_cts set with Special");
                            out.println("Got attribute crosscontext_cts set with " + tmp);
                            out.println("Expect attribute crosscontext_cts.include set with PASS");
                            out.println("Got attribute crosscontext_cts.include set with " + tmp1 );
                        } else {
                            out.println("Include Test PASSED");
                        }
                    } else {
                        out.println("Cannot get a HttpSession after RequestDispatcher returns");
                        pass = true;
                    }
                }  else {
                    out.println("Null RequestDispatcher got for path=" + path_context + path_servlet);
                    pass = true;
                }
            } else {
                out.println("Null ServletContext got for path=" + path_context );
                pass = true;
            }
        } else {
            out.println("Cannot get a HttpSession");
            pass = true;
        }
        if ( pass )
            out.println("Test PASSED");
        else
            out.println("Test FAILED");
    }
%>


<%!
    public void forwardJSPServlet(HttpServletRequest req,
                                 HttpServletResponse res,
                                 JspWriter out)
    throws ServletException, IOException {
        String path_context = "/servlet_spec_crosscontext2_web";
        String path_servlet = "/forward/ForwardedServlet?testname=session";
        HttpSession ses = null;
        boolean pass = false;
        RequestDispatcher rd = null;


        ses = req.getSession(true);
        if (ses != null) {
            ServletContext context = getServletConfig().getServletContext().getContext(path_context);
            if ( context !=null ) {
                rd = context.getRequestDispatcher(path_servlet);

                if (rd != null) {
                    ses.setAttribute("crosscontext_cts", "Special");

                    rd.forward(req, res);

                }  else {
                    out.println("Null RequestDispatcher got for path=" + path_context + path_servlet);
                    pass=true;
                }
            } else {
                out.println("Null Servlet Context");
                pass = true;
            }
        } else {
            out.println("Cannot get a HttpSession");
            pass = true;
        }
        if ( pass )
            out.println("Test PASSED");
        else
            out.println("Test FAILED");
    }
%>

<% JspTestUtil.invokeTest(this, request, response, out); %>
