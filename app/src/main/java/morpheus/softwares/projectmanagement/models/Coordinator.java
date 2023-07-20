package morpheus.softwares.projectmanagement.models;

public class Coordinator {
    private String name, phoneNumber, email, pin;

    public Coordinator(String name, String phoneNumber, String email, String pin) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPin(pin);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}