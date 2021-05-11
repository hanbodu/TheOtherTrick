import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by braunern on 16/12/2015.
 *
 * Classe qui contient le main pour faire les tests
 */
public class Executable {
    public static void main(String[] args)  {

        try{
            InputStream input = new FileInputStream("maison.txt");

            Graphe g = Graphe.read(input);
            
            // test toString()
            System.out.println(g);

            // test nbEdges
            System.out.printf("le nombre d'ar¨ºtes est %d\n" , g.nbAretes());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
