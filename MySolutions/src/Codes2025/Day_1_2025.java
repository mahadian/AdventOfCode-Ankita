package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day_1_2025 {

    public static void getDialOpened() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/test")); //Codes2025.Day_1_2025
        int count = 0;
        int currentPos = 50;

        for(String line : lines) {
            // extract the data
            String direction = line.substring(0,1);
            int distance = Integer.parseInt(line.substring(1));

            if(direction.equals("L")) {
                currentPos = currentPos-distance;
                while(currentPos < 0) {
                    currentPos = 100 + currentPos;
                }
            }
            else if(direction.equals("R")) {
                currentPos = currentPos+distance;
                while(currentPos > 99) {
                    currentPos = currentPos - 100;
                }
            }

            if(currentPos==0) {
                count++;
            }
        }
        System.out.println("Dial opened " + count + " times.");
    }
}
