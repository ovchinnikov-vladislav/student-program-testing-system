package rsoi.lab2.taskservice.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
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
    @Size(max=255)
    @Column(name = "name_task")
    private String nameTask;

    @Size(max=1000)
    @Column(name = "short_description", length = 1000)
    private String description;

    @NotNull
    @Size(max=2500)
    @Column(name = "text_task", length = 2500)
    private String textTask;

    @Size(max=10000)
    @Column(name = "template_code", length = 10000)
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
                Objects.equals(idUser, task.idUser);
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
                '}';
    }
}
