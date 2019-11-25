package rsoi.lab2.gservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Task implements Serializable {

    private Long idTask;

    @NotEmpty
    private String nameTask;
    private String description;
    @NotEmpty
    @Size(max=2500)
    private String textTask;
    @Size(max=10000)
    private String templateCode;
    private String image;
    @NotNull
    @DecimalMin(value = "1") @DecimalMax(value = "5")
    private Byte complexity;
    @NotNull
    private Date createDate;
    @NotNull
    @DecimalMin(value = "1")
    private Long idUser;

    private Test test;

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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
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
                Objects.equals(test, task.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, nameTask, description, textTask, templateCode, image, complexity, createDate, idUser);
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", nameTask='" + nameTask + "\'" +
                ", description='" + description + '\'' +
                ", textTask='" + textTask + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", image='" + image + '\'' +
                ", complexity=" + complexity +
                ", createDate=" + createDate +
                ", idUser=" + idUser +
                ", test=" + test +
                '}';
    }
}
