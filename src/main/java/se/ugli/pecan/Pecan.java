package se.ugli.pecan;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pecan {

    private static final Logger LOG = LoggerFactory.getLogger(Pecan.class);
    private static final List<String> RUNTIMES = asList("se.ugli.pecan.runtime.J2v8Runtime",
            "se.ugli.pecan.runtime.NashornRuntime");

    private final JsRuntime runtime;

    public Pecan(final JsRuntime runtime, final byte[] babelScript) {
        this.runtime = runtime;
        runtime.loadScript(babelScript);
    }

    public static Pecan create() {
        return new Pecan(getRuntime(), babelScript());
    }

    private static byte[] babelScript() {
        final Optional<byte[]> source = new BabelSourceService().getSource();
        if (source.isPresent())
            return source.get();
        throw new PecanException("No available Babel Script");
    }

    private static JsRuntime getRuntime() {
        for (final String runtime : RUNTIMES) {
            final Optional<JsRuntime> jsRuntime = createRuntime(runtime);
            if (jsRuntime.isPresent())
                return jsRuntime.get();
        }
        throw new PecanException("No available runtime: " + RUNTIMES);
    }

    private static Optional<JsRuntime> createRuntime(final String runtime) {
        try {
            return Optional.of((JsRuntime) Class.forName(runtime).getDeclaredConstructor().newInstance());
        }
        catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            LOG.info("Couldn't load runtime: " + runtime);
            return empty();
        }
    }

    public String transform(final String jsx) {
        final StringBuilder script = new StringBuilder();
        script.append("Babel.transform('");
        script.append(replaceSpecChars(jsx));
        script.append("', { presets: ['react'] }).code");
        System.out.println(script);
        return runtime.eval(script.toString());
    }

    private String replaceSpecChars(final String jsx) {
        return jsx.replace("'", "\\x27").replaceAll("\n", " ").replaceAll("\r", " ");
    }

}
