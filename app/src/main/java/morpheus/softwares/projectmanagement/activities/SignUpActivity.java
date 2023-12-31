package morpheus.softwares.projectmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import morpheus.softwares.projectmanagement.R;
import morpheus.softwares.projectmanagement.models.Database;
import morpheus.softwares.projectmanagement.models.Links;
import morpheus.softwares.projectmanagement.models.User;

public class SignUpActivity extends AppCompatActivity {
    private final String[] ROLES = new Links(SignUpActivity.this).getRoles();
    EditText email, pinCode, confirmPinCode, studentName;
    AutoCompleteTextView role;
    ArrayAdapter<String> roleAdapter;
    TextView login;
    Button createAccount;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.signUpEmail);
        pinCode = findViewById(R.id.signUpPin);
        confirmPinCode = findViewById(R.id.signUpConfirmPin);
        studentName = findViewById(R.id.signUpName);
        role = findViewById(R.id.signUpAs);
        login = findViewById(R.id.signUpLogin);
        createAccount = findViewById(R.id.signUpCreateAccount);

        roleAdapter = new ArrayAdapter<>(this, R.layout.list_items, ROLES);
        role.setAdapter(roleAdapter);

        database = new Database(this);

        login.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));

        createAccount.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        String email = String.valueOf(this.email.getText()).trim(),
                pin = String.valueOf(pinCode.getText()).trim(),
                confirmPin = String.valueOf(confirmPinCode.getText()).trim(),
                name = String.valueOf(studentName.getText()).trim(),
                signUpAs = String.valueOf(role.getText());
        boolean hasSignedUp = new Links(this).hasSignedUp(email);

        if (hasSignedUp)
            Toast.makeText(this, "You already have an account!", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(pin) || TextUtils.isEmpty(confirmPin) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(name) || TextUtils.isEmpty(signUpAs))
            Toast.makeText(this, "No field should be empty!", Toast.LENGTH_SHORT).show();
        else if (!TextUtils.equals(pin, confirmPin))
            Toast.makeText(this, "Pins must be the same!", Toast.LENGTH_SHORT).show();
        else
            switch (signUpAs) {
                case "student":
                    signUp(email, pin, name, signUpAs);
                    startActivity(new Intent(this, StudentActivity.class));
                    finish();
                    break;
                case "supervisor":
                    signUp(email, pin, name, signUpAs);
                    startActivity(new Intent(this, SupervisorActivity.class));
                    finish();
                    break;
                case "coordinator":
                    signUp(email, pin, name, signUpAs);
                    startActivity(new Intent(this, CoordinatorActivity.class));
                    finish();
                    break;
            }
    }

    private void signUp(String email, String pin, String name, String role) {
        User newUser = new User(0, email, pin, name, role, "pending", "online");
        database.insertUser(newUser);
        new Links(this).setEmail(email);
        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();
    }
}