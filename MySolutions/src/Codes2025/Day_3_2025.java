package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_3_2025 {

    public void totalJoltage() throws IOException {
        List<String> banks = Files.readAllLines(Paths.get("2024/resources/Codes2025.Day_3_2025")); //Codes2025.Day_3_2025
        List<Integer> joltages = new ArrayList<>();

        for(String bank : banks) {
            int firstDigit = Integer.parseInt(String.valueOf(bank.charAt(0)));
            int secondDigit = 1;
            int index = 0;
            String firstMax = findFirstMax(bank, firstDigit, index);
            firstDigit = Integer.parseInt(firstMax.split("-")[0]);
            index = Integer.parseInt(firstMax.split("-")[1]);
            System.out.println("Found first Digit: " + firstDigit + " at Index: " + index);

            if(index==bank.length()-1) {
                // if max element is at the end
                // this first max will become second digit
                // find the first digit (max) from the prev partition
                secondDigit = firstDigit;
                firstDigit = findMaxFromLeftPartition(bank.substring(0,index));
                // form the number
                int finalNum = Integer.parseInt("" + firstDigit + secondDigit);
                System.out.println("*****************Formed number****************** = " + finalNum);
                joltages.add(finalNum);
            } else {
                // if max element is somewhere in the middle or at the beginning
                // find the second digit from the right partition
                secondDigit = findMaxFromRightPartition(bank.substring(index+1));
                // form the number
                int finalNum = Integer.parseInt("" + firstDigit + secondDigit);
                System.out.println("*****************Formed number****************** = " + finalNum);
                joltages.add(finalNum);
            }
        }

        System.out.println("Sum of joltages: " + joltages.stream().mapToInt(Integer::intValue).sum());

    }

    private int findMaxFromRightPartition(String bank) {
        int max = Integer.parseInt(String.valueOf(bank.charAt(0)));
        for(int i = 1; i<bank.length(); i++) {
            int bankNum = Integer.parseInt(String.valueOf(bank.charAt(i)));
            if(bankNum > max){
                max = bankNum;
            }
        }
        return max;
    }

    private int findMaxFromLeftPartition(String bank) {
        int max = Integer.parseInt(String.valueOf(bank.charAt(0)));
        for(int i = 1; i<bank.length(); i++) {
            int bankNum = Integer.parseInt(String.valueOf(bank.charAt(i)));
            if(bankNum > max){
                max = bankNum;
            }
        }
        return max;
    }

    private String findFirstMax(String bank, int max, int index) {
        for(int i = index; i<bank.length(); i++) {
            int bankNum = Integer.parseInt(String.valueOf(bank.charAt(i)));
            if(bankNum > max){
                max = bankNum;
                index = i;
            }
        }
        return max + "-" + index;
    }
}
