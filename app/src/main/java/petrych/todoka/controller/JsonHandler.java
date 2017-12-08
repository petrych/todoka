package petrych.todoka.controller;

import petrych.todoka.model.TaskItem;

/**
 * The class is responsible for converting both a single task item and a task list
 * into a JSON representation and writing that representation to a given file.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;




public class JsonHandler {

    private JSONParser parser;

    public JsonHandler() throws IOException, ParseException {
        this.parser = new JSONParser();
    }

    /**
     * Reads the given file and returns a list of task items.
     * @param filePath The path of a file to read from.
     * @return The list of task items.
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<TaskItem> readTaskItemList(String filePath) throws IOException, ParseException {
        FileReader fileReader = new FileReader(filePath);
        JSONArray fileToJson = new JSONArray();

        try {
            fileToJson = (JSONArray) parser.parse(fileReader);
        }
        catch (ParseException e) {
            //System.out.println("The task list is empty.");
        }
        finally {
            if (fileReader != null)
                fileReader.close();
        }

        ArrayList<TaskItem> result = new ArrayList<>();

        for (Object taskJson : fileToJson) {
            JSONObject task = (JSONObject) taskJson;
            TaskItem t = new TaskItem((String)task.get("taskName"));
            result.add(t);
        }

        return result;
    }

    /**
     * Converts a task item to JSON object, add it to the list of tasks
     * and write it to the the corresponding file.
     */
    public void saveTaskListToJson(ArrayList<TaskItem> taskList, String filePath) throws IOException {
        JSONArray  jsonTaskArray = new JSONArray();

        for (TaskItem task: taskList) {
            JSONObject taskToJson = new JSONObject();

            taskToJson.put("taskName", task.getName());
            taskToJson.put("completed", task.isCompleted());
            taskToJson.put("timePeriod", task.getTimePeriod().toString());

            if (task.getCategory() == null) {
                taskToJson.put("category", "");
            }
            else {
                taskToJson.put("category", task.getCategory().toString());
            }

            jsonTaskArray.add(taskToJson);
        }

        FileWriter writer = new FileWriter(filePath);
        JSONArray.writeJSONString(jsonTaskArray, writer);
        writer.close();
    }
}
