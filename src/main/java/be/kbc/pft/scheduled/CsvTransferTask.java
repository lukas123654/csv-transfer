package be.kbc.pft.scheduled;

import static java.util.Calendar.*;

import be.kbc.pft.repository.entity.Csv;
import be.kbc.pft.service.CsvTransferService;
import be.kbc.pft.service.FileService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvTransferTask {

  private static final Logger logger = LoggerFactory.getLogger(CsvTransferTask.class);

  private final CsvTransferService csvTransferService;
  private final FileService fileService;
  private final String csvFolderPath;

  public CsvTransferTask(CsvTransferService csvTransferService, FileService fileService,
      @Value("${csv.folder}") String csvFolderPath) {
    this.csvTransferService = csvTransferService;
    this.fileService = fileService;
    this.csvFolderPath = csvFolderPath == null || csvFolderPath.endsWith("/")
        ? csvFolderPath
        : csvFolderPath + "/";
  }

  @Scheduled(cron = "0 23 0 * * ?")
  public void execute() throws IOException {
    logger.info("File transfer task started.");

    Calendar calendar = getInstance();
    String fileName = "pearlapp_"
        + calendar.get(YEAR)
        // months are zero based -> JANUARY = 0
        + ((calendar.get(MONTH) + 1) < 10 ? "0" + (calendar.get(MONTH) + 1) : (calendar.get(MONTH) + 1))
        + (calendar.get(DATE) < 10 ? "0" + calendar.get(DATE): calendar.get(DATE))
        + ".csv";

    Csv csv = new Csv()
        .setName(fileName)
        .setFile(fileService.loadFile(csvFolderPath + fileName));

    // in case file not exists the exception is thrown and the database stays as it is
    csvTransferService.deleteAllCsvFiles();
    csvTransferService.saveCsvFile(csv);

    logger.info("Transfer of csv file " + fileName + " successfully completed.");
  }

}
