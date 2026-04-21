package MATH.service;

import java.io.*;

/*
 * OOP:
 * - ABSTRACTION (implements interface)
 * - ENCAPSULATION (file logic hidden)
 */

public class FileHighScoreService implements HighScoreService {

    private final String path = "MATH/highscore.txt";

    public FileHighScoreService() {
        try {
            File f = new File(path);
            if (!f.exists()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write("0\n0\n0\n");
                bw.close();
            }
        } catch (Exception e) {}
    }

    public int getHighScore(String d) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int e=Integer.parseInt(br.readLine());
            int m=Integer.parseInt(br.readLine());
            int h=Integer.parseInt(br.readLine());
            br.close();

            if(d.equals("Easy")) return e;
            if(d.equals("Medium")) return m;
            return h;
        } catch(Exception ex){ return 0; }
    }

    public void saveHighScore(String d,int s){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            int e=Integer.parseInt(br.readLine());
            int m=Integer.parseInt(br.readLine());
            int h=Integer.parseInt(br.readLine());
            br.close();

            if(d.equals("Easy") && s>e) e=s;
            if(d.equals("Medium") && s>m) m=s;
            if(d.equals("Hard") && s>h) h=s;

            BufferedWriter bw=new BufferedWriter(new FileWriter(path));
            bw.write(e+"\n"+m+"\n"+h);
            bw.close();

        } catch(Exception ex){}
    }
}