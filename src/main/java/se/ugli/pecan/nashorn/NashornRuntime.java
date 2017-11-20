package se.ugli.pecan.nashorn;

import java.io.StringReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import se.ugli.pecan.JsRuntime;

class NashornRuntime implements JsRuntime {

    final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    final SimpleBindings bindings = new SimpleBindings();

    @Override
    public void loadScript(byte[] script) {
        try {
            engine.eval(new StringReader(new String(script)), bindings);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String eval(String script) {
        try {
            return (String) engine.eval(script, bindings);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

}
