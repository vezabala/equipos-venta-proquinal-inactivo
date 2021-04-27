package proquinal.com.criteria;

import io.github.jhipster.service.filter.StringFilter;

public class UsuarioCriteria {
    private StringFilter equipo;
    private StringFilter numeroDocumento;

    public StringFilter getEquipo() {
        return equipo;
    }

    public void setEquipo(StringFilter equipo) {
        this.equipo = equipo;
    }

    public StringFilter getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(StringFilter numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
