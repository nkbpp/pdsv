package ru.pfr.repo.asv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.model.asv.PhisikalEntity;

import java.util.Date;
import java.util.List;

public interface PhisikalEntityRepository extends JpaRepository<PhisikalEntity, Long> {

    public List<PhisikalEntity> findAll();

    @Query(
            value = "SELECT " +
                    "row_number() over(ORDER BY INSURER_REG_NUM) num, " +
                    "INSURER_REG_NUM, " +
                    "sum(PAYMENT_SUM) PAYMENT_SUM " +

                    "from PdsvPhizicalEntity " +
                    //"from PdsvLegalEntity " +
                    "where PAYMENT_STATEMENT_DATE BETWEEN ?1 AND ?2 " +
                    "group by INSURER_REG_NUM order by INSURER_REG_NUM",
            nativeQuery = true)
    public List<PhisikalEntity> findAll(Date d1, Date d2);

/*    @Query(
            value = "SELECT " +
                    "row_number() over(ORDER BY INSURER_REG_NUM) num, " +
                    "'99000' INSURER_REG_NUM, " +
                    "sum(PAYMENT_SUM) PAYMENT_SUM " +

                    "from PdsvPhizicalEntity " +
                    //"from PdsvLegalEntity " +
                    "where PAYMENT_STATEMENT_DATE BETWEEN ?1 AND ?2",
            nativeQuery = true)

    public List<PhisikalEntity> findAll(Date d1, Date d2);*/

}
