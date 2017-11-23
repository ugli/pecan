package se.ugli.pecan;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.java.io.Resource;

public class PecanTest {

    Pecan pecan = Pecan.create();

    @Test
    public void test() throws Exception {
        assertThat(pecan.transform("<p>Hej, <strong>va' bli're</strong>?</p>"), is(
                "React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

    @Test
    public void test2() throws Exception {
        assertEquals(
                "React.render(React.createElement(\n" + "  'p',\n" + "  null,\n" + "  'Hej, ',\n"
                        + "  React.createElement(\n" + "    'strong',\n" + "    null,\n" + "    'va\\' bli\\'re'\n"
                        + "  ),\n" + "  '?'\n" + "), document.getElementById('root'));",
                pecan.transform(Resource.apply("/index.jsx").asString()));
    }
}
