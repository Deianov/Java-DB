package softuni.workshop.service.model;


import com.google.gson.annotations.Expose;

public class EmployeeViewModel {

    @Expose
    private String fistName;

    @Expose
    private String lastName;

    @Expose
    private Integer age;

    @Expose
    private String projectName;

    public EmployeeViewModel() { }
    public EmployeeViewModel(String fistName, String lastName, Integer age, String projectName) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.age = age;
        this.projectName = projectName;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result
                .append("Name: ")
                .append(this.getFistName())
                .append(" ")
                .append(this.getLastName())
                .append(System.lineSeparator())
                .append("\tAge: ")
                .append(this.getAge())
                .append(System.lineSeparator())
                .append("\tProject name: ")
                .append(this.getProjectName());
        return result.toString();
    }
}
