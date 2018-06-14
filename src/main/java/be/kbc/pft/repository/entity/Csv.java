package be.kbc.pft.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "csv_storage")
public class Csv {

  @Id
  private String name;

  @Column
  private byte[] file;

  public String getName() {
    return name;
  }

  public Csv setName(String name) {
    this.name = name;
    return this;
  }

  public byte[] getFile() {
    return file;
  }

  public Csv setFile(byte[] file) {
    this.file = file;
    return this;
  }

}
