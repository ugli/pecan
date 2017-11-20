package se.ugli.pecan;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PecanTest {

    Pecan pecan = Pecan.create();

    @Test
    public void test() throws Exception {
        assertThat(pecan.transform("<p>Hej, <strong>va' bli're</strong>?</p>"), is(
                "React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

}
