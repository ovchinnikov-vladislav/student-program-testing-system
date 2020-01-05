package rsoi.lab2.gservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rsoi.lab2.gservice.model.operation.OperationTask;

@Repository
public interface OperationTaskRepository extends JpaRepository<OperationTask, Integer> {
    @Query (value = "Select min(op.id) from OperationTask op")
    Integer findMinId();
    @Query (value = "Select max(op.id) from OperationTask op")
    Integer findMaxId();
    @Query (value = "Select op from OperationTask op where op.id = (select min(op.id) from OperationTask op)")
    OperationTask findOpByMinId();
}
