///////////////////////////////////////////////////////////////////////
NOTE TO WRITERS:
The following sections should be customized for the technology.
This text was originally from the JACC TCK.  Some references
to JACC have been parameterized to serve as a simple starting
point for customization.  There are still many details that will
need to be changed or removed.
///////////////////////////////////////////////////////////////////////

[[GBFHA]][[troubleshooting-tips]]

6.7 Troubleshooting Tips
~~~~~~~~~~~~~~~~~~~~~~~~

This section provides some tips for troubleshooting errors that may be
encountered.

* Verify that the Ant `config.vi` and `enable.jacc` configuration
targets executed correctly.
* If there are several failures during a test run, check the various
output for hints about what caused the failures. A common problem is the
absence of the {TechnologyShortName} log file. This log file should be created in the
directory defined by the `log.file.location` property in the `ts.jte`
file. There should be a log file called `JACCLog.txt` in this directory.
The `JACCLog.txt` consists of record entries containing permission
infomation that will be used to verify the TCK tests for proper
compliance. This log file typically gets populated with
{TechnologyShortName}-based security information when test archives are
deployed. Then, during test execution, the `JACCLog.txt` file is read
and used for validating that {TechnologyShortName} behavior is
correct.
* Simultaneously deploying all {TechnologyShortName} TCK test archives
may cause false failures. If unexpected failures occur during a TCK run
when all {TechnologyShortName} archives were deployed, these failures
could be caused by interference from tests and archives that are
defined multiple times. If such situational failures do occur, undeploy
all archives, remove the `JACCLog.txt` file, recycle your server (if
necessary), and rerun only the tests in the directory that showed
failures.
* Check that the following JVM variables, which should have been set by
invoking the `enable.jacc` Ant target, have been set in the application
server :

** `-Dlog.file.location` (this comes from the +{jteFileName}+ property)

** `-Djakarta.security.jacc.policy.provider=com.sun.ts.tests.jacc.provider.TSPolicy`

** `-Djakarta.security.jacc.PolicyConfigurationFactory.provider=com.sun.ts.tests.jacc.provider.TSPolicyConfigurationFactoryImpl`

** `-Dvendor.jakarta.security.jacc.policy.provider=com.sun.enterprise.security.jacc.provider.SimplePolicyProvider`

** `-Dvendor.jakarta.security.jacc.PolicyConfigurationFactory.provider=com.sun.enterprise.security.jacc.provider.SimplePolicyConfigurationFactory` +

[NOTE]
=======================================================================

The values for the `-Dvendor.jakarta.security.jacc.policy.provider` and
`-Dvendor.jakarta.security.jacc.PolicyConfigurationFactory.provider` JVM
variables will need to be set specific to the application server in use.

=======================================================================



