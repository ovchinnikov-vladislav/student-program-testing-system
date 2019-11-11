package rsoi.lab2.gservice.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "result")
@IdClass(ResultKey.class)
public class Result implements Serializable {

    @Id
    @DecimalMin(value = "1")
    @Column(name = "id_task")
    private Long idTask;

    @Id
    @DecimalMin(value = "1")
    @Column(name = "id_user")
    private Long idUser;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "count_attempt")
    private Integer countAttempt;

    @NotNull
    @Column(name = "create_date")
    private Date createDate;

    @NotNull
    @Column(name = "mark")
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0") @DecimalMax(value = "100")
    private Double mark;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
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
