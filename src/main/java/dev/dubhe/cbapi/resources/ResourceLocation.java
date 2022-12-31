package dev.dubhe.cbapi.resources;

public class ResourceLocation {
    private final String namespace;
    private final String location;

    public ResourceLocation(String location) {
        this.namespace = "cbapi";
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
