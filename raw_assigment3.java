import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
         simulation();
    }
    static void simulation() {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        Board board = new Board(N);

        int x_green = sc.nextInt();
        int y_green = sc.nextInt();
        GreenFigure greenFigure = new GreenFigure(x_green,y_green, Color.GREEN.toString(), board );

        board.addFigure(x_green, y_green, greenFigure);

        int x_red = sc.nextInt();
        int y_red = sc.nextInt();
        RedFigure redFigure = new RedFigure(x_red,y_red, Color.RED.toString(), board );

        board.addFigure(x_red, y_red, redFigure);

        int m_coins = sc.nextInt();
        for (int i = 0; i < m_coins; i++) {
            int x_coin  = sc.nextInt();
            int y_coin  = sc.nextInt();
            int value = sc.nextInt();
            board.addCoin(x_coin, y_coin, value);

        }

        int n_commands = sc.nextInt();
        for (int i = 0; i < n_commands; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            String figureName = parts[0];
            String command = parts[1];

            processCommand(figureName, command, board);
        }
    }
    static void processCommand(String figureName, String command, Board board) {
        Figure figure = board.getFigure(figureName);
        FigureProxy proxy = new FigureProxy(figure , board);
        if (command.equals("UP")) {
            figure.setStrategy(new upMoveStrategy());


        } else if (command.equals("DOWN")) {

        } else if (command.equals("LEFT")) {

        } else if (command.equals("RIGHT")) {

        } else if (command.equals("COPY")) {
            proxy.clone();
        } else if (command.equals("STYLE")) {
            proxy.changeStyle();
        }
    }

}

enum Color{
    GREEN , RED;
}
enum Game_style{
    NORMAL, ATTACKING;
}

enum Direction{
    UP, DOWN, LEFT, RIGHT;
}

interface moveStrategy{
    public void move(Figure figure, String direction, Board board);
}
class upMoveStrategy implements moveStrategy{
    @Override
    public void move(Figure figure, String direction, Board board) {
        if (figure.getGame_style() == Game_style.NORMAL.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX(), figure.getY() + 1);
        } else if (figure.getGame_style() == Game_style.ATTACKING.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX(), figure.getY() + 2);
        }
    }
}

class downMoveStrategy implements moveStrategy{
    @Override
    public void move(Figure figure, String direction, Board board) {
        if (figure.getGame_style() == Game_style.NORMAL.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX(), figure.getY() - 1);
        } else if (figure.getGame_style() == Game_style.ATTACKING.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX(), figure.getY() - 2);
        }
    }
}

class leftMoveStrategy implements moveStrategy{
    @Override
    public void move(Figure figure, String direction, Board board) {
        if (figure.getGame_style() == Game_style.NORMAL.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX() - 1, figure.getY());
        } else if (figure.getGame_style() == Game_style.ATTACKING.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX() - 2 , figure.getY());
        }
    }
}

class rightMoveStrategy implements moveStrategy{
    @Override
    public void move(Figure figure, String direction, Board board) {
        if (figure.getGame_style() == Game_style.NORMAL.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX() + 1, figure.getY());
        } else if (figure.getGame_style() == Game_style.ATTACKING.toString()){
            board.moveFigure(figure.getX(), figure.getY(), figure.getX() + 2, figure.getY());
        }
    }
}


interface FigureAction{
    public Figure clone();
    public void changeStyle();
    public void move(Board board,String direction);
}

class Figure implements FigureAction{
    private int x;
    private int y;
    private String color;
    private String game_style = Game_style.NORMAL.toString();
    private boolean isAlive = true;
    private boolean clonable;
    public moveStrategy strategy;
    private Board board;


