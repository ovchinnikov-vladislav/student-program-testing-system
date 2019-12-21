package rsoi.lab2.gservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "operation_task")
@Entity
public class OperationTask implements Serializable {

    public enum TypeOperation {
        CREATE,
        UPDATE,
        DELETE,
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @Column(name = "type_operation")
    private TypeOperation typeOperation;
    @Column(name = "json", length = 50000)
    private String json;

    public OperationTask() {
    }

    public OperationTask(Integer id, TypeOperation typeOperation, String json) {
        this.id = id;
        this.typeOperation = typeOperation;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
