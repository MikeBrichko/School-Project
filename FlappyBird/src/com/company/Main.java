package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Bird bird;
    static List<Obstacle> obstacles;
    static char[][] map;
    static final int mapWidth = 15;
    static final int mapHeight = 10;
    static final int birdStartX = 2;
    static final int birdStartY = 2;
    static boolean gameOver;

    static final char emptyChar = ' ';
    static final char birdChar = '@';
    static final char obstacleChar = '#';
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        bird = new Bird(birdStartX, birdStartY);
        obstacles = new ArrayList<>();
        map = new char[mapHeight][mapWidth];
        gameOver = false;
        boolean birdGoingUp;
        int i, j;
        while (!gameOver)
        {
            deleteOldObstacles();
            createNewObstacles();
            //Main game loop
            //Clear the map
            for (i = 0; i < map.length; i++)
                for (j = 0; j < map[0].length; j++)
                    map[i][j] = emptyChar;

            //Draw bird and obstacles
            for (i = bird.body.left; i <= bird.body.right; i++)
                for (j = bird.body.bottom; j <= bird.body.top; j++)
                    map[j][i] = birdChar;

            for (Obstacle obstacle : obstacles)
                for (i = obstacle.body.left; i <= obstacle.body.right; i++)
                    for (j = obstacle.body.bottom; j <= obstacle.body.top; j++)
                        map[j][i] = obstacleChar;

            System.out.println("-----------------------");
            //Print to command line
            for(char[] row : map)
                System.out.println(row);
            System.out.println("-----------------------");


            //Bird moving
            birdGoingUp = userInput(scanner);
            if (birdGoingUp) {
                if (bird.body.getY() != 0)
                    bird.Update(-1);
            }
            else {
                bird.Update(1);
                if (bird.body.getY() >= mapHeight)
                    gameOver = true;
            }

            //Obstacle movement and collision
            for (Obstacle obstacle : obstacles)
            {
                obstacle.Update();
                if (obstacle.body.intersects(bird.body))
                    gameOver = true;
            }
        }
        //Game over stuff
    }
    static void createNewObstacles()
    {
        if (obstacles.size() < 2)
        {
            //Generate a random gap size from 2 to 5
            int gapSize = (int)(Math.random() * 4) + 2;
            int blocked = map.length - gapSize - 1;
            int top = (int)(Math.random() * blocked);
            int bottom = blocked - top;
            obstacles.add(new Obstacle(map[0].length - 2, 0, 1, top - 1));
            obstacles.add(new Obstacle(map[0].length - 2, map.length - bottom - 1, 1, bottom));

        }
    }
    static void deleteOldObstacles()
    {
        List<Obstacle> indicesToRemove = new ArrayList<>();
        for(Obstacle obstacle : obstacles)
        {
            if (obstacle.body.left <= 0)
                indicesToRemove.add(obstacle);
        }
        for(Obstacle obstacle : indicesToRemove)
            obstacles.remove(obstacle);
    }
    static boolean userInput(Scanner scanner)
    {
        //Returns true if the user wants the bird to go up
        return scanner.nextLine().contains(" ");
    }
}
