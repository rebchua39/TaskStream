package se.edu.inclass;

import se.edu.inclass.data.DataManager;
import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.TaskNameComparator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    private TaskNameComparator taskNameComparator;

    public static void main(String[] args) {
        DataManager dm = new DataManager("./data/data.txt");
        ArrayList<Task> tasksData = dm.loadData();

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");
        printData(filteredList);

        System.out.println("Printing deadlines");
        printDeadlines(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));

        printDeadlinesUsingStream(tasksData);
        System.out.println("Total number of deadlines (using stream): "
                + countDeadlinesUsingStream(tasksData));
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStream(ArrayList<Task> tasks) {
        int count = (int) tasks.stream()
                .filter((t) -> t instanceof Deadline) //filtering using lambda
                .count();
        return count;
    }

    public static void printData(ArrayList<Task> tasksData) {
        System.out.println("Printing tasks by looping");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataWithStream(ArrayList<Task> tasks) {
        System.out.println("Printing data using stream");
        tasks.stream() //convert to stream
                .forEach(System.out::println); //terminal operator
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStream(ArrayList<Task> tasks) {
        tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .sorted((a, b) -> a.getDescription().toLowerCase().compareTo(b.getDescription().toLowerCase()))
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasks, String filterString) {
        ArrayList<Task> filteredList = (ArrayList<Task>) tasks.stream()
                .filter((t) -> t.getDescription().contains(filterString))
                .collect(Collectors.toList());
        return filteredList;
    }
}
