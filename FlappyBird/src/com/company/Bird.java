package com.company;

public class Bird {

    Rectangle body;
    final int height = 0;
    final int width = 0;


    public Bird(int x, int y) {
        body = new Rectangle(x, y, width, height);
    }

    public void Update(int deltaY)
    {
        body.setLocation(body.getX(), body.getY() + deltaY);
    }


}
