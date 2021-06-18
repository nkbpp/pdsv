
package ru.pfr.repo.asv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pfr.model.asv.LegalEntity;
import ru.pfr.model.asv.LegalEntityGroup;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {

    //public List<LegalEntity> findByPAYMENTSTATEMENTDATEBetween(Date d1, Date d2);
    //public List<LegalEntity> findByPAYMENTSTATEMENTDATE(String s);

    public List<LegalEntity> findAll();


    @Query(
            value = "SELECT " +
                    "row_number() over(ORDER BY INSURER_REG_NUM) num, " +
                    "INSURER_REG_NUM, " +
                    "sum(coalesce(PRIHOD,0) - coalesce(RASHOD,0)) PAYMENT_SUM " +
                    "from PdsvLegalEntity " +
                    "where PAYMENT_STATEMENT_DATE BETWEEN ?1 AND ?2 " +
                    "group by INSURER_REG_NUM order by INSURER_REG_NUM",
            nativeQuery = true)
    public List<LegalEntity> findAll(Date d1, Date d2);


/*    @Query(
            value = "SELECT " +
                    "row_number() over(ORDER BY INSURER_REG_NUM) num, " +
                    "INSURER_REG_NUM, " +
                    "sum(PAYMENT_SUM) PAYMENT_SUM " +
                    "from PdsvLegalEntity " +
                    "where PAYMENT_STATEMENT_DATE BETWEEN ?1 AND ?2 " +
                    "group by INSURER_REG_NUM order by INSURER_REG_NUM",
            nativeQuery = true)
    public List<LegalEntity> findAll(Date d1, Date d2);*/

}

