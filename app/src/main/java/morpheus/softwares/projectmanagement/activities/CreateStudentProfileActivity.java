package morpheus.softwares.projectmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import morpheus.softwares.projectmanagement.R;
import morpheus.softwares.projectmanagement.models.Database;
import morpheus.softwares.projectmanagement.models.Links;
import morpheus.softwares.projectmanagement.models.Student;
import morpheus.softwares.projectmanagement.models.User;

public class CreateStudentProfileActivity extends AppCompatActivity {
    String[] AREAS = new Links(CreateStudentProfileActivity.this).getAreas();
    ImageView back;
    EditText id, mail, firstTopic, secondTopic, thirdTopic;

    AutoCompleteTextView firstArea;
    ArrayAdapter<String> firstAreaAdapter;
    AutoCompleteTextView secondArea;
    ArrayAdapter<String> secondAreaAdapter;
    AutoCompleteTextView thirdArea;
    ArrayAdapter<String> thirdAreaAdapter;

    RadioGroup grouping;
    RadioButton alone, group;
    Button createProfile;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student_profile);

        back = findViewById(R.id.createProfileBack);
        id = findViewById(R.id.createProfileID);
        mail = findViewById(R.id.createProfileEmail);
        firstTopic = findViewById(R.id.createProfileFirstProjectName);
        secondTopic = findViewById(R.id.createProfileSecondProjectName);
        thirdTopic = findViewById(R.id.createProfileThirdProjectName);
        firstArea = findViewById(R.id.createProfileFirstProjectArea);
        secondArea = findViewById(R.id.createProfileSecondProjectArea);
        thirdArea = findViewById(R.id.createProfileThirdProjectArea);
        grouping = findViewById(R.id.createProfileGrouping);
        alone = findViewById(R.id.createProfileAlone);
        group = findViewById(R.id.createProfileGroup);
        createProfile = findViewById(R.id.createProfileCreate);

        firstAreaAdapter = new ArrayAdapter<>(this, R.layout.list_items, AREAS);
        firstArea.setAdapter(firstAreaAdapter);

        secondAreaAdapter = new ArrayAdapter<>(this, R.layout.list_items, AREAS);
        secondArea.setAdapter(secondAreaAdapter);

        thirdAreaAdapter = new ArrayAdapter<>(this, R.layout.list_items, AREAS);
        thirdArea.setAdapter(thirdAreaAdapter);

        database = new Database(this);

        back.setOnClickListener(v -> finish());

        createProfile.setOnClickListener(v -> {
            // Current Date/Time
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            // Variable initializations
            String idNumber = String.valueOf(id.getText()).trim(),
                    email = String.valueOf(mail.getText()).trim(),
                    firstProject = String.valueOf(firstTopic.getText()).trim(),
                    areaOne = String.valueOf(firstArea.getText()).trim(),
                    secondProject = String.valueOf(secondTopic.getText()).trim(),
                    areaTwo = String.valueOf(secondArea.getText()).trim(),
                    thirdProject = String.valueOf(thirdTopic.getText()).trim(),
                    areaThree = String.valueOf(thirdArea.getText()).trim(),
                    aloneGroup = alone.isChecked() ? String.valueOf(alone.getText()).trim() :
                            group.isChecked() ? String.valueOf(group.getText()).trim() : null,
                    formattedDate = date.format(myFormatObj);

            ArrayList<User> users = database.selectAllUsers();
            for (User user : users)
                if (user.getEmail().equals(email)) {
                    Student student = new Student(0, idNumber, email, firstProject, secondProject,
                            thirdProject, areaOne, areaTwo, areaThree, aloneGroup, formattedDate,
                            "Unapproved", "Unapproved", "Unapproved",
                            "", "", "");
                    database.insertStudent(student);
                    database.updateUserStatus(email, getString(R.string.created));
                    Toast.makeText(this, "Profile created successfully!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, StudentActivity.class));
                    finish();
                }
        });
    }
}