package softuni.workshop.service.model;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProjectViewModel {

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    private BigDecimal payment;

    public ProjectViewModel() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result
                .append("Project Name: ")
                .append(this.getName())
                .append(System.lineSeparator())
                .append("\tDescription: ")
                .append(this.getDescription())
                .append(System.lineSeparator())
                .append("\t")
                .append(this.getPayment());
        return result.toString();
    }
}
