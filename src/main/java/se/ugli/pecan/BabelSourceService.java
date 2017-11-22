package se.ugli.pecan;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;

import java.util.List;
import java.util.Optional;

import se.ugli.pecan.source.WebjarsNpmDependencyRespository;

class BabelSourceService {

    // TODO implement PropertySourceRepository
    private final List<BabelSourceRepository> repositories = asList(new WebjarsNpmDependencyRespository());

    Optional<byte[]> getSource() {
        for (final BabelSourceRepository repository : repositories) {
            final Optional<byte[]> source = repository.findSource();
            if (source.isPresent()) {
                return source;
            }
        }
        return empty();
    }

}
