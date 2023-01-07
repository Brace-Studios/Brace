package dev.dubhe.brace.utils.chat;

import dev.dubhe.brace.utils.image.BraceImage;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageComponent extends BaseComponent {
    private final byte[] image;

    public ImageComponent(InputStream imageStream) {
        byte[] image = new byte[0];
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = imageStream.read(buffer)) != -1) {
                byte[] newBuf = new byte[image.length + len];
                System.arraycopy(image, 0, newBuf, 0, image.length);
                System.arraycopy(buffer, 0, newBuf, image.length, len);
                image = newBuf;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imageStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.image = image;
    }

    public ImageComponent(byte[] image) {
        this.image = image;
    }

    public ImageComponent(BraceImage image) throws IOException {
        BufferedImage im;
        if (image.getImage() instanceof BufferedImage bf) {
            im = bf;
        } else {
            im = new BufferedImage(image.getWidth(), image.getHeight(), image.getMode().getMode());
            Graphics2D g = im.createGraphics();
            g.drawImage(image.getImage(), 0, 0, null);
            g.dispose();
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(im, "png", out);
            this.image = out.toByteArray();
        }
    }

    public byte[] getImage() {
        return image;
    }

    @NotNull
    @Override
    public String getString() {
        return new TranslatableComponent("brace.filetype.image").getString();
    }
}
