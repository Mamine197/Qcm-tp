package com.example.model.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("MOTO")
public class Moto extends Vehicule {

    @NotNull
    @Min(50)
    @Max(2000)
    private Integer cylindree;

    private Boolean sidecar;

    public Moto() {}

    public Moto(String marque, boolean sidecar) {
        super(marque);
        this.cylindree = 650;
        this.sidecar = sidecar;
    }

    public Moto(String marque, LocalDate annee, Integer cylindree, Boolean sidecar) {
        super(marque, annee);
        this.cylindree = cylindree;
        this.sidecar = sidecar;
    }

    public Integer getCylindree() {
        return cylindree;
    }

    public void setCylindree(Integer cylindree) {
        this.cylindree = cylindree;
    }

    public Boolean getSidecar() {
        return sidecar;
    }

    public void setSidecar(Boolean sidecar) {
        this.sidecar = sidecar;
    }
}