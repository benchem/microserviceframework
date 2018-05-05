package team.benchem.demo.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.benchem.demo.bill.entity.OrderBill;

@Repository
public interface OrderBillRepository extends JpaRepository<OrderBill, String> {
    OrderBill findByBillNumber(String billNumber);
}
