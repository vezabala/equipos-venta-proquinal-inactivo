package proquinal.com.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import proquinal.com.domain.enumeration.State;

/**
 * A DTO for the {@link proquinal.com.domain.Equipo} entity.
 */
public class EquipoDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 70)
    private String activoFijo;

    @NotNull
    @Size(max = 70)
    private String marca;

    @NotNull
    @Size(max = 200)
    private String procesador;

    @NotNull
    @Size(max = 70)
    private String office;

    @NotNull
    @Size(max = 70)
    private String sistemaOperativo;

    @NotNull
    @Size(max = 70)
    private String discoDuro;

    @NotNull
    @Size(max = 70)
    private String ram;

    @Size(max = 200)
    private String observaciones;

    @Lob
    private byte[] imgUrl;

    private String imgUrlContentType;
    @NotNull
    private State tipo;

    @NotNull
    @Size(max = 70)
    private String windowss;

    @NotNull
    @Size(max = 70)
    private String precio;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivoFijo() {
        return activoFijo;
    }

    public void setActivoFijo(String activoFijo) {
        this.activoFijo = activoFijo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public byte[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrlContentType() {
        return imgUrlContentType;
    }

    public void setImgUrlContentType(String imgUrlContentType) {
        this.imgUrlContentType = imgUrlContentType;
    }

    public State getTipo() {
        return tipo;
    }

    public void setTipo(State tipo) {
        this.tipo = tipo;
    }

    public String getWindowss() {
        return windowss;
    }

    public void setWindowss(String windowss) {
        this.windowss = windowss;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipoDTO equipoDTO = (EquipoDTO) o;
        if (equipoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipoDTO{" +
            "id=" + getId() +
            ", activoFijo='" + getActivoFijo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", procesador='" + getProcesador() + "'" +
            ", office='" + getOffice() + "'" +
            ", sistemaOperativo='" + getSistemaOperativo() + "'" +
            ", discoDuro='" + getDiscoDuro() + "'" +
            ", ram='" + getRam() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", windowss='" + getWindowss() + "'" +
            ", precio='" + getPrecio() + "'" +
            "}";
    }
}
