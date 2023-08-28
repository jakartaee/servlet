<%--

    Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.

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

<%@ page language="java" %>
<html>
<head><title> A simple Error Page</title></head>
<body>
<h2>A simple Error Page</h2>
<hr>
You could not be authenticated with the information provided. <BR>
Please check your Username and Password.
<hr>
<%
if ( request.getUserPrincipal() == null )
    out.println("The user principal is: " + request.getUserPrincipal() + "<BR>");
else
    out.println("The user principal is: " + request.getUserPrincipal().getName() + "<BR>");
%>
<br>
</body>
</html>



