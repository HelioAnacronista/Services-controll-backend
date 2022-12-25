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
        return "https://drscdn.500px.org/photo/1058330717/m%3D900/v2?sig=a143ee3ab301aebc502b96e641bfcb0b8b5866441a08c9c98daa7931cb948b41";
    }
    public Integer getSector() {
        return 1;
    }

    public String getOperation() {
        return "Total de Vendas";
    }

    public LocalDate getDate() {
        return LocalDate.now();
    }
}
