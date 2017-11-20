package se.ugli.pecan.j2v8;

import com.eclipsesource.v8.V8;

import se.ugli.pecan.JsRuntime;

public class J2v8Runtime implements JsRuntime {

    final V8 runtime = V8.createV8Runtime();

    @Override
    public void loadScript(byte[] script) {
        runtime.executeScript(new String(script));
    }

    @Override
    public String eval(String script) {
        return runtime.executeStringScript(script);
    }

}
