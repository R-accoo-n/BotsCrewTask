package com.bohdan.botscrewtask.repos;

import com.bohdan.botscrewtask.models.Lector;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    List<Lector> findByNameContaining(String name);
}
