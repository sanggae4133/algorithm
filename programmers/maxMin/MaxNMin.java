package maxMin;
/**
 * programers: 최대값과 최솟값
 * https://school.programmers.co.kr/learn/courses/30/lessons/12939?language=java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MaxNMin {
    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        
        System.out.println(solution.solution(input));
    }
}

class Solution {
    public String solution(String s) {
        String[] splitStr = s.split(" ");

        int max = Integer.parseInt(splitStr[0]);
        int min = max;

        for (int i = 0; i < splitStr.length; i++) {
            int current = Integer.parseInt(splitStr[i]);
            if (max < current) max = current;
            if (min > current) min = current;
        }
        return Integer.toString(min) + " " + Integer.toString(max);
    }
}