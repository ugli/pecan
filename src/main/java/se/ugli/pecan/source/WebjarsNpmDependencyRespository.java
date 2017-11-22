package se.ugli.pecan.source;

import static java.util.Optional.empty;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.ugli.pecan.BabelSourceRepository;
import se.ugli.pecan.PecanException;
import sun.misc.IOUtils;

public class WebjarsNpmDependencyRespository implements BabelSourceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(WebjarsNpmDependencyRespository.class);

    @Override
    public Optional<byte[]> findSource() {
        try {
            final InputStream pomPropStream = getClass()
                    .getResourceAsStream("/META-INF/maven/org.webjars.npm/babel-standalone/pom.properties");
            if (pomPropStream == null) {
                return empty();
            }
            final Properties pomProperties = new Properties();
            pomProperties.load(pomPropStream);
            LOG.info("pom.properties: {}", pomProperties);
            final String version = pomProperties.getProperty("version");
            final String srcResourcePath = "/META-INF/resources/webjars/babel-standalone/" + version + "/babel.min.js";
            final InputStream srcResource = getClass().getResourceAsStream(srcResourcePath);
            if (srcResource == null) {
                LOG.warn("Source not found: {}", srcResourcePath);
                return empty();
            }
            LOG.info("Source found: {}", srcResourcePath);
            return Optional.of(IOUtils.readFully(srcResource, -1, true));
        } catch (final IOException e) {
            throw new PecanException(e);
        }
    }

}
