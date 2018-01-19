package com.company;

public class Obstacle {
    public Rectangle body;

    public Obstacle(int x, int y, int width, int height)
    {
        body = new Rectangle(x,y,width,height);
    }

    public void Update()
    {
        body.setLocation(body.getX() - 1, body.getY());
    }
}
