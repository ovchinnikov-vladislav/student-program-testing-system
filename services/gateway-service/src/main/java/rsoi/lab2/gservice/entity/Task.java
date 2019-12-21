package rsoi.lab2.gservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Task implements Serializable {

    private UUID idTask;
    private UUID idUser;
    private String image;
    private Byte withoutTest;
    private Date createdAt;
    private Date updatedAt;

    @NotEmpty
    @Size(max=255)
    private String nameTask;

    @Size(max=1000)
    private String description;

    @NotEmpty
    @Size(max=2500)
    private String textTask;

    @Size(max=10000)
    private String templateCode;

    @DecimalMin(value = "1") @DecimalMax(value = "5")
    private Byte complexity;

    private Test test;

    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask(UUID idTask) {
        this.idTask = idTask;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Byte getWithoutTest() {
        return withoutTest;
    }

    public void setWithoutTest(Byte withoutTest) {
        this.withoutTest = withoutTest;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public Byte getComplexity() {
        return complexity;
    }

    public void setComplexity(Byte complexity) {
        this.complexity = complexity;
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
                Objects.equals(idUser, task.idUser) &&
                Objects.equals(image, task.image) &&
                Objects.equals(withoutTest, task.withoutTest) &&
                Objects.equals(createdAt, task.createdAt) &&
                Objects.equals(updatedAt, task.updatedAt) &&
                Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(description, task.description) &&
                Objects.equals(textTask, task.textTask) &&
                Objects.equals(templateCode, task.templateCode) &&
                Objects.equals(complexity, task.complexity) &&
                Objects.equals(test, task.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idUser, image, withoutTest, createdAt, updatedAt, nameTask, description, textTask, templateCode, complexity, test);
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", idUser=" + idUser +
                ", image='" + image + '\'' +
                ", withoutTest=" + withoutTest +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                ", textTask='" + textTask + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", complexity=" + complexity +
                ", test=" + test +
                '}';
    }
}
