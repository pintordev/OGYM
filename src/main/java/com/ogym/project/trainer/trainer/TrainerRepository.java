package com.ogym.project.trainer.trainer;

import com.ogym.project.trainer.field.Field;
import com.ogym.project.user.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUserInfo(SiteUser userInfo);

    @Query(value = "select "
            + "distinct t.* "
            + "from trainer t "
            + "left outer join site_user u on t.user_info_id = u.id "
            + "left outer join address a on t.address_id = a.id "
            + "left outer join trainer_field_list tfl on t.id = tfl.trainer_id "
            + "left outer join field f on tfl.field_list_id = f.id "
            + "where f.name like %:tField% and "
            + "(u.username like %:tKw% "
            + "or a.main_address like %:tKw% "
            + "or t.center like %:tKw%) "
            + "order by t.create_date desc "
            , countQuery = "select count(*) from trainer"
            , nativeQuery = true)
    Page<Trainer> findAllByKeywordAndFieldOrderByCreateDate(@Param("tField") String tField,
                                                            @Param("tKw") String tKw,
                                                            Pageable pageable);
}
