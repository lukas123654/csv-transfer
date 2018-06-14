package be.kbc.pft.repository;

import be.kbc.pft.repository.entity.Csv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepository extends JpaRepository<Csv, String> {

}
