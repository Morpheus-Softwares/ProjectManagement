package morpheus.softwares.projectmanagement.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import morpheus.softwares.projectmanagement.R;
import morpheus.softwares.projectmanagement.models.Database;
import morpheus.softwares.projectmanagement.models.Links;
import morpheus.softwares.projectmanagement.models.Student;
import morpheus.softwares.projectmanagement.models.User;

public class StudentActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View header;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    CardView first, second, third;

    TextView studentID, studentEmail, studentNavID, studentNavEmail, studentNavRole, firstProject,
            firstArea, firstSupervisor, firstStatus, secondProject, secondArea, secondSupervisor,
            secondStatus, thirdProject, thirdArea, thirdSupervisor, thirdStatus;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        appBarLayout = findViewById(R.id.studentAppBar);
        toolbar = findViewById(R.id.studentToolbar);
        collapsingToolbarLayout = findViewById(R.id.studentCollapsingToolbar);

        first = findViewById(R.id.studentFirst);
        second = findViewById(R.id.studentSecond);
        third = findViewById(R.id.studentThird);

        studentID = findViewById(R.id.studentName);
        studentEmail = findViewById(R.id.studentID);
        firstProject = findViewById(R.id.studentFirstTopic);
        firstArea = findViewById(R.id.studentFirstArea);
        firstSupervisor = findViewById(R.id.studentFirstSupervisor);
        firstStatus = findViewById(R.id.studentFirstStatus);
        secondProject = findViewById(R.id.studentSecondTopic);
        secondArea = findViewById(R.id.studentSecondArea);
        secondSupervisor = findViewById(R.id.studentSecondSupervisor);
        secondStatus = findViewById(R.id.studentSecondStatus);
        thirdProject = findViewById(R.id.studentThirdTopic);
        thirdArea = findViewById(R.id.studentThirdArea);
        thirdSupervisor = findViewById(R.id.studentThirdSupervisor);
        thirdStatus = findViewById(R.id.studentThirdStatus);
        drawerLayout = findViewById(R.id.studentDrawer);
        navigationView = findViewById(R.id.studentNavigator);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.syncState();

        database = new Database(this);

//        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedTitle);

        // NavigationView items
        header = navigationView.getHeaderView(0);
        studentNavID = header.findViewById(R.id.navName);
        studentNavEmail = header.findViewById(R.id.navEmail);
        studentNavRole = header.findViewById(R.id.navRole);
        studentNavRole.setText(R.string.stdent);

        SharedPreferences prefID = getSharedPreferences("ID", MODE_PRIVATE);
        String status = prefID.getString("id", null),
                nil = "Create profile...", id = getIntent().getStringExtra("idNumber");

        studentID.setText(nil);
        studentNavID.setText(nil);
        studentEmail.setText("");
        studentNavEmail.setText("");

        ArrayList<Student> students = database.selectAllStudents();
        for (Student student : students) {
            String idNumber = student.getIdNumber();
            if (idNumber.equals(status) || idNumber.equals(id)) {
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.VISIBLE);
                third.setVisibility(View.VISIBLE);

                String mail = student.getEmail(),
                        status1 = student.getFirstStatus(), status2 = student.getSecondStatus(),
                        status3 = student.getThirdStatus(), areaOne = student.getFirstArea(),
                        areaTwo = student.getSecondArea(), areaThree = student.getThirdArea(),
                        supervisorOne = new Links(this).matchSupervisors(areaOne),
                        supervisorTwo = new Links(this).matchSupervisors(areaTwo),
                        supervisorThree = new Links(this).matchSupervisors(areaThree);
                studentID.setText(idNumber);
                studentNavID.setText(idNumber);
                studentEmail.setText(mail);
                studentNavEmail.setText(mail);
                firstProject.setText(student.getFirstProject());
                firstArea.setText(areaOne);
                firstSupervisor.setText(supervisorOne);
                firstStatus.setText(status1);
                secondProject.setText(student.getSecondProject());
                secondArea.setText(areaTwo);
                secondSupervisor.setText(supervisorTwo);
                secondStatus.setText(status2);
                thirdProject.setText(student.getThirdProject());
                thirdArea.setText(areaThree);
                thirdSupervisor.setText(supervisorThree);
                thirdStatus.setText(status3);
            }
        }

        ArrayList<User> users = database.selectAllUsers();
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.createProfile) {
                boolean foundDesiredUser = false;

                for (User user : users) {
                    String idNumber = user.getIdentifier(), stat = user.getStatus();

                    if ((idNumber.equals(status) || idNumber.equals(id)) && stat.equals(getString(R.string.created))) {
                        Toast.makeText(this, "You can't create multiple profiles...", Toast.LENGTH_SHORT).show();
                        foundDesiredUser = true;
                        break;
                    }
                }

                if (!foundDesiredUser)
                    startActivity(new Intent(this, CreateStudentProfileActivity.class));
            } else if (item.getItemId() == R.id.viewApprovedTopics)
                Toast.makeText(this, "View Approved Topic", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.complain)
                Toast.makeText(this, "Complain", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.about)
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.logout) {
                database.updateUserOnlineOfflineStatus(String.valueOf(studentID.getText()), "offline");
                finishAffinity();
            } else if (item.getItemId() == R.id.exit) finishAffinity();

            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        first.setVisibility(View.VISIBLE);
        second.setVisibility(View.VISIBLE);
        third.setVisibility(View.VISIBLE);
    }
}