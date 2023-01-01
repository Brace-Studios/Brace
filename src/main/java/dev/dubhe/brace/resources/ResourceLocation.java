package dev.dubhe.brace.resources;

public class ResourceLocation {
    private final String namespace;
    private final String location;

    public ResourceLocation(String location) {
        this.namespace = "brace";
        this.location = location;
    }

    public ResourceLocation(String namespace, String location) {
        this.namespace = namespace;
        this.location = location;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getLocation() {
        return location;
    }
}
