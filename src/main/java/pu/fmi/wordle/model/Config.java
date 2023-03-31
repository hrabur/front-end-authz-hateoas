package pu.fmi.wordle.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Config {
  @Id
  @Column(name = "config_id")
  @GeneratedValue
  Long id;

  String maxGuesses;

  String updatedBy;
  LocalDateTime updatedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMaxGuesses() {
    return maxGuesses;
  }

  public void setMaxGuesses(String maxGuesses) {
    this.maxGuesses = maxGuesses;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }
}
