package WORDJUMBLE.generator;

import java.util.*;

public class HardWordGenerator implements WordGenerator {

    String[] words = {"people","system","family","school","friend","doctor","police"};
    Random r = new Random();

    public String getWord() {
        return words[r.nextInt(words.length)];
    }

    public String shuffle(String word) {
        char[] arr = word.toCharArray();

        for (int i = 0; i < arr.length - 2; i++) {
            int j = r.nextInt(arr.length);
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return new String(arr);
    }
}