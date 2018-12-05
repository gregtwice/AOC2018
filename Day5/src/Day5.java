import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day5 {

    ArrayList<String> mesStrings;

    public Day5(List<String> mesStrings) {
        this.mesStrings = new ArrayList<>();
        this.mesStrings.addAll(mesStrings);
    }

    public int part1(String string) {
        char[] chars = string.toCharArray();
        boolean lench = true;
        StringBuilder chaine = new StringBuilder();


        for (char c1 : chars) {
            if (chaine.length() == 0) {
                chaine.append(c1);
            } else {
                if ((c1 ^ chaine.charAt(chaine.length() - 1)) == 32) {
                    chaine.deleteCharAt(chaine.length() - 1);
                } else {
                    chaine.append(c1);
                }
            }
        }
        string = chaine.toString();
        return string.length();

    }

    public void part2() {
        char c = 'a';
        char C = 'A';
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            String string = mesStrings.get(0);
            string = string.replace(new StringBuilder().append(c), "");
            string = string.replace(new StringBuilder().append(C), "");
            min = Math.min(min, part1(string));
            c++;
            C++;
        }
        System.out.println(min);

    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Lecture lecture = new Lecture("5.txt");
        Day5 day5 = new Day5(lecture.getList());
        System.out.println(day5.part1(day5.mesStrings.get(0)));
        day5.part2();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
