package rsoi.lab2.gservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rsoi.lab2.gservice.model.OperationTask;
import rsoi.lab2.gservice.model.OperationTest;

@Repository
public interface OperationTestRepository extends JpaRepository<OperationTest, Integer> {
    @Query (value = "Select min(op.id) from OperationTest op")
    Integer findMinId();
    @Query (value = "Select max(op.id) from OperationTest op")
    Integer findMaxId();
    @Query (value = "Select op from OperationTest op where op.id = (select min(op.id) from OperationTest op)")
    OperationTest findOpByMinId();
}
