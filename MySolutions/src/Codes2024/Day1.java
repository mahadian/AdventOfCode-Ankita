package Codes2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    public static void getDistance() throws IOException {
        // get the input as list
//        List<Integer> left = Arrays.asList(3,4,2,1,3,3);
//        List<Integer> right = Arrays.asList(4,3,5,3,9,3);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Day1_input"));
        for (String line : lines) {
            String[] numbers = line.split("\\s+");
            left.add(Integer.parseInt(numbers[0]));
            right.add(Integer.parseInt(numbers[1]));
        }

        // main logic
        long total = 0;
        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);


        for(int i=0; i<left.size(); i++)
        {
            total = total + Math.abs(left.get(i) - right.get(i));
        }

        System.out.println("Distance is : " + total);

    }

    public static void getSimilarityScore() throws IOException {
        // get the input as list
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Day1_input"));
        for (String line : lines) {
            String[] numbers = line.split("\\s+");
            left.add(Integer.parseInt(numbers[0]));
            right.add(Integer.parseInt(numbers[1]));
        }

        // main logic
        long similarityScore = 0;
        for(int num : left) {
            similarityScore = similarityScore + (num * right.stream().filter(i -> i == num).count());
        }

        System.out.println("Similarity Score is : " + similarityScore);
    }
}
