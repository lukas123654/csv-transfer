package be.kbc.pft;

import be.kbc.pft.scheduled.CsvTransferTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @ConditionalOnProperty("csv.transfer.onStartup")
  @Bean
  public CommandLineRunner runCsvTransferOnStartup(CsvTransferTask csvTransferTask) {
    return (args -> csvTransferTask.execute());
  }

}
