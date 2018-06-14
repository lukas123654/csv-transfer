package be.kbc.pft.service;

import be.kbc.pft.repository.CsvRepository;
import be.kbc.pft.repository.entity.Csv;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CsvTransferService {

  private final CsvRepository repository;

  public CsvTransferService(CsvRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void saveCsvFile(Csv csv) {
    repository.save(csv);
  }

  @Transactional
  public void deleteAllCsvFiles() {
    repository.deleteAll();
  }
}
