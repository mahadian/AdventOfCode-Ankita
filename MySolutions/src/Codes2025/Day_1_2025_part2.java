package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day_1_2025_part2 {

    public static void getDialOpened() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Codes2025.Day_1_2025"));
        int count = 0;
        int currentPos = 50;

        for (String line : lines) {

            String direction = line.substring(0, 1);
            int distance = Integer.parseInt(line.substring(1));

            if (direction.equals("L")) {
                for (int i = 0; i < distance; i++) {
                    currentPos = currentPos - 1;
                    if (currentPos < 0) {
                        currentPos = 99;
                    }
                    if (currentPos == 0) {
                        count++;
                    }
                }
            }

            if (direction.equals("R")) {
                for (int i = 0; i < distance; i++) {
                    currentPos = currentPos + 1;
                    if (currentPos > 99) {
                        currentPos = 0;
                    }
                    if (currentPos == 0) {
                        count++;
                    }
                }
            }

        }

        System.out.println("Dial opened " + count + " times.");
    }
}
