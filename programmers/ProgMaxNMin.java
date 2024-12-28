/**
 * programers: 최대값과 최솟값
 * https://school.programmers.co.kr/learn/courses/30/lessons/12939?language=java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ProgMaxNMin {
    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        String[] rawParsedInput = input.split(",");

        List<String> stripInputList = new ArrayList<>();
        for (String parsedString : rawParsedInput) {
            stripInputList.add(
                parsedString.replace("[", "").replace("]", "").replace("\"", "").trim()
            );
        }

        String[] answer = solution.solution(stripInputList.toArray(new String[0]));

        for (String str : answer) System.out.println(str);
    }
}
