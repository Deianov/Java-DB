package softuni.workshop.service.model;

import com.google.gson.annotations.Expose;

public class CompanyViewModel {

    @Expose
    private String name;

    public CompanyViewModel() { }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
