package dev.dubhe.cbapi.resources;

import dev.dubhe.cbapi.ChatServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Resources {
    public static final Path ASSETS = ChatServer.ROOT.resolve("assets");
    public static final Path RESOURCES_PACK = ChatServer.ROOT.resolve("resources_pack");

    public static void load() {
        File resPack = RESOURCES_PACK.toFile();
        if (resPack.isDirectory()) resPack.mkdir();
        File[] resPacks = resPack.listFiles((file) -> file.getName().endsWith(".zip"));
        if (null != resPacks) for (File file : resPacks) {
            try (ZipFile zipFile = new ZipFile(file)) {
                Resources.load(zipFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load(ZipFile file) {
        for (Iterator<? extends ZipEntry> it = file.entries().asIterator(); it.hasNext(); ) {
            ZipEntry entry = it.next();
            if (entry.getName().equals("assets") && entry.isDirectory()) {
                File assetsDir = ASSETS.toFile();
                if (assetsDir.isDirectory()) assetsDir.mkdir();
                try (FileOutputStream stream = new FileOutputStream(assetsDir)) {
                    stream.write(file.getInputStream(entry).readAllBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
