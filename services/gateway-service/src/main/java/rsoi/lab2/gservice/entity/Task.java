package rsoi.lab2.gservice.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long idTask;

    @NotNull
    @Column(name = "name_task")
    private String nameTask;

    @Column(name = "short_description")
    private String description;

    @NotNull
    @Column(name = "text_task")
    private String textTask;

    @Column(name = "template_code")
    private String templateCode;

    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "complexity")
    @DecimalMin(value = "1") @DecimalMax(value = "5")
    private Byte complexity;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "id_user")
    private Long idUser;

    private Test[] tests;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Byte getComplexity() {
        return complexity;
    }

    public void setComplexity(Byte complexity) {
        this.complexity = complexity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Test[] getTests() {
        return tests;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(idTask, task.idTask) &&
                Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(description, task.description) &&
                Objects.equals(textTask, task.textTask) &&
                Objects.equals(templateCode, task.templateCode) &&
                Objects.equals(image, task.image) &&
                Objects.equals(complexity, task.complexity) &&
                Objects.equals(createDate, task.createDate) &&
                Objects.equals(idUser, task.idUser) &&
                Arrays.equals(tests, task.tests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, nameTask, description, textTask, templateCode, image, complexity, createDate, idUser);
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nameTask=" + nameTask +
                ", description='" + description + '\'' +
                ", textTask='" + textTask + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", image='" + image + '\'' +
                ", complexity=" + complexity +
                ", createDate=" + createDate +
                ", idUser=" + idUser +
                ", test=" + Arrays.toString(tests) +
                '}';
    }
}
