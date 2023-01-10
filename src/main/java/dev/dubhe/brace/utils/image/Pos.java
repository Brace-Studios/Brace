package dev.dubhe.brace.utils.image;

public class Pos {
    public final int x;
    public final int y;

    /**
     * 坐标
     *
     * @param x 横坐标
     * @param y 纵坐标
     */
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos move(int x, int y) {
        return new Pos(this.x + x, this.y + y);
    }
}
