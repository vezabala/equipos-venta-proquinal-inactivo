package proquinal.com.criteria;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.StringFilter;
import proquinal.com.domain.enumeration.State;

public class EquipoCriteria {
    public static class StateFilter extends Filter<State>{}
    private StringFilter activoFijo;
    private StateFilter state;

    public StringFilter getActivoFijo() {
        return activoFijo;
    }

    public void setActivoFijo(StringFilter activoFijo) {
        this.activoFijo = activoFijo;
    }

    public StateFilter getState() {
        return state;
    }

    public void setState(StateFilter state) {
        this.state = state;
    }
}
