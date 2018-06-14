package be.kbc.pft;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import be.kbc.pft.scheduled.CsvTransferTask;
import be.kbc.pft.service.FileService;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ScheduledTaskFlow {

  @Autowired
  private CsvTransferTask csvTransferTask;

  @MockBean
  private FileService fileService;

  @Before
  public void setup() throws IOException {
    when(fileService.loadFile(anyString())).thenReturn("some csv file".getBytes());
  }

  @Test
  public void scheduledTaskOk() throws IOException {
    csvTransferTask.execute();
  }

  @Test(expected = IOException.class)
  public void scheduledTaskFileProblem() throws IOException {
    when(fileService.loadFile(anyString())).thenThrow(new IOException());
    csvTransferTask.execute();
  }

}
