package dev.dubhe.brace.utils.image;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;

public class BraceImage {
    private Image image;
    private final int width;
    private final int height;
    private final Mode mode;

    public BraceImage(int width, int height, Color color, Mode mode) {
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.image = BraceImage.newImage(width, height, color, mode);
    }

    private static BufferedImage newImage(int width, int height, Color color, Mode mode) {
        BufferedImage image;
        if (mode == Mode.RGBA) image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        else image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        return image;
    }

    /**
     * 图像缩放
     *
     * @param ratio 比例
     */
    public void resize(double ratio) {
        this.image = image.getScaledInstance((int) (this.width * ratio), (int) (this.height * ratio), Image.SCALE_DEFAULT);
    }

    /**
     * 图像缩放
     *
     * @param width  宽
     * @param height 高
     */
    public void resize(int width, int height) {
        this.image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    /**
     * 图像裁切
     *
     * @param range 范围
     */
    public void crop(Range range) {
        if (this.image instanceof BufferedImage im) {
            BufferedImage bf = new BufferedImage(range.x2 - range.x1, range.y2 - range.y1, im.getType());
            Graphics2D g = bf.createGraphics();
            g.drawImage(im, range.x1, range.y1, range.x2 - range.x1, range.y2 - range.y1, null);
            g.dispose();
            this.image = bf;
        }
    }

    /**
     * 粘贴图片
     *
     * @param image 图片
     * @param pos   坐标
     */
    public void paste(BraceImage image, Pos pos) {
        if (this.image instanceof BufferedImage im) {
            Graphics2D g = im.createGraphics();
            g.drawImage(image.image, pos.x, pos.y, null);
            g.dispose();
            this.image = im;
        }
    }

    /**
     * 写文本
     *
     * @param text  文本
     * @param pos   坐标
     * @param font  字体
     * @param color 颜色
     * @param align 对齐方式
     */
    public void text(String text, Pos pos, Font font, Color color, TextAlign align) {
        if (this.image instanceof BufferedImage im) {
            Graphics2D g = im.createGraphics();
            g.setFont(font);
            g.setColor(color.toAwtColor());
            FontMetrics fm2 = g.getFontMetrics();
            Rectangle2D rec2 = fm2.getStringBounds(text, g);
            int cx = (int) Math.ceil(rec2.getWidth());
            int x = pos.x, y = pos.y;
            if (align == TextAlign.CENTER) {
                x = pos.x - (int) (cx / 2.0f);
                y = pos.y - (int) (height / 2.0f);
            } else if (align == TextAlign.LEFT) {
                y = pos.y - (int) (height / 2.0f);
            } else if (align == TextAlign.RIGHT) {
                x = pos.x - (int) ((cx + cx) / 2.0f);
                y = pos.y - (int) (height / 2.0f);
            }
            try {
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontMetrics fm = g.getFontMetrics();
                g.drawString(text, x, y + fm.getAscent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            g.dispose();
        }
    }

    /**
     * 将某一部分进行拉伸
     *
     * @param pos       拉伸的部分
     * @param length    拉伸的目标长/宽度
     * @param direction 拉伸方向，WIDTH:横向, HEIGHT: 竖向
     */
    public void stretch(Pos pos, int length, StretchDirection direction) {
    }

    /**
     * 绘制矩形
     *
     * @param range 范围
     * @param color 颜色
     * @param width 宽度
     */
    public void drawRectangle(Range range, Color color, int width) {
    }

    /**
     * 绘制圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @param width  宽度
     */
    public void drawRoundedRectangle(Range range, int radius, Color color, int width) {
    }

    /**
     * 选择最多4个角绘制圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @param width  宽度
     * @param angles 角列表
     */
    public void drawRoundedRectangle2(Range range, int radius, Color color, int width, Collection<Angles> angles) {
    }

    /**
     * 画线
     *
     * @param pos1  起点
     * @param pos2  终点
     * @param color 颜色
     * @param width 宽度
     */
    public void drawLine(Pos pos1, Pos pos2, Color color, int width) {
    }

    /**
     * 画百分比圆环
     *
     * @param size        圆环大小
     * @param pos         圆环位置
     * @param width       圆环宽度
     * @param percent     百分比
     * @param color       颜色
     * @param startAngle  起始角度
     * @param transparent 是否透明
     */
    public void drawRing(int size, Pos pos, int width, double percent, Color color, int startAngle, boolean transparent) {
    }

    /**
     * 将图片转换为圆形
     *
     * @param shape 原图形状
     */
    public void toCircle(Shape shape) {
    }

    /**
     * 将图片变为圆角
     *
     * @param radius 半径
     */
    public void toRoundedCorner(int radius) {
    }

    /**
     * 给图片添加边框
     *
     * @param width 边框宽度
     * @param color 边框颜色
     * @param shape 边框形状
     */
    public void addBorder(int width, Color color, Shape shape) {
    }

    public Image getImage() {
        return this.image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Mode getMode() {
        return mode;
    }

    public enum Shape {
        CIRCLE,
        RECTANGLE
    }

    public enum Angles {
        TOP_LEFT,
        BOTTOM_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT
    }

    public enum StretchDirection {
        WIDTH,
        HEIGHT
    }

    public enum TextAlign {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum Mode {
        RGB,
        RGBA;

        public int getMode() {
            if (this == RGBA) return BufferedImage.TYPE_INT_ARGB;
            else return BufferedImage.TYPE_INT_RGB;
        }
    }
}
