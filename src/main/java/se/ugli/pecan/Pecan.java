package se.ugli.pecan;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Pecan {

    private static final List<String> RUNTIMES = Arrays.asList("se.ugli.pecan.j2v8.J2v8Runtime",
            "se.ugli.pecan.nashorn.NashornRuntime");

    private final JsRuntime runtime;

    public Pecan(JsRuntime runtime, byte[] babelScript) {
        this.runtime = runtime;
        runtime.loadScript(babelScript);
    }

    public static Pecan create() {
        return new Pecan(getRuntime(), babelScript());
    }

    private static byte[] babelScript() {
        try {
            return Files.readAllBytes(Paths.get(Pecan.class.getResource("/babel-standalone-min-6.26.0.js").toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new PecanException(e);
        }
    }

    private static JsRuntime getRuntime() {
        for (final String runtime : RUNTIMES) {
            final JsRuntime jsRuntime = createRuntime(runtime);
            if (jsRuntime != null) {
                return jsRuntime;
            }
        }
        throw new PecanException("No available runtime: " + RUNTIMES);
    }

    private static JsRuntime createRuntime(final String runtime) {
        try {
            return (JsRuntime) Class.forName(runtime).getDeclaredConstructor().newInstance();
        } catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            return null;
        }
    }

    public String transform(String jsx) {
        final StringBuilder script = new StringBuilder();
        script.append("Babel.transform('");
        script.append(replaceSpecChars(jsx));
        script.append("', { presets: ['react'] }).code");
        return runtime.eval(script.toString());
    }

    private String replaceSpecChars(String jsx) {
        return jsx.replace("'", "\\x27");
    }

}
