package WORDJUMBLE.service;

import java.io.*;

public class FileHighScoreService implements HighScoreService {

    String file = "WORDJUMBLE/data/wordjumble_highscore.txt";

    public int[] loadAll() {
        int[] hs = {0,0,0};

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            hs[0] = Integer.parseInt(br.readLine());
            hs[1] = Integer.parseInt(br.readLine());
            hs[2] = Integer.parseInt(br.readLine());
            br.close();
        } catch(Exception e){}

        return hs;
    }

    public int getHighScore(String level) {
        int[] hs = loadAll();

        switch(level) {
            case "Easy": return hs[0];
            case "Medium": return hs[1];
            default: return hs[2];
        }
    }

    public void saveHighScore(String level, int score) {
        int[] hs = loadAll();

        if(level.equals("Easy")) hs[0] = Math.max(hs[0], score);
        else if(level.equals("Medium")) hs[1] = Math.max(hs[1], score);
        else hs[2] = Math.max(hs[2], score);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(hs[0] + "\n" + hs[1] + "\n" + hs[2]);
            bw.close();
        } catch(Exception e){}
    }
}