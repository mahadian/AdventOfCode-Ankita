package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day_4_2025 {

    public void countAccessiblePaperRolls() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("MySolutions/resources/Day_4_2025")); //Day_4_2025

        // Determine the number of maxRows and the maximum number of columns across all lines
        int maxRows = lines.size();
        int maxCols = 0;
        for (String line : lines) {
            if (line != null) {
                maxCols = Math.max(maxCols, line.length());
            }
        }

        // Initialize the 2D array with proper dimensions
        String[][] diagram = new String[maxRows][maxCols];

        // Fill the diagram, initializing each row before use
        int r = 0;
        for (String line : lines) {
            for (int c = 0; c < maxCols; c++) {
                if (line != null) {
                    diagram[r][c] = String.valueOf(line.charAt(c));
                }
            }
            r++;
        }

//        int count = findAccessibleRolls(diagram, maxRows, maxCols);
//        System.out.println("Total accessible paper rolls: " + count);
    }

    // getting 7024 which is too low :(
    public void countAccessiblePaperRollsAgain() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("MySolutions/resources/test")); //Day_4_2025

        // Determine the number of maxRows and the maximum number of columns across all lines
        int maxRows = lines.size();
        int maxCols = 0;
        for (String line : lines) {
            if (line != null) {
                maxCols = Math.max(maxCols, line.length());
            }
        }

        // Initialize the 2D array with proper dimensions
        String[][] diagram = new String[maxRows][maxCols];

        // Fill the diagram, initializing each row before use
        int r = 0;
        for (String line : lines) {
            for (int c = 0; c < maxCols; c++) {
                if (line != null) {
                    diagram[r][c] = String.valueOf(line.charAt(c));
                }
            }
            r++;
        }

        int count = 0;
        while(true) {
            // get the list of visited positions
            // update the diagram to mark those positions as .
            // repeat until no more accessible rolls found
            Map<Integer, List<String>> result = findAccessibleRolls(diagram, maxRows, maxCols);
            count = count + result.keySet().iterator().next();
            List<String> visitedPositions = result.get(count);
            if(visitedPositions == null) {
                break;
            }
            for(String pos : visitedPositions) {
                String[] indices = pos.split(",");
                int row = Integer.parseInt(indices[0]);
                int col = Integer.parseInt(indices[1]);
                diagram[row][col] = ".";
            }
        }

        System.out.println("Total accessible paper rolls: " + count);
    }

    private Map<Integer,List<String>> findAccessibleRolls(String[][] diagram, int maxRows, int maxCols) {
        int count = 0;
        List<String> visitedPositions = new ArrayList<>();
        List<Integer> adjacentValues = new ArrayList<>();
        // for every row, locate @
        // check all its 8 surrounding values one by one
        // if you find . then add 0 to a adjacentValues list
        // if you find @ then check its adjacent positions
        // if adjacent valu is @ 4 times then skip otherwise increment count
        for(int row = 0; row < maxRows; row++) {
            for(int col = 0; col < maxCols; col++) {
                if(diagram[row][col].equals("@")) {
                    // check 8 directions
                    adjacentValues.clear();
                    // top-left
                    adjacentValues.add(checkDirection(diagram, row, col, -1, -1, maxRows, maxCols, false, false));
                    // top
                    adjacentValues.add(checkDirection(diagram, row, col, -1, 0, maxRows, maxCols, false, true));
                    // top-right
                    adjacentValues.add(checkDirection(diagram, row, col, -1, 1, maxRows, maxCols, false, false));
                    // left
                    adjacentValues.add(checkDirection(diagram, row, col, 0, -1, maxRows, maxCols, true, false));
                    // right
                    adjacentValues.add(checkDirection(diagram, row, col, 0, 1, maxRows, maxCols, true, false));
                    // bottom-left
                    adjacentValues.add(checkDirection(diagram, row, col, 1, -1, maxRows, maxCols, false, false));
                    // bottom
                    adjacentValues.add(checkDirection(diagram, row, col, 1, 0, maxRows, maxCols,false, true));
                    // bottom-right
                    adjacentValues.add(checkDirection(diagram, row, col, 1, 1, maxRows, maxCols, false, false));

                    // count number of 1s in adjacentValues and check if it's not more than 4
                    int ones = java.util.Collections.frequency(adjacentValues, 1);
                    if(ones <= 4) {
                        count++;
                        visitedPositions.add(row + "," + col);
                        System.out.println("Found accessible paper roll at [" + row + ", " + col + "]");
                    }
                }
            }
        }
        return Map.of(count, visitedPositions);
    }

    private Integer checkDirection(String[][] diagram, int row, int col, int i, int j, int maxRows, int maxCols, boolean b, boolean b1) {
        int adjacentAtCount = 0;
        int r = row + i;
        int c = col + j;
        if(r >= 0 && r < maxRows && c >= 0 && c < maxCols){
            if(diagram[r][c].equals("@")){
                adjacentAtCount++;
            }
        }
        return adjacentAtCount;
    }
}
