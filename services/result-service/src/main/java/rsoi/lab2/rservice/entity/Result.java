package rsoi.lab2.rservice.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "result")
@IdClass(ResultKey.class)
@EntityListeners(AuditingEntityListener.class)
public class Result implements Serializable {

    @Id
    @Column(name = "id_task", nullable = false)
    @NotNull
    private UUID idTask;

    @Id
    @Column(name = "id_user", nullable = false)
    @NotNull
    private UUID idUser;

    @DecimalMin(value = "0")
    @Column(name = "count_attempt", nullable = false)
    private Integer countAttempt;

    @NotNull
    @Column(name = "mark", nullable = false)
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0") @DecimalMax(value = "100")
    private Double mark;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        if (countAttempt == null)
            countAttempt = 0;
    }

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

    public Integer getCountAttempt() {
        return countAttempt;
    }

    public void setCountAttempt(Integer countAttempt) {
        this.countAttempt = countAttempt;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(idTask, result.idTask) &&
                Objects.equals(idUser, result.idUser) &&
                Objects.equals(countAttempt, result.countAttempt) &&
                Objects.equals(mark, result.mark) &&
                Objects.equals(createdAt, result.createdAt) &&
                Objects.equals(updatedAt, result.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idUser, countAttempt, mark, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Result{" +
                "idTask=" + idTask +
                ", idUser=" + idUser +
                ", countAttempt=" + countAttempt +
                ", mark=" + mark +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
