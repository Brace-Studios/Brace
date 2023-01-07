package dev.dubhe.brace.utils.image;

public class Range {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    /**
     * 两点确定一个范围
     *
     * @param pos1 坐标1
     * @param pos2 坐标2
     */
    Range(Pos pos1, Pos pos2) {
        this.x1 = Math.min(pos1.x, pos2.x);
        this.y1 = Math.min(pos1.y, pos2.y);
        this.x2 = Math.max(pos1.x, pos2.x);
        this.y2 = Math.max(pos1.y, pos2.y);
    }

    /**
     * 两点确定一个范围
     *
     * @param x1 坐标1横坐标
     * @param y1 坐标1纵坐标
     * @param x2 坐标2横坐标
     * @param y2 坐标2纵坐标
     */
    Range(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }
}
