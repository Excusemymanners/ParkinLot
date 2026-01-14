package com.parking.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "carhotos")
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "file_name")
    String filename;

    @Column(name = "file_type")
    String fileType;

    @Column(name = "file_content")
    byte[] fileContent;

    @OneToOne
    Car car;

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }


    public Car getCar() {
        return car;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
