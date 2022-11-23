package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.order.ItemGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup,Long> {
    List<ItemGroup> findAllByShippingDate(LocalDate date);
}
