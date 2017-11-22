package se.ugli.pecan.runtime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.pecan.Pecan;
import se.ugli.pecan.source.WebjarsNpmDependencyRespository;

public class NashornRuntimeTest {

    @Test
    public void test() throws Exception {
        final byte[] babelBytes = new WebjarsNpmDependencyRespository().findSource().get();
        assertThat(new Pecan(new NashornRuntime(), babelBytes).transform("<p>Hej, <strong>va' bli're</strong>?</p>"),
                is("React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

}
