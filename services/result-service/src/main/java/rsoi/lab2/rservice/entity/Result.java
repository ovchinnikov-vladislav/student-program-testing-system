package rsoi.lab2.rservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "result")
@IdClass(ResultKey.class)
public class Result implements Serializable {

    @Id
    @Column(name = "id_task", nullable = false)
    @NotNull
    private UUID idTask;

    @Id
    @Column(name = "id_user", nullable = false)
    @NotNull
    private UUID idUser;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "count_attempt", nullable = false)
    @Value("${some.key:0}")
    private Integer countAttempt;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @NotNull
    @Column(name = "mark", nullable = false)
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0") @DecimalMax(value = "100")
    private Double mark;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(countAttempt, result.countAttempt) &&
                Objects.equals(createDate, result.createDate) &&
                Objects.equals(mark, result.mark) &&
                Objects.equals(idTask, result.idTask) &&
                Objects.equals(idUser, result.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idUser, countAttempt, createDate, mark);
    }

    @Override
    public String toString() {
        return "Result{" +
                "countAttempt=" + countAttempt +
                ", createDate=" + createDate +
                ", mark=" + mark +
                ", idTask=" + idTask +
                ", idUser=" + idUser +
                '}';
    }
}
