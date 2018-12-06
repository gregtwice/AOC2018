import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {


    int[][] intGrille = new int[400][400];
    static int idG = 0;
    ArrayList<String> mesStrings;
    ArrayList<Point> mesPoints;

    public Day6(List<String> mesStrings) {
        this.mesStrings = new ArrayList<>();
        this.mesStrings.addAll(mesStrings);
        this.mesPoints = new ArrayList<>();
    }

    class Point {
        int x;
        int y;
        boolean loc;
        int ptsproches = 0;
        int id;

        public Point(int x, int y, boolean loc) {
            this.id = idG++;
            this.x = x;
            this.y = y;
            this.loc = loc;
        }
    }


    public void part1() {
        Setpoints();


        for (int i = 0; i < intGrille.length; i++) { // y
            for (int j = 0; j < intGrille.length; j++) { // x
                int min = 9999;
                int idpt = 0;
                for (Point point1 : mesPoints) {
//                    if ((i == point1.x && j == point1.y)) {
                    int dist = Math.abs(point1.x - j) + Math.abs(point1.y - i);
                    if (dist < min) {
                        min = dist;
                        idpt = point1.id;
                    } else if (min == dist) {
                        idpt = -1;
//                        }

                    }
                }
                intGrille[i][j] = idpt;
                if (idpt != -1) {
                    for (Point point : mesPoints) {
                        if ((i == 0 || i == 399 || j == 0 || j == 399) && idpt == point.id) {
                            point.ptsproches = Integer.MAX_VALUE;
                        } else if (point.ptsproches != Integer.MAX_VALUE && idpt == point.id) {
                            point.ptsproches++;
                        }
                    }
                }
            }
        }
        int max = 0;
        for (Point point : mesPoints) {
            if (point.ptsproches != Integer.MAX_VALUE) {
                max = Math.max(max, point.ptsproches);
            }
        }
        System.out.println(max);
    }

    public int distance(int x1, int y1, int x2, int y2) {
        return (Math.abs(x2 - x1) + Math.abs(y1 - y2));
    }

    ArrayList<Integer> tailleZone;

    public void part2() {

//        Setpoints();
        int t = 400;
        int taille = 0;
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                int totdist = 0;
                for (Point p : mesPoints) {
                    totdist += this.distance(p.x, p.y, j, i);
                }
                if (totdist < 10000) {
                    taille++;
                }
            }
        }
        System.out.println(taille);
    }

    private void Setpoints() {
        Pattern p = Pattern.compile("([0-9]+), ([0-9]+)");
        for (String s : mesStrings) {
            Matcher m = p.matcher(s);
            if (m.find()) {
                int x = Integer.parseInt(m.group(1));
                int y = Integer.parseInt(m.group(2));
                Point point = new Point(x, y, true);
                intGrille[x][y] = point.id;
                mesPoints.add(point);
            }

        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Lecture lecture = new Lecture("6.txt");
        Day6 day6 = new Day6(lecture.getList());
        day6.part1();
        day6.part2();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
