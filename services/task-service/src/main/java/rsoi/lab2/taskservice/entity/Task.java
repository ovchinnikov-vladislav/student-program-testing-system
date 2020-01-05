package rsoi.lab2.taskservice.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "task")
public class Task extends BaseEntity implements Serializable {

    @NotEmpty
    @Size(max=255)
    @Column(name = "name_task", nullable = false)
    private String nameTask;

    @Size(max=1000)
    @Column(name = "short_description", length = 1000)
    private String description;

    @NotEmpty
    @Size(max=2500)
    @Column(name = "text_task", length = 2500, nullable = false)
    private String textTask;

    @Size(max=10000)
    @Column(name = "template_code", length = 10000)
    private String templateCode;

    @Column(name = "image")
    private String image;

    @Column(name = "complexity", nullable = false)
    @DecimalMin(value = "1") @DecimalMax(value = "5")
    private Byte complexity;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    @PrePersist
    public void perPersist() {
        if (complexity == null)
            complexity = 1;
        super.setStatus(Status.ACTIVE);
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

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(super.getId(), task.getId()) &&
                Objects.equals(nameTask, task.nameTask) &&
                Objects.equals(description, task.description) &&
                Objects.equals(textTask, task.textTask) &&
                Objects.equals(templateCode, task.templateCode) &&
                Objects.equals(image, task.image) &&
                Objects.equals(complexity, task.complexity) &&
                Objects.equals(idUser, task.idUser) &&
                Objects.equals(super.getCreatedAt(), task.getCreatedAt()) &&
                Objects.equals(super.getUpdatedAt(), task.getUpdatedAt()) &&
                Objects.equals(super.getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), nameTask, description, textTask, templateCode, image, complexity, idUser,
                super.getCreatedAt(), super.getUpdatedAt(), super.getStatus());
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + super.getId() +
                ", nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                ", textTask='" + textTask + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", image='" + image + '\'' +
                ", complexity=" + complexity +
                ", idUser=" + idUser +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                ", status=" + super.getStatus() +
                '}';
    }
}
