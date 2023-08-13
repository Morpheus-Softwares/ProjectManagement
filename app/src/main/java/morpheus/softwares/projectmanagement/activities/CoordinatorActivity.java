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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import morpheus.softwares.projectmanagement.R;
import morpheus.softwares.projectmanagement.adapters.CoodinatorAdapter;
import morpheus.softwares.projectmanagement.models.Coordinator;
import morpheus.softwares.projectmanagement.models.Database;
import morpheus.softwares.projectmanagement.models.Links;
import morpheus.softwares.projectmanagement.models.Projects;
import morpheus.softwares.projectmanagement.models.User;

public class CoordinatorActivity extends AppCompatActivity {
    TextView coordinatorName, coordinatorEmail, coordinatorNavName, coordinatorNavEmail, coordinatorNavRole;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View header;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ArrayList<Projects> projects;
    CoodinatorAdapter coodinatorAdapter;
    RecyclerView recyclerView;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        appBarLayout = findViewById(R.id.coordinatorAppBar);
        toolbar = findViewById(R.id.coordinatorToolbar);
        collapsingToolbarLayout = findViewById(R.id.coordinatorCollapsingToolnar);
        drawerLayout = findViewById(R.id.coordinatorDrawer);
        navigationView = findViewById(R.id.coordinatorNavigator);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        coordinatorName = findViewById(R.id.coordinatorName);
        coordinatorEmail = findViewById(R.id.coordinatorEmail);

        database = new Database(this);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.syncState();

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedTitle);

        header = navigationView.getHeaderView(0);
        coordinatorNavName = header.findViewById(R.id.navName);
        coordinatorNavEmail = header.findViewById(R.id.navEmail);
        coordinatorNavRole = header.findViewById(R.id.navRole);
        coordinatorNavRole.setText(R.string.coordinator);

        projects = new ArrayList<>();
        recyclerView = findViewById(R.id.coordinatorList);
        coodinatorAdapter = new CoodinatorAdapter(this, projects);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(coodinatorAdapter);

        ArrayList<Coordinator> coordinators = database.selectAllCoordinators();
        for (Coordinator coordinator : coordinators) {
            String name = coordinator.getName(), email = coordinator.getEmail();

            coordinatorName.setText(name);
            coordinatorNavName.setText(name);
            coordinatorEmail.setText(email);
            coordinatorNavEmail.setText(email);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        String profile = sharedPreferences.getString("profile", "null");
        String nil = "Create profile...";

        ArrayList<User> users = database.selectAllUsers();
        for (User user : users)
            if (user.getIdentifier().equals(profile)) {
                String name = user.getName();
                coordinatorName.setText(name);
                coordinatorNavName.setText(name);
            } else {
                coordinatorName.setText(nil);
                coordinatorNavName.setText(nil);
            }

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.createProfile) {
                String email = String.valueOf(coordinatorNavEmail.getText()).trim();
                if (new Links(this).checkProfile(email))
                    Toast.makeText(this, "You can't create multiple profiles...", Toast.LENGTH_SHORT).show();
                else
                    startActivity(new Intent(this, CreateCoordinatorProfileActivity.class));
            } else if (item.getItemId() == R.id.viewApprovedTopics)
                Toast.makeText(this, "View Approved Topic", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.complain)
                Toast.makeText(this, "Complain", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.about)
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
            else if (item.getItemId() == R.id.logout) {
                new Links(this).removeStatus();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else if (item.getItemId() == R.id.exit) finishAffinity();

            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }
}