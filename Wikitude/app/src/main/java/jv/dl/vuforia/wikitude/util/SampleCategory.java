package jv.dl.vuforia.wikitude.util;

public class SampleCategory {

    private final String name;
    private final String[] samples;

    public SampleCategory(String name, String[] samples) {
        this.name = name;
        this.samples = samples;
    }

    public String getName() {
        return name;
    }

    public String[] getSamples() {
        return samples;
    }
}
