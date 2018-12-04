import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day42 {

    ArrayList<String> mesStrings;
    Map<Integer, Garde> mesGardes;

    public Day42(List<String> mesStrings) {
        this.mesStrings = new ArrayList<>();
        this.mesStrings.addAll(mesStrings);
        this.mesGardes = new HashMap<>();
    }


    class Garde {

        int id;
        ArrayList<Repos> repos;
        int minDodo = 0;
        int[] minutesDodo;

        public Garde(int id) {
            minutesDodo = new int[60];
            this.id = id;
            this.repos = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Garde garde = (Garde) o;

            if (id != garde.id) return false;
            return repos != null ? repos.equals(garde.repos) : garde.repos == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (repos != null ? repos.hashCode() : 0);
            return result;
        }

    }

    class Repos {
        Date debut;
        Date fin;
        int duree;

        public Repos(Date debut, Date fin) {
            this.debut = debut;
            this.fin = fin;
            this.duree = (int) (fin.getTime() - debut.getTime()) / (60 * 1000);
        }

    }

    public void traiter() throws ParseException {
        Collections.sort(mesStrings);
        this.mesGardes.clear();
        // générer gardes
        for (String str : mesStrings) {
            Pattern garde = Pattern.compile("#([0-9]+)");
            Matcher m = garde.matcher(str);
            if (m.find()) {
                Garde garde1 = new Garde(Integer.parseInt(m.group(1)));
                this.mesGardes.put(Integer.parseInt(m.group(1)), garde1);
            }
        }

        classerGardes();
    }

    public void classerGardes() throws ParseException {

        // Attention c'est dégeulasse

        Date debut = null;
        Date fin = null;
        Garde g = null;
        for (String string : mesStrings) {
            String[] str = string.trim().split("\\s+", 3);
            str[0] = str[0].replace("[", "");
            str[1] = str[1].replace("[", "");
            str[0] = str[0].replace("]", "");
            str[1] = str[1].replace("]", "");
            str[0] += ' ' + str[1];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            if (string.contains("shift")) {
                Pattern garde = Pattern.compile("#([0-9]+)");
                Matcher m = garde.matcher(string);
                if (m.find())
                    g = mesGardes.get(Integer.parseInt(m.group(1)));
            }
            if (string.contains("falls")) {
                debut = formatter.parse(str[0]);
            }
            if (string.contains("wakes")) {
                fin = formatter.parse(str[0]);
//                g.repos.add(new Repos(debut,fin));
                Repos e = new Repos(debut, fin);
                mesGardes.get(g.id).repos.add(e);
                mesGardes.get(g.id).minDodo += e.duree;
//                mesGardes.put(g.id , g);

            }

        }
        int i = 0;
        int Maxmin = 0;
        int idg = 0;
        int ifinal = 0;
        for (Garde v : mesGardes.values()) {
            i++;
            if (Maxmin < v.minDodo) {
                ifinal = i;
                idg = v.id;
                Maxmin = v.minDodo;
            }
        }

        Garde bg = mesGardes.get(idg);

        int[][] grille = new int[20][60];
        for (int w = 0; w < 20; w++) {
            if (w == 0)
                for (int j = 0; j < 60; j++) {
                    grille[0][j] = j;
                }
            else
                for (int j = 0; j < 60; j++) {
                    grille[w][j] = 0;
                }

        }

        int b = 1;
        int curDay = bg.repos.get(0).debut.getDay();
        for (Repos repos : bg.repos) {

            if (curDay != repos.debut.getDay()) {
                curDay = repos.debut.getDay();
                b++;
            }
            for (int j = repos.debut.getMinutes(); j < repos.fin.getMinutes(); j++) {
                grille[b][j] = 1;
            }

        }

        int cpt = 0;
        int best = 0;
        int index = 0;
        for (int k = 0; k < 60; k++) {
            for (int l = 0; l < 20; l++) {
                if (grille[l][k] == 1)
                    cpt++;
            }
            if (cpt > best) {
                best = cpt;
                index = k;
            }
            cpt = 0;
        }

//        System.out.println(index * bg.id);

        part2();
    }

    public void part2() {
        int idGarde = 0;
        int maxSleepGarde = 0;
        int idMin = 0;
        for (Garde garde : mesGardes.values()) {
            for (Repos repos : garde.repos) {
                for (int j = repos.debut.getMinutes(); j < repos.fin.getMinutes(); j++) {
                    garde.minutesDodo[j]++;
                }
            }
            int max = 0;
            int idx = 0;
            for (int i = 0; i < garde.minutesDodo.length; i++) {
                if (garde.minutesDodo[i] > max) {
                    max = garde.minutesDodo[i];
                    idx = i;
                }
            }

            if (maxSleepGarde < max) {
                maxSleepGarde = max;
                idMin = idx;
                idGarde = garde.id;
            }

        }
        System.out.println("Solution partie 2 = " + idMin * idGarde);


//        System.out.println("a");
    }


    @Override
    public String toString() {
        return "Day42{" +
                "mesStrings=" + mesStrings +
                ", mesGardes=" + mesGardes +
                '}';
    }

    public static void main(String[] args) {
        Lecture lecture = new Lecture("dataPierre.txt");
        Day42 day42 = new Day42(lecture.getList());
        try {
            day42.traiter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
