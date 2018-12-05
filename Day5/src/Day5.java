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
        while (true) {
            int len = string.length();
                char c = 'a';
                char C = 'A';
                int lenin = string.length();
                for (int k = 0; k < 26; k++) {
                    String toreplace = new StringBuilder().append(c).append(C).toString();
                    string = string.replace(toreplace, "");
                    toreplace = new StringBuilder().append(C).append(c).toString();
                    string = string.replace(toreplace, "");
                    c += 1;
                    C += 1;
                }
            if (string.length() == len) {
                break;
            }
        }
        return string.length();
    }

    public void part2() {
        char c = 'a';
        char C = 'A';
        int min = 999999;
        for (int i = 0; i < 26; i++) {
            String string = mesStrings.get(0);
            string = string.replace(new StringBuilder().append(c),"");
            string = string.replace(new StringBuilder().append(C),"");
            System.out.println((char) c +" | "+ (char)(C));
            System.out.println(string);
            min = Math.min(min , part1(string));
            System.out.println(min);
            c++;
            C++;
        }
        System.out.println(min);

    }

    public static void main(String[] args) {
        Lecture lecture = new Lecture("5.txt");
        Day5 day5 = new Day5(lecture.getList());
        System.out.println(day5.part1(day5.mesStrings.get(0)));
        day5.part2();
    }
}
