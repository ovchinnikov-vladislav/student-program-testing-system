package rsoi.lab2.gservice.entity.user;

import rsoi.lab2.gservice.entity.BaseEntity;
import javax.validation.constraints.NotEmpty;

public class Role extends BaseEntity {

    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
