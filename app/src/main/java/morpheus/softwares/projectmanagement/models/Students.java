package morpheus.softwares.projectmanagement.models;

public class Students {
    private int id;
    private String idNumber, email, firstProject, secondProject, thirdProject, firstArea,
            secondArea, thirdArea, grouping;

    public Students(int id, String idNumber, String email, String firstProject,
                    String secondProject, String thirdProject, String firstArea,
                    String secondArea, String thirdArea, String grouping) {
        setId(id);
        setIdNumber(idNumber);
        setEmail(email);
        setFirstProject(firstProject);
        setSecondProject(secondProject);
        setThirdProject(thirdProject);
        setFirstArea(firstArea);
        setSecondArea(secondArea);
        setThirdArea(thirdArea);
        setGrouping(grouping);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstProject() {
        return firstProject;
    }

    public void setFirstProject(String firstProject) {
        this.firstProject = firstProject;
    }

    public String getSecondProject() {
        return secondProject;
    }

    public void setSecondProject(String secondProject) {
        this.secondProject = secondProject;
    }

    public String getThirdProject() {
        return thirdProject;
    }

    public void setThirdProject(String thirdProject) {
        this.thirdProject = thirdProject;
    }

    public String getFirstArea() {
        return firstArea;
    }

    public void setFirstArea(String firstArea) {
        this.firstArea = firstArea;
    }

    public String getSecondArea() {
        return secondArea;
    }

    public void setSecondArea(String secondArea) {
        this.secondArea = secondArea;
    }

    public String getThirdArea() {
        return thirdArea;
    }

    public void setThirdArea(String thirdArea) {
        this.thirdArea = thirdArea;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }
}