/**
 * programers: 최솟값 만들기
 * https://school.programmers.co.kr/learn/courses/30/lessons/12941
 */

package makeMin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MakeMin {
    public static void main(String args[]) throws IOException {
        Solution solution = new Solution();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String[] inputStrA = br.readLine().split(",");
        int[] A = new int[inputStrA.length];
        for (int i = 0; i < inputStrA.length; i++) {
            A[i] = Integer.parseInt(inputStrA[i].replace("[", "").replace("]", "").trim());
        }

        String [] inputStrB = br.readLine().split(",");
        int[] B = new int[inputStrB.length];
        for (int i = 0; i < inputStrB.length; i++) {
            B[i] = Integer.parseInt(inputStrB[i].replace("[", "").replace("]", "").trim());
        }

        System.out.println(solution.solution(A, B));
    }
}

class Solution {
    public int solution(int []A, int []B) {
        Arrays.sort(A);
        Arrays.sort(B);

        int answer = 0;

        for (int i = 0; i < A.length; i++) {
            answer += A[i] * B[B.length - i - 1];
        }

        return answer;
    }
}