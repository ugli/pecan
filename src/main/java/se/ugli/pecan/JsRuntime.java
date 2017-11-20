package se.ugli.pecan;

public interface JsRuntime {

    void loadScript(byte[] script);

    String eval(String script);

}
