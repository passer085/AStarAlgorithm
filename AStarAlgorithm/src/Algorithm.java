import helpers.Point;

import java.util.*;

public class Algorithm {
    private Maze maze;
    private Set<Tree.Node<Point>> closedSet;
    private Set<Tree.Node<Point>> openSet;
    private Tree<Point> parent;
    private Point goal;

    public Algorithm(Maze m) {
        maze = m;
        closedSet = new HashSet<>();
        openSet = new HashSet<>();
        Tree.Node<Point> initial = new Tree.Node<>();
        initial.g = 0;
        initial.data = m.getInitial();
        openSet.add(initial);
        parent = new Tree<>(null);
        goal = m.getGoal();
    }

    private static class Tree<T> {
        private Node<T> root;

        private Tree(T rootData) {
            root = new Node<>();
            root.data = rootData;
            root.g = 0;
            root.parent = null;
            root.children = new ArrayList<>();
        }

        private static class Node<T> {
            private T data;
            private int g;
            private Node<T> parent;
            private List<Node<T>> children;

            @Override
            public boolean equals(Object obj) {
                boolean eq = false;
                if(obj instanceof Tree.Node<?>){
                    Tree.Node<?> node = (Tree.Node<?>) obj;
                    eq = node.data.equals(this.data);
                }
                return eq;
            }

            @Override
            public int hashCode() {
                return (g + data.hashCode());
            }
            

        }
    }

    public int run() {
        while (!this.openSet.isEmpty()) {
            Tree.Node<Point> currentNode = algF(openSet);// lowest f value in openSet
            if (maze.getGoal().equals(currentNode.data)) {
                reconstructPath(currentNode);
                System.out.println(maze.toString());
                return 1;
            }

            openSet.remove(currentNode); //no funciona
            closedSet.add(currentNode);
            ArrayList<Tree.Node<Point>> neighbors = neighbors(currentNode);
            for(Tree.Node<Point> neighbor : neighbors){
                if(!closedSet.contains(neighbor)){
                    int tentative_g = alG(currentNode);
                    if(!openSet.contains(neighbor) || tentative_g < currentNode.g){
                        if(parent.root.data == null){
                            parent.root.data = currentNode.data;
                        }
                        neighbor.parent = currentNode;
                        neighbor.g = tentative_g;

                        if(!openSet.contains(neighbor)){
                            openSet.add(neighbor);
                        }
                    }
                }

            }

        }
        System.out.println(maze.toString());
        throw new RuntimeException("A path to the Goal does not exist");
    }

    private Tree.Node<Point> algF(Set<Tree.Node<Point>> fs) {
        Tree.Node<Point> t = new Tree.Node<Point>();
        int f = Integer.MAX_VALUE;
        int fForNode;
        for(Tree.Node<Point> node : fs) {
            fForNode = node.g + algH(node);
            if (f > fForNode) {
                f = fForNode;
                t.data = node.data;
                t.g = node.g;
                t.parent = node.parent;
                t.children = node.children; //es necesario siquiera?
            }
        }
        return t;
    }

    private int alG(Tree.Node<Point> node) { //calculates the g of node's children
        int g = 1;
        if(node.parent != null){
            g = node.parent.g + 1;
        }
        return g;
    }

    private int algH(Tree.Node<Point> p) { //creo que va a haber que cambiarlo
        int dis = 0;
        dis += Math.abs(p.data.getX() - goal.getX());
        dis += Math.abs(p.data.getY() - goal.getY());
        return dis;
    }

    private ArrayList<Tree.Node<Point>> neighbors(Tree.Node<Point> current){
        ArrayList<Tree.Node<Point>> toReturn = new ArrayList<>();
        //hay que tener en cuenta que no pueden ser numeros negativos o que se pasen del limite del maze
        int x = current.data.getX();
        int y = current.data.getY();
        Point upLeft = new Point(x-1, y-1);
        Point up = new Point(x, y-1);
        Point upRight = new Point(x+1, y-1); 
        Point left = new Point(x-1, y);     
        Point right = new Point(x+1, y);    
        Point downLeft = new Point(x-1, y+1);
        Point down = new Point(x, y+1);
        Point downRight = new Point(x+1, y+1);  
        if (x-1 >= 0 && y-1 >= 0 && (maze.getSymbol(upLeft) == ' ' || maze.getSymbol(upLeft) == 'G')) {    // up-left
            Tree.Node<Point> n1 = new Tree.Node<>();
            n1.data = upLeft;
            toReturn.add(n1);          
        }
        if (y-1 >= 0 && (maze.getSymbol(up) == ' ' || maze.getSymbol(up) == 'G')) {    // up
            Tree.Node<Point> n2 = new Tree.Node<>();
            n2.data = up;
            toReturn.add(n2);              
        }
        if (x+1 < maze.getCols() && y-1 >= 0 && (maze.getSymbol(upRight) == ' ' || maze.getSymbol(upRight) == 'G')) {   // up-right
            Tree.Node<Point> n3 = new Tree.Node<>();
            n3.data = upRight;
            toReturn.add(n3);          
        }
        if (x-1 >= 0 && (maze.getSymbol(left) == ' ' || maze.getSymbol(left) == 'G')) {  // left
            Tree.Node<Point> n4 = new Tree.Node<>();
            n4.data = left;
            toReturn.add(n4);              
        }
        if (x+1 < maze.getCols() && (maze.getSymbol(right) == ' ' || maze.getSymbol(right) == 'G')) { // right
            Tree.Node<Point> n5 = new Tree.Node<>();
            n5.data = right;
            toReturn.add(n5);      
        }
        if (x-1 >= 0 && y+1 < maze.getRows() && (maze.getSymbol(downLeft) == ' ' || maze.getSymbol(downLeft) == 'G')) {  // down-left
            Tree.Node<Point> n6 = new Tree.Node<>();
            n6.data = downLeft;
            toReturn.add(n6);
        }
        if (y+1 < maze.getRows() && (maze.getSymbol(down) == ' ' || maze.getSymbol(down) == 'G')) {  // down
            Tree.Node<Point> n7 = new Tree.Node<>();
            n7.data = down;
            toReturn.add(n7);      
        }
        if(x+1 < maze.getCols() && y+1 < maze.getRows() && (maze.getSymbol(downRight) == ' ' || maze.getSymbol(downRight) == 'G')) { // down-right
            Tree.Node<Point> n8 = new Tree.Node<>();
            n8.data = downRight;
            toReturn.add(n8);
        }

        return toReturn;
    }

    private  void reconstructPath(Tree.Node<Point> current){
        current = current.parent;
        while(!current.data.equals(parent.root.data)){
            maze.setSymbol(current.data);
            current = current.parent;
        }
    }
}