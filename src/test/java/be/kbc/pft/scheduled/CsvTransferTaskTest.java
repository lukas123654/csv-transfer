package be.kbc.pft.scheduled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import be.kbc.pft.repository.entity.Csv;
import be.kbc.pft.service.CsvTransferService;
import be.kbc.pft.service.FileService;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsvTransferTaskTest {

  @Mock
  private FileService fileService;

  @Mock
  private CsvTransferService csvTransferService;

  @InjectMocks
  private CsvTransferTask task;

  @Captor
  private ArgumentCaptor<Csv> argumentCaptor;

  @Before
  public void setup() {
    setField(task, "csvFolderPath", "path to file");
  }

  @Test
  public void executeTaskOk() throws IOException {
    when(fileService.loadFile(anyString())).thenReturn("csv file content".getBytes());

    task.execute();

    verify(csvTransferService).deleteAllCsvFiles();
    verify(csvTransferService).saveCsvFile(argumentCaptor.capture());

    assertThat(argumentCaptor.getValue().getName()).isNotBlank();
    assertThat(argumentCaptor.getValue().getFile()).isEqualTo("csv file content".getBytes());
  }

  @Test
  public void executeTaskFileProblem() throws IOException {
    when(fileService.loadFile(anyString())).thenThrow(new IOException());

    boolean exceptionThrown = false;
    try {
      task.execute();
    }
    catch (IOException e) {
      exceptionThrown = true;
    }

    assertThat(exceptionThrown).isTrue();
    verifyZeroInteractions(csvTransferService);
  }

}
