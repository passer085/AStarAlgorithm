import helpers.Point;

import java.util.Random;

public class Maze {
    private final char[][] maze;

    private static final double percentage = 0.3;
    private static final int rows = 60;

    private static final int cols = 80;

    private Point initial;
    private Point goal;

    public Maze() {
        this.maze = new char[Maze.rows][Maze.cols];
        this.createMaze();

    }

    private void createMaze() {
        for (int i = 0; i < Maze.rows; i++) {
            for (int j = 0; j < Maze.cols; j++) {
                double rand = Math.random();
                if (rand <= Maze.percentage) {
                    this.maze[i][j] = '*';
                } else {
                    this.maze[i][j] = ' ';
                }
            }
        }

        Random r = new Random();

        this.initial = new Point(r.nextInt(Maze.cols), r.nextInt(Maze.rows));
        this.goal = new Point(r.nextInt(Maze.cols), r.nextInt(Maze.rows));

        if (this.maze[this.initial.getY()][this.initial.getX()] == '*') {
            throw new RuntimeException("Initial collides with obstacle");
        }

        this.maze[this.initial.getY()][this.initial.getX()] = 'I';

        if (this.maze[this.goal.getY()][this.goal.getX()] == '*') {
            throw new RuntimeException("Goal collides with obstacle");
        }

        this.maze[this.goal.getY()][this.goal.getX()] = 'G';
    }

    public Point getInitial() {
        return initial;
    }

    public Point getGoal() {
        return goal;
    }
    public char getSymbol(Point p){
        return this.maze[p.getY()][p.getX()];
    }

    public void setSymbol(Point p){
        this.maze[p.getY()][p.getX()] = '+';
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    @Override
    public String toString() {
        int numRows = this.maze.length;
        int numCols = this.maze[0].length;
        String out = "";

        for (int i = 0; i < numCols + 2; i++) {
            out += "#";
        }

        out += "\n";

        for (int i = 0; i < numRows; i++) {
            out += "#";
            for (int j = 0; j < numCols; j++) {
                out += this.maze[i][j];
            }
            out += "#\n";
        }

        for (int i = 0; i < numCols + 2; i++) {
            out += "#";
        }
        return out;
    }
}