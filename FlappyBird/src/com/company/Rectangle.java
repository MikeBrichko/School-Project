package com.company;

public class Rectangle {

    private int x, y, width, height;
    public int left, right, top, bottom;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        left = x;
        right = x + width;
        top = y + height;
        bottom = y;
    }

    public boolean intersects(Rectangle other)
    {
        if ((left >= other.left && left <= other.right && top >= other.top && top <= other.bottom)
                || (right >= other.left && right <= other.right && top <= other.top && top >= other.bottom)
                || (left >= other.left && left <= other.right && bottom >= other.bottom && bottom <= other.top)
                || (right >= other.left && right <= other.right && bottom >= other.bottom && bottom <= other.top))
            return true;
        else
            return false;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
        left = x;
        right = x + width;
        top = y + height;
        bottom = y;
    }
}
