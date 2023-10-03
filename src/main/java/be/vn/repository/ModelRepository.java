package be.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.vn.model.Model;

import java.util.Date;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}
