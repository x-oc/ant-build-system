package ru.vova.lab3;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@RequestScoped
@Named("inputBean")
public class InputBean implements Serializable {
    private Double x = null;
    private Double xGraph;
    private Double y;
    private Double yGraph;
    private Double r;
}