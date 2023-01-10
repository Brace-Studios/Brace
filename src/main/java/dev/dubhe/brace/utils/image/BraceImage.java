package dev.dubhe.brace.utils.image;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class BraceImage {
    private BufferedImage image;
    private final int width;
    private final int height;
    private final Mode mode;

    public BraceImage(int width, int height, Color color, Mode mode) {
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.image = BraceImage.newImage(width, height, color, mode);
    }

    public BraceImage(BufferedImage image, Mode mode) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.mode = mode;
    }

    private static BufferedImage newImage(int width, int height, Color color, Mode mode) {
        BufferedImage image;
        if (mode == Mode.RGBA) image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        else image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.fillRect(0, 0, width, height);
        g.dispose();
        return image;
    }

    /**
     * 图像缩放
     *
     * @param ratio 比例
     * @return 原BraceImage实例
     */
    public BraceImage resize(double ratio) {
        this.image = (BufferedImage) image.getScaledInstance((int) (this.width * ratio), (int) (this.height * ratio), Image.SCALE_DEFAULT);
        return this;
    }

    /**
     * 图像缩放
     *
     * @param width  宽
     * @param height 高
     * @return 原BraceImage实例
     */
    public BraceImage resize(int width, int height) {
        this.image = (BufferedImage) image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return this;
    }

    /**
     * 图像裁切
     *
     * @param range 范围
     * @return 原BraceImage实例
     */
    public BraceImage crop(Range range) {
        BufferedImage bf = new BufferedImage(range.getWidth(), range.getHeight(), image.getType());
        Graphics2D g = bf.createGraphics();
        g.drawImage(image, 0, 0, range.getWidth(), range.getHeight(), range.x1, range.y1, range.x2, range.y2, null);
        g.dispose();
        this.image = bf;
        return this;
    }

    /**
     * 粘贴图片
     *
     * @param image 图片
     * @param pos   坐标
     * @return 原BraceImage实例
     */
    public BraceImage paste(BraceImage image, Pos pos) {
        Graphics2D g = this.image.createGraphics();
        g.drawImage(image.image, pos.x, pos.y, null);
        g.dispose();
        return this;
    }

    /**
     * 写文本
     *
     * @param text  文本
     * @param pos   坐标
     * @param font  字体
     * @param color 颜色
     * @param align 对齐方式
     * @return 原BraceImage实例
     */
    public BraceImage text(String text, Pos pos, Font font, Color color, TextAlign align) {
        Graphics2D g = image.createGraphics();
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
        return this;
    }

    /**
     * 将某一部分进行拉伸
     *
     * @param pos       拉伸的部分
     * @param length    拉伸的目标长/宽度
     * @param direction 拉伸方向，WIDTH:横向, HEIGHT: 竖向
     * @return 原BraceImage实例
     */
    public BraceImage stretch(Pos pos, int length, StretchDirection direction) {
        return this;
    }

    /**
     * 绘制矩形
     *
     * @param range 范围
     * @param color 颜色
     * @param width 宽度
     * @return 原BraceImage实例
     */
    public BraceImage drawRectangle(Range range, Color color, int width) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(width));
        g.drawRect(range.x1, range.y1, range.getWidth(), range.getHeight());
        g.dispose();
        return this;
    }

    /**
     * 绘制实心矩形
     *
     * @param range 范围
     * @param color 颜色
     * @return 原BraceImage实例
     */
    public BraceImage fillRectangle(Range range, Color color) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(0));
        g.fillRect(range.x1, range.y1, range.getWidth(), range.getHeight());
        g.dispose();
        return this;
    }

    /**
     * 绘制圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @param width  宽度
     * @return 原BraceImage实例
     */
    public BraceImage drawRoundedRectangle(Range range, int radius, Color color, int width) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(width));
        g.drawRoundRect(range.x1, range.y1, range.getWidth(), range.getHeight(), radius, radius);
        g.dispose();
        return this;
    }

    /**
     * 绘制实心圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @return 原BraceImage实例
     */
    public BraceImage fillRoundedRectangle(Range range, int radius, Color color) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(0));
        g.fillRoundRect(range.x1, range.y1, range.getWidth(), range.getHeight(), radius, radius);
        g.dispose();
        return this;
    }

    /**
     * 选择最多4个角绘制圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @param width  宽度
     * @param angles 角列表
     * @return 原BraceImage实例
     */
    public BraceImage drawRoundedRectangle2(Range range, int radius, Color color, int width, Angles... angles) {
        int roundRectWidth = range.getWidth() + width / 2;
        int roundRectHeight = range.getHeight() + width / 2;
        Range rectRange = new Range(width / 2, width / 2, range.getWidth(), range.getHeight());
        BraceImage roundRect = new BraceImage(roundRectWidth, roundRectHeight, new Color(0, 0, 0, 0), BraceImage.Mode.RGBA);
        roundRect.drawRoundedRectangle(rectRange, radius * 2, color, width);
        BraceImage rectangle = new BraceImage(roundRectWidth, roundRectHeight, new Color(0, 0, 0, 0), BraceImage.Mode.RGBA);
        rectangle.drawRectangle(rectRange, color, width);

        return drawByAngle(range, width, roundRectWidth, roundRectHeight, roundRect, rectangle, angles);
    }

    /**
     * 选择最多4个角绘制实心圆角矩形
     *
     * @param range  圆角矩形的范围
     * @param radius 半径
     * @param color  颜色
     * @param angles 角列表
     * @return 原BraceImage实例
     */
    public BraceImage fillRoundedRectangle2(Range range, int radius, Color color, Angles... angles) {
        int roundRectWidth = range.getWidth();
        int roundRectHeight = range.getHeight();
        Range rectRange = new Range(0, 0, range.getWidth(), range.getHeight());
        BraceImage roundRect = new BraceImage(roundRectWidth, roundRectHeight, new Color(0, 0, 0, 0), BraceImage.Mode.RGBA);
        roundRect.fillRoundedRectangle(rectRange, radius * 2, color);
        BraceImage rectangle = new BraceImage(roundRectWidth, roundRectHeight, new Color(0, 0, 0, 0), BraceImage.Mode.RGBA);
        rectangle.fillRectangle(rectRange, color);

        return drawByAngle(range, 0, roundRectWidth, roundRectHeight, roundRect, rectangle, angles);
    }

    @NotNull
    private BraceImage drawByAngle(Range range, int width, int roundRectWidth, int roundRectHeight, BraceImage image1, BraceImage image2, Angles[] angles) {
        Pos topLeftPoint, topRightPoint, bottomLeftPoint, bottomRightPoint, centerPoint;
        topLeftPoint = new Pos(0, 0);
        topRightPoint = new Pos(roundRectWidth, 0);
        bottomLeftPoint = new Pos(0, roundRectHeight);
        bottomRightPoint = new Pos(roundRectWidth, roundRectHeight);
        centerPoint = new Pos(roundRectWidth / 2, roundRectHeight / 2);

        Range topLeftRange, topRightRange, bottomLeftRange, bottomRightRange;
        topLeftRange = new Range(topLeftPoint, centerPoint);
        topRightRange = new Range(topRightPoint, centerPoint);
        bottomLeftRange = new Range(bottomLeftPoint, centerPoint);
        bottomRightRange = new Range(bottomRightPoint, centerPoint);

        List<Angles> anglesList = List.of(angles);
        BraceImage topLeft, topRight, bottomLeft, bottomRight;
        if (anglesList.contains(Angles.TOP_LEFT)) topLeft = image1.copy().crop(topLeftRange);
        else topLeft = image2.copy().crop(topLeftRange);
        if (anglesList.contains(Angles.TOP_RIGHT)) topRight = image1.copy().crop(topRightRange);
        else topRight = image2.copy().crop(topRightRange);
        if (anglesList.contains(Angles.BOTTOM_LEFT)) bottomLeft = image1.copy().crop(bottomLeftRange);
        else bottomLeft = image2.copy().crop(bottomLeftRange);
        if (anglesList.contains(Angles.BOTTOM_RIGHT)) bottomRight = image1.copy().crop(bottomRightRange);
        else bottomRight = image2.copy().crop(bottomRightRange);

        Pos topLeftPastePoint, topRightPastePoint, bottomLeftPastePoint, bottomRightPastePoint;
        topLeftPastePoint = new Pos(range.x1 - width / 2, range.y1 - width / 2);
        topRightPastePoint = topLeftPastePoint.move(topLeftRange.getWidth(), 0);
        bottomLeftPastePoint = topLeftPastePoint.move(0, topLeftRange.getHeight());
        bottomRightPastePoint = bottomLeftPastePoint.move(topLeftRange.getWidth(), 0);

        this.paste(topLeft, topLeftPastePoint);
        this.paste(topRight, topRightPastePoint);
        this.paste(bottomLeft, bottomLeftPastePoint);
        this.paste(bottomRight, bottomRightPastePoint);

        return this;
    }

    /**
     * 画线
     *
     * @param pos1  起点
     * @param pos2  终点
     * @param color 颜色
     * @param width 宽度
     * @return 原BraceImage实例
     */
    public BraceImage drawLine(Pos pos1, Pos pos2, Color color, int width) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(width));
        g.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
        g.dispose();
        return this;
    }

    /**
     * 画百分比圆环
     *
     * @param size       圆环大小
     * @param pos        圆环位置
     * @param width      圆环宽度
     * @param percent    百分比
     * @param color      颜色
     * @param startAngle 起始角度
     * @return 原BraceImage实例
     */
    public BraceImage drawRing(int size, Pos pos, int width, double percent, Color color, int startAngle) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(width));
        g.drawArc(pos.x, pos.y, size, size, startAngle, (int) (360 * percent));
        g.dispose();
        return this;
    }

    /**
     * 将图片转换为圆形
     *
     * @return 原BraceImage实例
     */
    public BraceImage toCircle() {
        Graphics2D g = image.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage resultImg = g.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
        g = resultImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setClip(shape);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        this.image = resultImg;
        return this;
    }

    /**
     * 将图片变为圆角
     *
     * @param radius 半径
     * @return 原BraceImage实例
     */
    public BraceImage toRoundedCorner(int radius) {
        BufferedImage bfImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight());
        Graphics2D g2 = bfImage.createGraphics();
        bfImage = g2.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
        g2 = bfImage.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fill(new Rectangle(bfImage.getWidth(), bfImage.getHeight()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
        g2.setClip(shape);
        g2 = bfImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, image.getWidth(), image.getHeight(), radius, radius);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g2.dispose();
        this.image = bfImage;
        return this;
    }

    /**
     * 给图片添加边框
     *
     * @param width 边框宽度
     * @param color 边框颜色
     * @param shape 边框形状
     * @return 原BraceImage实例
     */
    public BraceImage addBorder(int width, Color color, Shape shape) {
        Graphics2D g = image.createGraphics();
        g.setColor(color.toAwtColor());
        g.setStroke(new BasicStroke(width));
        int l = width / 2;
        if (shape == Shape.CIRCLE) {
            g.drawArc(l, l, this.width - width, this.height - width, 0, 360);
        } else {
            g.drawRect(l, l, this.width - width, this.height - width);
        }
        g.dispose();
        return this;
    }

    public BraceImage copy() {
        return new BraceImage(width, height, new Color(0, 0, 0, 0), this.mode).paste(this, new Pos(0, 0));
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
