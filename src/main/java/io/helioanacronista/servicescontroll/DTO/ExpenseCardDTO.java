package io.helioanacronista.servicescontroll.DTO;

import java.time.LocalDate;

public class ExpenseCardDTO {

    private Double value;
    private Double percentage;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getIcon() {
        return "https://drscdn.500px.org/photo/1058330718/m%3D900/v2?sig=08711b9ac2b71c6444e48e3b6f336e31ad92c813ad7f0be654000fa4170f696c";
    }
    public String getSector() {
        return "#FF7782";
    }

    public String getOperation() {
        return "Gastos";
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }
}
