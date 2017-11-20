package se.ugli.pecan.j2v8;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import se.ugli.pecan.Pecan;
import se.ugli.pecan.j2v8.J2v8Runtime;

public class J2v8RuntimeTest {

    @Test
    public void test() throws Exception {
        final byte[] babelBytes = Files
                .readAllBytes(Paths.get(J2v8RuntimeTest.class.getResource("/babel-standalone-min-6.26.0.js").toURI()));
        assertThat(new Pecan(new J2v8Runtime(), babelBytes).transform("<p>Hej, <strong>va' bli're</strong>?</p>"), is(
                "React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

}
