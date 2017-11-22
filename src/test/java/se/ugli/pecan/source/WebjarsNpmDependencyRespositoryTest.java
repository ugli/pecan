package se.ugli.pecan.source;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class WebjarsNpmDependencyRespositoryTest {

    @Test
    public void test() {
        final Optional<byte[]> optional = new WebjarsNpmDependencyRespository().findSource();
        assertThat(optional.isPresent(), is(true));
    }

}
