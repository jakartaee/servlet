#!/usr/bin/env bash
#
# Copyright (c) 2024  Contributors to the Eclipse Foundation
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

##script to install the artifacts directory contents into a local maven repository

if [[ $1 =~ ^[0-9]+\.[0-9]+\.[0-9]+.*$ ]]; then
  VERSION="$1"
else
  VERSION="6.1.0"
fi

JAKARTAEE_VERSION="11.0.0-M1"

# servlet-tck pom
mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file  \
-Dfile=servlet-tck-$VERSION.pom -DgroupId=jakarta.tck -DartifactId=servlet-tck \
-Dversion=$VERSION -Dpackaging=pom

# servlet-tck-runtime jar
mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file  \
-Dfile=servlet-tck-runtime-$VERSION.jar -DgroupId=jakarta.tck -DartifactId=servlet-tck-runtime \
-Dversion=$VERSION -Dpackaging=jar

# servlet-tck-util jar
mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file  \
-Dfile=servlet-tck-util-$VERSION.jar -DgroupId=jakarta.tck -DartifactId=servlet-tck-util \
-Dversion=$VERSION -Dpackaging=jar
