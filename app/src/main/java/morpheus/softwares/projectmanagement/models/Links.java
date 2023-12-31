package morpheus.softwares.projectmanagement.models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Links {
    protected final Context context;
    private final String[] areas = {"Artificial Intelligence", "Software Engineering", "Quantum " +
            "Computing", "Networking", "Multimedia Technology", "Cyber Security", "Data Science",
            "Programming Languages", "Soft Computing", "Machine Learning", "Data structures & " +
            "Algorithms", "Computer Hardware", "Medical Informatics", "Cloud Computing", "Game " +
            "Design", "Data Mining", "Information & Communication Technology", "Computer Vision",
            "Natural Language Processing"};
    private final String[] roles = {"student", "supervisor", "coordinator"};
    Database database;

    public Links(Context context) {
        this.context = context;
        database = new Database(context);
    }

    /**
     * Returns the list of available project areas of interests
     */
    public String[] getAreas() {
        return areas;
    }

    /**
     * Returns the list of available roles of to be signed up as
     */
    public String[] getRoles() {
        return roles;
    }

    /**
     * Checks if the user has signed up before
     */
    public boolean hasSignedUp(String identifier) {
        ArrayList<User> users = database.selectAllUsers();

        for (User user : users) if (user.getEmail().equalsIgnoreCase(identifier)) return true;
        return false;
    }

    /**
     * Sets the email/ID Number of a user account
     */
    public void setEmail(String status) {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("ID", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("id", status);

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.apply();
    }

    /**
     * Matches supervisors in the database with a specified supervisory area and returns a formatted string.
     *
     * @param supervisoryArea The supervisory area to match against supervisors in the database.
     * @return A formatted string containing the names of supervisors that match the specified supervisory area.
     * The names of matching supervisors are separated by a newline in the returned string.
     */
    public String matchSupervisors(String supervisoryArea) {
        // Get all supervisors from the database
        ArrayList<Supervisor> supervisors = database.selectAllSupervisors();

        // Initialize an empty StringBuilder to hold the result
        StringBuilder result = new StringBuilder();

        // Iterate through the list of supervisors and add matching supervisors to the result StringBuilder
        for (Supervisor supervisor : supervisors) {
            // Get the name of the current supervisor
            String currentSupervisor = supervisor.getName();

            /*
             Check if the supervisory area of the current supervisor matches the specified supervisoryArea
             Append the name of the current supervisor to the result StringBuilder
             and add a newline character to separate the names of matching supervisors
            */
            if (supervisor.getArea().equals(supervisoryArea))
                result.append(currentSupervisor).append(", ");
        }

        // Convert the result StringBuilder to a String and return it
        return result.toString();
    }
}