package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_5_2025 {

    List<String> ranges = new ArrayList<>();
    List<String> availableIngredients = new ArrayList<>();

    private void extractIngredientsDatabase() throws IOException {
        int blankLineIndex = 0;

        // get proper data from file
        List<String> allLines = Files.readAllLines(Paths.get("MySolutions/resources/Day_5_2025")); // Day_5_2025
        for (String line : allLines) {
            if (line.trim().isEmpty()) {
                blankLineIndex = allLines.indexOf(line);
                break;
            }
            ranges.add(line);
        }

        if(blankLineIndex!=0 && blankLineIndex+1<allLines.size()){
            for(int i=blankLineIndex+1; i<allLines.size(); i++){
                availableIngredients.add(allLines.get(i));
            }
        }

        // sort the ranges based on lower limit
        ranges.sort((a, b) -> {
            long aLower = Long.parseLong(a.split("-")[0]);
            long bLower = Long.parseLong(b.split("-")[0]);
            return Long.compare(aLower, bLower);
        });
    }

    public int returnFreshIngredientsCount() throws IOException {
        List<Long> freshIngredientsList = new ArrayList<>();
        extractIngredientsDatabase();

        // for very ingredient in availableIngredients
        // loop through ranges
        // check if it lies in a given range
        // if yes, then update freshCount by 1
        // break and check next ingredient
        for(String ingredientStr : availableIngredients) {
            long ingredient = Long.parseLong(ingredientStr);
            System.out.println("\nChecking for ingredient " + ingredient);
            for(String range : ranges) {
                long lowerLimit = Long.parseLong(range.split("-")[0]);
                long upperLimit = Long.parseLong(range.split("-")[1]);
                if (ingredient >= lowerLimit && ingredient <= upperLimit) {
                    if (!freshIngredientsList.contains(ingredient)) {
                        freshIngredientsList.add(ingredient);
                        System.out.println("Found ingredient " + ingredient + " in range " + range);
                        break;
                    }
                }
            }
        }

        return freshIngredientsList.size();
    }

    public long returnFreshIngredientsCountBasedOnRange() throws IOException {
        extractIngredientsDatabase();

        // start with first interval
        // for each next interval:
        //   if it overlaps → extend current interval
        //   if not → add previous interval length and start a new one

        long totalCount = 0;
        long prevLowerLimit = Long.parseLong(ranges.getFirst().split("-")[0]);
        long prevUpperLimit = Long.parseLong(ranges.getFirst().split("-")[1]);
        for(int i=1; i<ranges.size(); i++) {
            long currentLowerLimit = Long.parseLong(ranges.get(i).split("-")[0]);
            long currentUpperLimit = Long.parseLong(ranges.get(i).split("-")[1]);
            System.out.println("Current range = " + currentLowerLimit + "-" + currentUpperLimit);
            if(currentLowerLimit<= prevUpperLimit) {
                // overlap
                System.out.println("Overlapping range detected = " + currentLowerLimit + "-" + currentUpperLimit);
                if(currentUpperLimit> prevUpperLimit) {
                    prevUpperLimit = currentUpperLimit;
                }
            } else {
                // no overlap
                System.out.println("No overlap");
                totalCount = totalCount + (prevUpperLimit - prevLowerLimit + 1);
                prevLowerLimit = currentLowerLimit;
                prevUpperLimit = currentUpperLimit;
            }
        }

        // at this point we have the count of non-overlapping ranges only
        // we merged the overlapping ranges and stored the limits in prevLowerLimit and prevUpperLimit
        // we need to count the IDs from that super overlapped range as well :)
        totalCount = totalCount + (prevUpperLimit - prevLowerLimit + 1);
        return totalCount;
    }
}



/*
INITIAL ALGORITHM - NOT WORKING FOR ALL CASES

        // get total count (difference) from all ranges
        // next loop : start from 2nd element
        // check if next range lower limit is inside previous range
        // if yes, then calculate difference(prev upper limit and next lower limit) and subtract it from total count

 REASON : I assumed that only the previous range can overlap with the current one wrt lower limit, there could be a higher upper limit
 if range =
10–100
20–30
40–60
then 20-30 overlaps with 10-100 and 40-60 also overlaps with 10-100

for(String range : ranges) {
            long lowerLimit = Long.parseLong(range.split("-")[0]);
            long upperLimit = Long.parseLong(range.split("-")[1]);
            totalCount = totalCount + (upperLimit - lowerLimit + 1);
        }
        System.out.println("Total count before removing overlaps = " + totalCount);

        for(int i=1; i<ranges.size(); i++) {
            long currLowerLimit = Long.parseLong(ranges.get(i).split("-")[0]);
            long prevLowerLimit = Long.parseLong(ranges.get(i-1).split("-")[0]);
            long prevUpperLimit = Long.parseLong(ranges.get(i-1).split("-")[1]);
            if(currLowerLimit >= prevLowerLimit && currLowerLimit <= prevUpperLimit) {
                long overlapCount = prevUpperLimit - currLowerLimit + 1;
                totalCount = totalCount - overlapCount;
            }
        }
 */