    Figure(int x, int y, String color, Board board ,boolean clonable){
        this.x = x;
        this.y = y;
        this.color = color;
        this.board = board;
        this.clonable = clonable;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public String getColor() {
        return color;
    }
    public boolean isClonable() {
        return clonable;
    }
    public String getGame_style() {
        return game_style;
    }
    @Override
    public Figure clone() {
        clonable = false;
        int new_x = y;
        int new_y = x;
        String new_color = color + "CLONE";
        System.out.println(color.toString() + " CLONED TO " + new_x + " " + new_y);
        return new Figure(new_x, new_y, new_color, board,false);
    }

    @Override
    public void changeStyle() {
        if (game_style == Game_style.ATTACKING.toString()){
            game_style = Game_style.NORMAL.toString();
        } else {
            game_style = Game_style.ATTACKING.toString();
        }
        System.out.println(color + " CHANGED STYLE TO " + game_style);
    }
    public void setStrategy(moveStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void move(Board board, String direction) {
        strategy.move(this,direction,board);
    }

    public void die() {
        isAlive = false;
    }
}
// For proxy pattern
class FigureProxy extends Figure {
    private Figure proxy;
    private Board board;

    FigureProxy(Figure figure, Board board) {
        super(figure.getX(), figure.getY(), figure.getColor(), board,true);
        this.proxy = figure;
        this.board = board;
    }

    @Override
    public Figure clone(){
        if (!isAlive()){
            System.out.println("INVALID ACTION");
            return null;
        }
        if (!isClonable()){
            System.out.println("INVALID ACTION");
            return null;
        }
        if (getX() == getY()){
            System.out.println("INVALID ACTION");
            return null;
        }
        proxy.clone();
        return null;
    }

    @Override
    public void changeStyle() {
        if (!isAlive()){
            System.out.println("INVALID ACTION");
        }
        proxy.changeStyle();
    }
    @Override
    public void move(Board board, String direction) {
        if (!isAlive()){
            System.out.println("INVALID ACTION");
        }
        int new_x = proxy.getX();
        int new_y = proxy.getY();

        String style = proxy.getGame_style();

        if (style == Game_style.ATTACKING.toString()){
            switch (direction){
                case "UP": new_y += 2; break;
                case "DOWN": new_y -= 2; break;
                case "LEFT": new_x -= 2; break;
                case "RIGHT": new_x += 2; break;
            }
        }
        else {
            switch (direction){
                case "UP": new_y -= 1; break;
                case "DOWN": new_y += 1; break;
                case "LEFT": new_x -= 1; break;
                case "RIGHT": new_x += 1; break;
            }
        }

        if (board.hasSameColorFigure(new_x,new_y,proxy)){
            System.out.println("INVALID ACTION");
        } else if (board.hasOppositeFigure(new_x,new_y,proxy)) {
            System.out.println(proxy.getColor() + " MOVED TO " + new_x + " " + new_y + " AND KILLED " +
                    board.getFigure(new_x,new_y).getColor());
            board.killFigure(new_x,new_y);
            proxy.move(board,direction);
        } else if (board.hasCoin(new_x,new_y)) {
            System.out.println(proxy.getColor() + " MOVED TO " + new_x + " " + new_y + " AND COLLECTED " +
                    board.getCoin(new_x,new_y));
            board.collectCoin(new_x,new_y, proxy.getColor());
            proxy.move(board,direction);
        }

    }

}

class GreenFigure extends Figure{
    GreenFigure(int x, int y, String color, Board board){
        super(x,y,Color.GREEN.toString(),board,true);
    }
}

class RedFigure extends Figure{
    RedFigure(int x, int y, String color,Board board){
        super(x,y,Color.RED.toString(),board,true);
    }
}

class Board{
    private int dimension;
    private Map<String, Figure> figures = new HashMap<String, Figure>();
    private Map<String, Integer> coins = new HashMap<>();
    private Map<String,Integer> score = new HashMap<>();


    Board(int dimension){
        this.dimension = dimension;
    }

    //add elements
    public void addFigure(int x, int y, Figure figure) {
        figures.put(x + "," + y, figure);
    }
    public void addCoin(int x, int y, int value){
        coins.put(x + "," + y, value);
    }

    //figures
    public Figure getFigure(String color){
        for (int i = 0; i < figures.size(); i++){
            for (int j = 0; j < figures.size(); j++){
                if (figures.get(i + " " + j).getColor().equals(color)){
                    return figures.get(i + " " + j);
                }
            }
        }
        return null;
    }

    public Figure getFigure(int x, int y){
        return figures.get(x + "," + y);
    }

    public boolean hasSameColorFigure(int x, int y, Figure figure) {
        Figure f = figures.get(x + "," + y);
        if (f == null){
            return false;
        } else if (f.getColor().startsWith(figure.getColor())){
            return true;
        }
        return false;
    }

    public boolean hasOppositeFigure(int x, int y, Figure figure) {
        Figure f = figures.get(x + "," + y);
        if (f == null){
            return false;
        } else if (!f.getColor().startsWith(figure.getColor())){
            return true;
        }
        return false;
    }

    public void killFigure(int x, int y) {
        Figure figure = figures.get(x + "," + y);
        if (figure != null) {
            figure.die();
        }
        figures.remove(x + "," + y);
    }

    public void moveFigure(int fromX, int fromY, int toX, int toY) {
        Figure figure = figures.remove(fromX + "," + fromY);
        if (figure != null) {
            figures.put(toX + "," + toY, figure);
        }
    }


    //Coins
    public boolean hasCoin(int x, int y) {
        return coins.containsKey(x + "," + y);
    }
    public int getCoin(int x, int y) {
        return coins.get(x + "," + y);
    }
    public void collectCoin(int x, int y, String color) {
        score.put(color, coins.get(x + "," + y));
        coins.remove(x + "," + y);
    }
}

