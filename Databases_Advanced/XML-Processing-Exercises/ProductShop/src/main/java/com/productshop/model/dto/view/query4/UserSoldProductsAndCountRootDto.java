package com.productshop.model.dto.view.query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsAndCountRootDto {

    @XmlAttribute
    private int count;

    @XmlElement(name = "user")
    private List<UserSoldProductsAndCountDto> users;

    public UserSoldProductsAndCountRootDto() { }
    public UserSoldProductsAndCountRootDto(int count, List<UserSoldProductsAndCountDto> users) {
        this.count = count;
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserSoldProductsAndCountDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldProductsAndCountDto> users) {
        this.users = users;
    }
}
