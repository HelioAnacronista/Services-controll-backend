package io.helioanacronista.servicescontroll.DTO;

import java.time.LocalDate;

public class WorkCardDTO {

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
        return "https://drscdn.500px.org/photo/1058330716/m%3D900/v2?sig=f211a5fef5b34e0bae5ab0d8d5a10ee64e495b826ae0a73c5b02bb0d9b17286f";
    }
    public String getSector() {
        return "#41f1b6";
    }

    public String getOperation() {
        return "Vendas";
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }
}
