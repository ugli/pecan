package se.ugli.pecan.nashorn;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import se.ugli.pecan.Pecan;

public class NashornRuntimeTest {

    @Test
    public void test() throws Exception {
        final byte[] babelBytes = Files.readAllBytes(
                Paths.get(NashornRuntimeTest.class.getResource("/babel-standalone-min-6.26.0.js").toURI()));
        assertThat(new Pecan(new NashornRuntime(), babelBytes).transform("<p>Hej, <strong>va' bli're</strong>?</p>"),
                is("React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

}
