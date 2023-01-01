package dev.dubhe.brace.plugins;

import dev.dubhe.brace.BraceServer;

public class PluginClassLoader extends ClassLoader {
    public PluginClassLoader(ClassLoader parent) {
        super(parent);
    }

    public final Class<?> defineClazz(String name, byte[] b, int off, int len) throws ClassFormatError {
        return super.defineClass(name, b, off, len);
    }

    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        for (PluginFile value : BraceServer.getPluginManager().PLUGIN_FILES.values()) {
            if (value.classMap.containsKey(name)) return value.classMap.get(name);
        }
        throw new ClassNotFoundException();
    }
}
