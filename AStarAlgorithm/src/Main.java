import helpers.Point;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze();
        Algorithm al = new Algorithm(maze);
        al.run();
        
    }
}