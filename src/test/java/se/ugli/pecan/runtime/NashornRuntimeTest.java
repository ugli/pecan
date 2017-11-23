package se.ugli.pecan.runtime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import se.ugli.java.io.Resource;
import se.ugli.pecan.Pecan;
import se.ugli.pecan.source.WebjarsNpmDependencyRespository;

public class NashornRuntimeTest {

    @Test
    public void test() throws Exception {
        final byte[] babelBytes = new WebjarsNpmDependencyRespository().findSource().get();
        assertThat(new Pecan(new NashornRuntime(), babelBytes).transform("<p>Hej, <strong>va' bli're</strong>?</p>"),
                is("React.createElement(\n  \"p\",\n  null,\n  \"Hej, \",\n  React.createElement(\n    \"strong\",\n    null,\n    \"va' bli're\"\n  ),\n  \"?\"\n);"));
    }

    @Test
    public void test2() throws Exception {
        final byte[] babelBytes = new WebjarsNpmDependencyRespository().findSource().get();
        final Pecan pecan = new Pecan(new NashornRuntime(), babelBytes);
        assertEquals(
                "React.render(React.createElement(\n" + "  'p',\n" + "  null,\n" + "  'Hej, ',\n"
                        + "  React.createElement(\n" + "    'strong',\n" + "    null,\n" + "    'va\\' bli\\'re'\n"
                        + "  ),\n" + "  '?'\n" + "), document.getElementById('root'));",
                pecan.transform(Resource.apply("/index.jsx").asString()));

    }

}
