package progPCCP4;
/**
 * programers: PCPP 기출문제 4번 / 수식 복원하기
 * https://school.programmers.co.kr/learn/courses/30/lessons/340210
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ProgPCCP4 {
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

class Solution {
    public String[] solution(String[] expressions) {
        FormationDto formationDto = new FormationDto(2, false);

        formationDto = setFormation(expressions, formationDto);

        System.out.println(formationDto.formation.toString() + " " + formationDto.isClear.toString());

        String [] answer = {};

        answer = calcX(expressions, formationDto);

        return answer;
    }

    class FormationDto {
        Integer formation;
        Boolean isClear;

        public FormationDto(Integer formation, Boolean isClear) {
            this.formation = formation;
            this.isClear = isClear;
        }
    }

    private FormationDto setFormation(String[] expressions, FormationDto formationDto) {
        for (String expression : expressions) {
            String[] expSplitArr = expression.split(" ");
            String c = new StringBuffer(expSplitArr[4]).reverse().toString();

            // [0]:14, [1]:+, [2]:3, [3]:=, [4]:17
            String a = new StringBuffer(expSplitArr[0]).reverse().toString();
            String b = new StringBuffer(expSplitArr[2]).reverse().toString();
            String op = expSplitArr[1];
            
            int maxLen = Math.max(Math.max(a.length(), b.length()), c.length());

            a = a + "0".repeat(maxLen - a.length());
            b = b + "0".repeat(maxLen - b.length());
            c = c + "0".repeat(maxLen - c.length());

            for (int i = 0; i < maxLen; i++) {
                int rawA = Character.getNumericValue(a.charAt(i));
                int rawB = Character.getNumericValue(b.charAt(i));
                int rawC = Character.getNumericValue(c.charAt(i));

                if (rawA + 1 > formationDto.formation) formationDto.formation = rawA + 1;
                if (rawB + 1 > formationDto.formation) formationDto.formation = rawB + 1;

                if (c.contains("X")) continue;

                if (rawC + 1 > formationDto.formation) formationDto.formation = rawC + 1;

                if (op.equals("+")) {  // +
                    if (rawA + rawB != rawC) {
                        // rawA + rawB - formation = rawC
                        formationDto.formation = rawA + rawB - rawC;
                        formationDto.isClear = true;
                        break;
                    }
                } else {    // -
                    if (rawA < rawB) {
                        // formation + (rawA - rawB) = rawC
                        formationDto.formation = rawC - rawA + rawB;
                        formationDto.isClear = true;
                        break;
                    }
                }
            }

            if (formationDto.formation == 9) {
                formationDto.isClear = true;
                break;
            }

        }

        return formationDto;
    }

    private String[] calcX(String[] expressions, FormationDto formationDto) {
        List<String> answerList = new ArrayList<>();
        
        for (String expression: expressions) {
            String[] expSplitArr = expression.split(" ");
            String c = expSplitArr[4];
            if (!c.equals("X")) continue;

            // [0]:14, [1]:+, [2]:3, [3]:=, [4]:17
            String a = new StringBuffer(expSplitArr[0]).reverse().toString();
            String b = new StringBuffer(expSplitArr[2]).reverse().toString();
            String op = expSplitArr[1];

            String originA = new StringBuffer(a).reverse().toString();
            String originB = new StringBuffer(b).reverse().toString();                

            StringBuilder cSb = new StringBuilder();
            if (formationDto.isClear) {
                int intA = 0;
                int intB = 0;

                for (int i = 0; i < a.length(); i++) {
                    intA += Character.getNumericValue(a.charAt(i)) * Math.pow(formationDto.formation, i);
                }
                for (int i = 0; i < b.length(); i++) {
                    intB += Character.getNumericValue(b.charAt(i)) * Math.pow(formationDto.formation, i);
                }

                int intC = op.equals("+") ? intA + intB : intA - intB;

                int tempC = intC;
                while (tempC > 0) {
                    cSb.append(tempC % formationDto.formation);
                    tempC = tempC / formationDto.formation;
                }
            } else {
                int maxLen = Math.max(a.length(), b.length());

                a = a + "0".repeat(maxLen - a.length());
                b = b + "0".repeat(maxLen - b.length());
                c = c + "0".repeat(maxLen - c.length());

                for (int i = 0; i < maxLen; i++) {
                    int rawA = Character.getNumericValue(a.charAt(i));
                    int rawB = Character.getNumericValue(b.charAt(i));

                    Integer resultC = op.equals("+") ? rawA + rawB : rawA - rawB;
                    if (resultC >= formationDto.formation || resultC < 0) {
                        cSb = new StringBuilder("?");
                        break;
                    }
                    cSb.append(resultC.toString());
                }
            }
            cSb.reverse();

            answerList.add(
                originA + " " +
                op + " " +
                originB + " = " +
                cSb.toString()
            );
        }

        return answerList.toArray(new String[0]);
    }
}

class Solution2 {
    public ArrayList<String> solution(String[] expressions) {
        boolean[] possible = new boolean[10];
        for(int base=2; base<=9; base++) possible[base] = true;
        ArrayList<String> answer = new ArrayList<>();

        for(String exp : expressions) {
            int minBase = minPossibleBase(exp);
            while(minBase-- > 2) possible[minBase] = false;

            if(exp.charAt(exp.length()-1) == 'X') answer.add(exp);
            else for(int base=2; base<=9; base++) if(possible[base] && !isPossible(exp, base)) possible[base] = false;
        }

        for(int i=0; i<answer.size(); i++) {
            Set<String> result = new HashSet<>();
            for(int base=2; base<=9; base++) if(possible[base]) result.add(calcExp(answer.get(i), base));

            if(result.size() == 1) answer.set(i, answer.get(i).replace("X", result.iterator().next()));
            else answer.set(i, answer.get(i).replace('X', '?'));
        }

        return answer;
    }

    private int minPossibleBase(String exp) {
        int max = 0;
        for(char c : exp.toCharArray()) if('0' <= c && c <= '9') max = Math.max(max, c-'0');
        return max+1;
    }

    private boolean isPossible(String exp, int base) {
        return exp.substring(exp.lastIndexOf(' ')+1).equals(calcExp(exp, base));
    }

    private String calcExp(String exp, int base) {
        StringTokenizer tk = new StringTokenizer(exp);

        int result = Integer.parseInt(tk.nextToken(), base);
        if(tk.nextToken().equals("+")) result += Integer.parseInt(tk.nextToken(), base);
        else result -= Integer.parseInt(tk.nextToken(), base);

        return Integer.toString(result, base);
    }
}