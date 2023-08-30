package servlet.tck.common.util;

import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.InvocationException;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to load TCK Arquillian extension
 */
public class TCKLoadableExtension implements LoadableExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(TCKLoadableExtension.class);

    @Override
    public void register(ExtensionBuilder extensionBuilder) {
        extensionBuilder.observer(TCKDeploymentExceptionObserver.class);
    }


    public static class TCKDeploymentExceptionObserver {

        /**
         * this will fail the build when Arquillian deployment fail
         */
        public void invoke(@Observes DeploymentException e) {
            LOGGER.error("Fail to deploy: " + e.getMessage(), e);
        }

        public void invoke(@Observes InvocationException e) {
            LOGGER.error("Fail to deploy: " + e.getMessage(), e);
        }

    }


}
