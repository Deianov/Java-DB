package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeCardSeedDto {

    @Expose
    private String number;

    public EmployeeCardSeedDto() { }

    @Size(max = 30)
    @NotNull
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
