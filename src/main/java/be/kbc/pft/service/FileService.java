package be.kbc.pft.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  public byte[] loadFile(String path) throws IOException {
    return Files.readAllBytes(Paths.get(path));
  }

}
