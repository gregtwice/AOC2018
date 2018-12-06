import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lecture {

    private List<String> liste;

    public List<String> getList() {
        return liste;
    }


    public Lecture(String fichierEntree) {
        try {
            this.liste = new ArrayList<>();
            FileReader f = new FileReader(fichierEntree);
            BufferedReader br = new BufferedReader(f);
            String st = br.readLine();
            while (st != null) {
                liste.add(st);
                st = br.readLine();
            }
            br.close();

        } catch (FileNotFoundException ex) {
            System.err.println("FICHIER NON TROUVE");
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("PROBLEME IOE");
        }

    }
}