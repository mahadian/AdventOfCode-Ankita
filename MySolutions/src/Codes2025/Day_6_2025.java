package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_6_2025 {

    int[][] mathArray = new int[4][3747];
    String[][] mathArrayStr = new String[5][3749];
    List<String> operators = new ArrayList<>();

    private void initializeMathArray() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("MySolutions/resources/Day_6_2025")); //Day_6_2025

        int row = 0;
        for(String line: lines) {
            int col = 0;
            line = line.trim().replaceAll("\\s+", "-");
            String[] rowStr = line.split("-");
            for(String value : rowStr) {
                if(value.equals("*") || value.equals("+")) {
                    operators.add(value);
                } else {
                    mathArray[row][col] = Integer.parseInt(value);
                }
                col++;
            }
            row++;
        }
    }

    private void initializeMathArrayAgain() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("MySolutions/resources/Day_6_2025")); //Day_6_2025
        // traverse column wise
        // create a 2D array and add values by replacing " " with a null or blank value
        // then traverse column wise
        // and perform operations based on the operator
        for(int row = 0; row <=4; row++) {
            int col = 0;
            char[] arr =  lines.get(row).toCharArray();
            for (char c : arr) {
                mathArrayStr[row][col] = String.valueOf(c);
                col++;
            }
        }
        printMathArray();
    }

    private void printMathArray() {
        for(int row = 0; row <=4; row++) {
            for(int col = 0; col < mathArrayStr[0].length; col++) {
                System.out.print(mathArrayStr[row][col]);
            }
            System.out.println();
        }
    }

    public void doCephalopodMath() throws IOException {
        initializeMathArray();
        List<Long> totalsList = new ArrayList<>();
        int col = 0;

        for(String operation : operators) {
            long totalAnswer = 0;
            if(operation.equals("+")) {
                for(int row = 0; row <=3; row++) {
                    totalAnswer = totalAnswer + mathArray[row][col];
                }
            } else { //if(operation.equals("*"))
                totalAnswer = 1;
                for(int row = 0; row <=3; row++) {
                    totalAnswer = totalAnswer * mathArray[row][col];
                }
            }
            col++; // next column for next operation
            totalsList.add(totalAnswer);
            System.out.println("Performed " + operation + "operation for column " + col + ", current totalAnswer: " + totalAnswer);
        }


        System.out.println("Total = " + totalsList.stream().mapToLong(Long::longValue).sum());
    }

    public void doCephalopodMathAgain() throws IOException {
        initializeMathArrayAgain();
        List<Long> totalsList = new ArrayList<>();

        for(int col = 0; col<3748; col++) {
            int prevCol = col;
            while(!isItATrueGap(col)){
                col++;
            }
            totalsList.add(performOperationsOnSubArray(col, prevCol));
        }


        System.out.println("Total = " + totalsList.stream().mapToLong(Long::longValue).sum());
    }

    private boolean isItATrueGap(int column) {
        if(mathArrayStr[0][column] == null) {
            System.out.println("Reached end of column");
            return true;
        }
        boolean flag = false;
        for(int row = 0; row <=3; row++) {
            if(mathArrayStr[row][column].equals(" ")) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private long performOperationsOnSubArray(int currentCol, int prevCol) {

        List<Integer> numberList = new ArrayList<>();
        long totalAnswer = 0;

        for(int i = prevCol;i<currentCol;i++) {
            String number = "";
            for(int j=0;j<=3;j++) {
                if(!mathArrayStr[j][i].equals(" ")) {
                    number = number + mathArrayStr[j][i];
                }
            }
            numberList.add(Integer.parseInt(number));
        }

        // get operator
        String operation = "";
        for(int col = prevCol; col<currentCol; col++) {
            if(mathArrayStr[4][col].equals("+") || mathArrayStr[4][col].equals("*")) {
                operation = mathArrayStr[4][col];
                break;
            }
        }

        if(!numberList.isEmpty()) {
            if(operation.equals("+"))  {
                for(Integer num : numberList) {
                    totalAnswer = totalAnswer + num;
                }
            } else { //if(operation.equals("*"))
                totalAnswer = 1;
                for(Integer num : numberList) {
                    totalAnswer = totalAnswer * num;
                }
            }
        }

        return totalAnswer;
    }
}
