package com.roy.rocketmq.repo;



import com.roy.rocketmq.domain.Wz03Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface R01UFDOCIllnessOrderRepo extends JpaRepository<Wz03Entity,Long> {
    Wz03Entity findByWwz311(String wwz311);
    Wz03Entity findByWwz301(Long wwz301);
}
