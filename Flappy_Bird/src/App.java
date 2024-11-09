import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        int boardHeight=360;
        int boardWidth=640;

        JFrame frame = new JFrame("Flappy Bird");
    //  frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird=new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();  
        frame.setVisible(true);

    }
}
