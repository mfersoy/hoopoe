package com.hoopoe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_imagedata")
@Entity
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Lob
    private byte [] data;


    public ImageData(byte[] data){
        this.data = data;
    }

    public ImageData(Long id){
        this.id=id;
    }


}
