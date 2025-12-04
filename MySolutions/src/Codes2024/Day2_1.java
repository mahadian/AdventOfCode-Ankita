package Codes2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2_1 {

    private static void getSafeReportsTrial() {
        // get your input
        String input = "7 6 4 2 1\n" +
                "1 2 7 8 9\n" +
                "9 7 6 2 1\n" +
                "1 3 2 4 5\n" +
                "8 6 4 4 1\n" +
                "1 3 6 7 9";


        // main logic
        long safeReportsCount = 0;
        String[] lines = input.split("\n");

        for (String line : lines) { //for each line
            line = line.replace(" ", "");
            List<Integer> levels = new ArrayList<>();

            // obtain the array
            for (int i = 0; i<line.length(); i++) {
                levels.add(Integer.parseInt(String.valueOf(line.charAt(i))));
            }

            // check if the levels are safe
//            if (checkForSafeReports(levels))
//                safeReportsCount++;
        }

        System.out.println("Total Safe reports = " + safeReportsCount);

    }

    // Total Safe reports = 479

    public static void getSafeReports() throws IOException {
        // get your input
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Day2_input"));
        List<List<Integer>> unsafeLevels = new ArrayList<>();

        // main logic
        long safeReportsCount = 0;

        for (String line : lines) { //for each line

            // obtain the levels list
            List<Integer> levels = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

            // check if the levels are safe
            if (checkForSafeReports(levels, unsafeLevels))
                safeReportsCount++;

            // check if unsafe levels could be safe by removing one level

        }

        System.out.println("Total Safe reports = " + safeReportsCount);
        //System.out.println("Unsafe levels = " + unsafeLevels);

        // go through the unsafe levels and filter out the levels with 1 bad item
        List<List<Integer>> unsafeLevelsFiltered = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        for (List<Integer> unsafeLevel : unsafeLevels) {
            for (int i = 0; i<unsafeLevel.size(); i++) {
                positions = getPositions(unsafeLevel);
                if (positions.size() == 1) {
                    unsafeLevelsFiltered.add(unsafeLevel);
                }
            }
        }
        System.out.println("Unsafe levels filtered = " + unsafeLevelsFiltered + "size = " + unsafeLevelsFiltered.size());

        // check if the filtered unsafe levels are safe by removing that bad item


    }

    private static List<Integer> getPositions(List<Integer> unsafeLevel) {
        List<Integer> positions = new ArrayList<>();
        int expectedSeriesFlag = unsafeLevel.get(0) < unsafeLevel.get(1) ? 1 : 0;

        if (expectedSeriesFlag==1) {
            for (int i = 0; i < unsafeLevel.size() - 1; i++) {
                if (unsafeLevel.get(i) + 1 != unsafeLevel.get(i + 1) || unsafeLevel.get(i) + 2 != unsafeLevel.get(i + 1) || unsafeLevel.get(i) + 3 != unsafeLevel.get(i + 1)) {
                    positions.add(i);
                }
            }
        } else {
            for (int i = 0; i < unsafeLevel.size() - 1; i++) {
                if (unsafeLevel.get(i) - 1 != unsafeLevel.get(i + 1) || unsafeLevel.get(i) - 2 != unsafeLevel.get(i + 1) || unsafeLevel.get(i) - 3 != unsafeLevel.get(i + 1)) {
                    positions.add(i);
                }
            }
        }
        return positions;
    }

    public static boolean checkForSafeReports(List<Integer> levels, List<List<Integer>> unsafeLevels) {
        boolean isSafe = true;
        int expectedSeriesFlag = levels.get(0) < levels.get(1) ? 1 : 0;

        if (expectedSeriesFlag==1) {
            // check for increasing values
            for (int i = 0; i<levels.size()-1; i++) {
                if (levels.get(i) + 1 == levels.get(i + 1) || levels.get(i) + 2 == levels.get(i + 1) || levels.get(i) + 3 == levels.get(i + 1)) {
                    continue;
                } else {
                    unsafeLevels.add(levels);
                    isSafe = false;
                    break;
                }
            }
        } else {
            // check for decreasing values
            for (int i = 0; i<levels.size()-1; i++) {
                if (levels.get(i) - 1 == levels.get(i+1) || levels.get(i) - 2 == levels.get(i+1) || levels.get(i) - 3 == levels.get(i+1)) {
                    continue;
                } else {
                    unsafeLevels.add(levels);
                    isSafe = false;
                    break;
                }
            }
        }
        return isSafe;
    }
}
