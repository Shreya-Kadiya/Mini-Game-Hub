package WORDJUMBLE.generator;

import java.util.*;

/*
 * OOP CONCEPT: POLYMORPHISM IMPLEMENTATION
 */

public class EasyWordGenerator implements WordGenerator {

    String[] words = {"apple","ball","cat","dog","sun"};
    Random r = new Random();

    public String getWord() {
        return words[r.nextInt(words.length)];
    }

    public String shuffle(String word) {
        char[] arr = word.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int j = r.nextInt(arr.length);
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return new String(arr);
    }
}