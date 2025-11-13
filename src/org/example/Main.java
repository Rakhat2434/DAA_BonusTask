package org.example;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        JSONArray outputArray = new JSONArray();

        try (FileReader reader = new FileReader("input.json")) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray tests = (JSONArray) obj.get("tests");

            for (Object testObj : tests) {
                JSONObject test = (JSONObject) testObj;
                String text = (String) test.get("text");
                String pattern = (String) test.get("pattern");

                long startTime = System.nanoTime();
                List<Integer> matches = KMP.search(text, pattern);
                long endTime = System.nanoTime();
                double elapsedMs = (endTime - startTime) / 1_000_000.0;

                JSONObject resultObj = new JSONObject();
                resultObj.put("textLength", text.length());
                resultObj.put("patternLength", pattern.length());
                resultObj.put("matchesCount", matches.size());
                resultObj.put("executionTimeMs", elapsedMs);
                resultObj.put("matches", matches);
                outputArray.add(resultObj);
            }

            try (FileWriter file = new FileWriter("output.json")) {

                file.write(prettyPrintJSON(outputArray));
                System.out.println("Results saved to output.json");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String prettyPrintJSON(JSONArray array) {
        return JSONValue.toJSONString(array)
                .replaceAll("},", "},\n")
                .replaceAll("\\{", "{\n  ")
                .replaceAll("}", "\n}")
                .replaceAll(",", ",\n  ");
    }
}
