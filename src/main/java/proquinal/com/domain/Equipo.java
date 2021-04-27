package proquinal.com.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import proquinal.com.domain.enumeration.State;

/**
 * A Equipo.
 */
@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column(name = "activo_fijo", length = 70, nullable = false, unique = true)
    private String activoFijo;

    @NotNull
    @Size(max = 70)
    @Column(name = "marca", length = 70, nullable = false)
    private String marca;

    @NotNull
    @Size(max = 200)
    @Column(name = "procesador", length = 200, nullable = false)
    private String procesador;

    @NotNull
    @Size(max = 70)
    @Column(name = "office", length = 70, nullable = false)
    private String office;

    @NotNull
    @Size(max = 70)
    @Column(name = "sistema_operativo", length = 70, nullable = false)
    private String sistemaOperativo;

    @NotNull
    @Size(max = 70)
    @Column(name = "disco_duro", length = 70, nullable = false)
    private String discoDuro;

    @NotNull
    @Size(max = 70)
    @Column(name = "ram", length = 70, nullable = false)
    private String ram;

    @Size(max = 200)
    @Column(name = "observaciones", length = 200)
    private String observaciones;

    @Lob
    @Column(name = "img_url")
    private byte[] imgUrl;

    @Column(name = "img_url_content_type")
    private String imgUrlContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private State tipo;

    @NotNull
    @Size(max = 70)
    @Column(name = "windowss", length = 70, nullable = false)
    private String windowss;

    @NotNull
    @Size(max = 70)
    @Column(name = "precio", length = 70, nullable = false)
    private String precio;

    @OneToMany(mappedBy = "equipo")
    private Set<Usuario> usuarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivoFijo() {
        return activoFijo;
    }

    public Equipo activoFijo(String activoFijo) {
        this.activoFijo = activoFijo;
        return this;
    }

    public void setActivoFijo(String activoFijo) {
        this.activoFijo = activoFijo;
    }

    public String getMarca() {
        return marca;
    }

    public Equipo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProcesador() {
        return procesador;
    }

    public Equipo procesador(String procesador) {
        this.procesador = procesador;
        return this;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getOffice() {
        return office;
    }

    public Equipo office(String office) {
        this.office = office;
        return this;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public Equipo sistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
        return this;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public Equipo discoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
        return this;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getRam() {
        return ram;
    }

    public Equipo ram(String ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Equipo observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public byte[] getImgUrl() {
        return imgUrl;
    }

    public Equipo imgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrlContentType() {
        return imgUrlContentType;
    }

    public Equipo imgUrlContentType(String imgUrlContentType) {
        this.imgUrlContentType = imgUrlContentType;
        return this;
    }

    public void setImgUrlContentType(String imgUrlContentType) {
        this.imgUrlContentType = imgUrlContentType;
    }

    public State getTipo() {
        return tipo;
    }

    public Equipo tipo(State tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(State tipo) {
        this.tipo = tipo;
    }

    public String getWindowss() {
        return windowss;
    }

    public Equipo windowss(String windowss) {
        this.windowss = windowss;
        return this;
    }

    public void setWindowss(String windowss) {
        this.windowss = windowss;
    }

    public String getPrecio() {
        return precio;
    }

    public Equipo precio(String precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public Equipo usuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }

    public Equipo addUsuarios(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setEquipo(this);
        return this;
    }

    public Equipo removeUsuarios(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setEquipo(null);
        return this;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipo)) {
            return false;
        }
        return id != null && id.equals(((Equipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipo{" +
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
            ", imgUrlContentType='" + getImgUrlContentType() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", windowss='" + getWindowss() + "'" +
            ", precio='" + getPrecio() + "'" +
            "}";
    }
}
