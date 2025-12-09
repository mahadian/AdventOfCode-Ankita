package Codes2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day_9_2025 {

    List<Long> areasList = new ArrayList<>();

    public void getMaxAreaBetweenRedTiles() throws IOException {
        List<String> vertices = Files.readAllLines(Paths.get("MySolutions/resources/Day_9_2025")); //Day_9_2025

        for (int i = 0; i < vertices.size(); i++) {
            String vertexStr = vertices.get(i);
            long point_1_x = Long.parseLong(vertexStr.split(",")[0]);
            long point_1_y = Long.parseLong(vertexStr.split(",")[1]);
            for(int j = i+1; j<vertices.size()-1; j++) {
                long point_2_x = Long.parseLong(vertices.get(j).split(",")[0]);
                long point_2_y = Long.parseLong(vertices.get(j).split(",")[1]);
                //calculate area
                long area = (Math.abs(point_1_x - point_2_x) + 1) * (Math.abs(point_1_y - point_2_y) + 1);
                areasList.add(area);
            }
        }

       System.out.println(areasList.stream().max(Long::compareTo));
    }
}
