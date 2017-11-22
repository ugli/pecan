package se.ugli.pecan;

import java.util.Optional;

public interface BabelSourceRepository {

    Optional<byte[]> findSource();

}
