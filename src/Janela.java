import javax.swing.*;
import java.awt.*;

public class Janela {

    public Janela(int width, int height, String titulo, Game game)
    {
        JFrame frame = new JFrame(titulo);

        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        //A função acima defina o tamanho mínimo e máximo da tela

        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //constante que determina o fechado do programa
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }



}
