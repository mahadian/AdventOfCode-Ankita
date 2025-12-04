package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_2_2025 {

    public void sumOfInvalidInputs() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Codes2025.Day_2_2025")); //Codes2025.Day_2_2025
        String[] ranges = lines.getFirst().split(",");
        List<Long> invalidInputs = new ArrayList<>();

        for(String range: ranges) {
            String[] bounds = range.split("-");
            long lowerBound = Long.parseLong(bounds[0]); //11
            long upperBound = Long.parseLong(bounds[1]); //22

            // for every number in the range
            for(long i = lowerBound; i <= upperBound; i++) {
                // divide the num (i) in half (by taking its len)
                // check if both are same
                // if yes, print the number as invalid input
                String num = String.valueOf(i); // 11885 11885
                int len = num.length();
                if(len % 2 == 0){  // skip odd length numbers
                    String firstHalf = num.substring(0, len/2);
                    String secondHalf = num.substring((len + 1)/2);
                    if(firstHalf.equals(secondHalf)) {
                        invalidInputs.add(i);
                        System.out.println("Found invalid num : "+i);
                    }
                }
            }
        }
        System.out.println("Sum of invalid inputs: "+invalidInputs.stream().mapToLong(Long::longValue).sum());
    }

    public void sumOfInvalidInputsPart2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/test")); //Codes2025.Day_2_2025
        String[] ranges = lines.getFirst().split(",");
        List<Long> invalidInputs = new ArrayList<>();

        for(String range: ranges) {
            System.out.println("#### Considering range : " + range);
            String[] bounds = range.split("-");
            long lowerBoundLong = Long.parseLong(bounds[0]);
            long upperBoundLong = Long.parseLong(bounds[1]);

            int lengthOfUpperBound = bounds[1].length();
            // get the divisors of this length - assuming max length is 9
            List<Integer> divisors = getDivisors(lengthOfUpperBound);

            List<Integer> divisorsLowerBound = new ArrayList<>(divisors);
            if (lengthOfUpperBound != bounds[0].length()) {
                if(!getDivisors(bounds[0].length()).isEmpty()) {
                    divisorsLowerBound.add(getDivisors(bounds[0].length()).getFirst());
                }
            }
            divisors = removeDuplicatesFromList(divisorsLowerBound);

            // for every divisor
            // check if the number can be split into equal parts of that divisor length
            // If yes, add to invalid inputs

            if (divisors.isEmpty()) {
                System.out.println("No divisors found for length: " + lengthOfUpperBound);
                continue;
            }

            if (lengthOfUpperBound <= 4 && bounds[0].length() <= 4) { // 99 - 117, 995 - 1005
                for (long i = lowerBoundLong; i <= upperBoundLong; i++) {
                    String num = String.valueOf(i);
                    if (num.length() == 2) {
                        if (i % 10 == i / 10) { // for 2 digit nums
                            invalidInputs.add(i);
                            System.out.println("Found invalid num : " + i + " for divisor: 2");
                        }
                    } else if(num.length() == 3) {
                        if(num.charAt(0) == num.charAt(1) && num.charAt(1) == num.charAt(2)){
                            invalidInputs.add(i);
                            System.out.println("Found invalid num : " + i + " for divisor: 3");
                        }
                    } else {
                        String firstHalf = num.substring(0, lengthOfUpperBound/2);
                        String secondHalf = num.substring((lengthOfUpperBound + 1)/2);
                        if(firstHalf.equals(secondHalf)) {
                            invalidInputs.add(i);
                            System.out.println("Found invalid num : " + i + " for divisor: 4");
                        }
                    }
                }
            } else {
                // main loop for other ranges
                for (long i = lowerBoundLong; i <= upperBoundLong; i++) {
                    String num = String.valueOf(i);
                    int len = num.length();

                    // iterate all divisors for this number
                    for (int d : divisors) {
                        // skip numbers whose length cannot be divided evenly by divisor
                        if (len % d != 0) continue;

                        String firstPart = num.substring(0, d);
                        boolean allEqual = true;
                        for (int start = d; start < len; start += d) {
                            if (!num.substring(start, start + d).equals(firstPart)) {
                                allEqual = false;
                                break;
                            }
                        }
                        if (allEqual) {
                            if (!invalidInputs.contains(i)) {
                                invalidInputs.add(i);
                                System.out.println("Found invalid num : " + i + " (divisor " + d + ")");
                            }
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Sum of invalid inputs : " + invalidInputs.stream().mapToLong(Long::longValue).sum());
    }


    // Gives correct answer!!! :)
    public void sumOfInvalidInputsAgain() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("MySolutions/resources/Day_2_2025")); //Day_2_2025
        String[] ranges = lines.getFirst().split(",");
        List<Long> invalidInputs = new ArrayList<>();

        for (String range : ranges) {
            String[] bounds = range.split("-");
            long lowerLimit = Long.parseLong(bounds[0]);
            long upperLimit = Long.parseLong(bounds[1]);
            int lowerLimitLength = bounds[0].length();
            int upperLimitLength = bounds[1].length();

            if(lowerLimitLength==upperLimitLength){
                // same length = same divisors
                if(lowerLimitLength==1){
                    // there will be no repetition
                    continue;
                } else if(!isPrimeNumber(lowerLimitLength)) {
                    List<Integer> allDivisors = getDivisors(lowerLimitLength);
                    // check repetition for each divisor
                    checkRepeatedDigitsForDivisorLength(lowerLimit, upperLimit, allDivisors, invalidInputs);
                } else {
                    // if it is a prime num, it will only have single digit repetition
                    checkRepeatedDigitsInPrimeLength(lowerLimit, upperLimit, invalidInputs);
                }
            } else if (lowerLimitLength<upperLimitLength){
                if(lowerLimitLength==1){
                    // there will be no repetition for single digit numbers
                    // check for repetition for 2 digit numbers (which is only 11)
                    invalidInputs.add(Long.parseLong(String.valueOf(11)));

                } else if(isPrimeNumber(lowerLimitLength) && isPrimeNumber(upperLimitLength)){
                    // both are prime (2 and 3)
                    // find new limit
                    // check prime length repetition for both ranges
                    long lengthLimit = findNewUpperLimit(lowerLimitLength, lowerLimit, upperLimit);
                    checkRepeatedDigitsInPrimeLength(lowerLimit, lengthLimit, invalidInputs);
                    checkRepeatedDigitsInPrimeLength(lengthLimit + 1, upperLimit, invalidInputs);

                } else if(isPrimeNumber(lowerLimitLength)) {
                    // find new limit
                    // check repeated digits until that new limit
                    // for rest of the range, check for upper limit length divisors
                    long lengthLimit = findNewUpperLimit(lowerLimitLength, lowerLimit, upperLimit);
                    checkRepeatedDigitsInPrimeLength(lowerLimit, lengthLimit, invalidInputs);
                    List<Integer> divisorsOfUpperLimitLength = getDivisors(upperLimitLength);
                    checkRepeatedDigitsForDivisorLength(lengthLimit + 1, upperLimit, divisorsOfUpperLimitLength, invalidInputs);

                } else if(isPrimeNumber(upperLimitLength)){
                    // find new limit
                    // check divisor length repetition until that new limit
                    // for rest of the range, check for prime length repetition
                    long lengthLimit = findNewUpperLimit(lowerLimitLength, lowerLimit, upperLimit);
                    List<Integer> divisorsOfLowerLimitLength = getDivisors(lowerLimitLength);
                    checkRepeatedDigitsForDivisorLength(lowerLimit, lengthLimit, divisorsOfLowerLimitLength, invalidInputs);
                    checkRepeatedDigitsInPrimeLength(lengthLimit + 1, upperLimit, invalidInputs);

                } else {
                    // none are prime (8 and 9 lengths)
                    List<Integer> divisorsOfLowerLimitLength = new ArrayList<>();
                    List<Integer> divisorsOfUpperLimitLength = new ArrayList<>();

                    divisorsOfLowerLimitLength.add(getDivisors(lowerLimitLength).getFirst());
                    divisorsOfUpperLimitLength.add(getDivisors(upperLimitLength).getFirst());

                    // find the new upperLimit having length same as lowerLimit
                    long newUpperLimitWrtLowerLimit = findNewUpperLimit(lowerLimitLength, lowerLimit, upperLimit);

                    // check repetition for each divisor
                    checkRepeatedDigitsForDivisorLength(lowerLimit, newUpperLimitWrtLowerLimit, divisorsOfLowerLimitLength, invalidInputs);
                    checkRepeatedDigitsForDivisorLength(newUpperLimitWrtLowerLimit+1, upperLimit, divisorsOfUpperLimitLength, invalidInputs);
                }
            } else {
                System.out.println("Invalid range : " + range);
            }
        }

        System.out.println("Sum of invalid inputs : " + invalidInputs.stream().mapToLong(Long::longValue).sum());
    }

    private long findNewUpperLimit(int lowerLimitLength, long lowerLimit, long upperLimit) {
        long newLimit = lowerLimit, i = lowerLimit;
        while(i<upperLimit){
            if(String.valueOf(i).length() > lowerLimitLength){
                newLimit = i;
                break; // assuming there would be only 1-step increase in length (88 - 555 and not 88 - 5555)
            }
            i++;
        }
        return newLimit-1;
    }

    private void checkRepeatedDigitsForDivisorLength(long lowerLimit, long upperLimit, List<Integer> allDivisors, List<Long> invalidInputs) {
        for (long i = lowerLimit; i <= upperLimit; i++) {
            String num = String.valueOf(i);
            int len = num.length();

            // iterate all divisors for num
            for (int d : allDivisors) {
                String firstPart = num.substring(0, d);
                boolean allEqual = true;
                for (int start = d; start < len; start += d) {
                    if (!num.substring(start, start + d).equals(firstPart)) {
                        allEqual = false;
                        break;
                    }
                }
                if (allEqual) {
                    invalidInputs.add(i);
                    System.out.println("Found invalid num : " + i + " of divisor length " + d);
                    break;
                }
            }
        }
    }

    private void checkRepeatedDigitsInPrimeLength(long lowerLimit, long upperLimit, List<Long> invalidInputs) {
        for(long i=lowerLimit;i<=upperLimit;i++) {
            String num = String.valueOf(i);
            boolean allSame = true;
            char firstChar = num.charAt(0);
            for(int j=1;j<num.length();j++) {
                if(num.charAt(j) != firstChar) {
                    allSame = false;
                    break;
                }
            }
            if(allSame) {
                invalidInputs.add(i);
                System.out.println("Found invalid num of prime length : " + i);
            }
        }
    }

    private boolean isPrimeNumber(int lowerLimitLength) {
        if(lowerLimitLength <= 1) return false;
        for(int i=2; i<=Math.sqrt(lowerLimitLength); i++) {
            if(lowerLimitLength % i == 0) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> removeDuplicatesFromList(List<Integer> divisors) {
        List<Integer> uniqueDivisors = new ArrayList<>();
        for(Integer divisor: divisors) {
            if(!uniqueDivisors.contains(divisor)) {
                uniqueDivisors.add(divisor);
            }
        }
        return uniqueDivisors;
    }

    private static List<Integer> getDivisors(int lengthOfBounds) {
        List<Integer> divisors = new ArrayList<>();
        for(int i = 2; i <= lengthOfBounds/2; i++) {
            if(lengthOfBounds % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }
}


// 33832678380 is correct