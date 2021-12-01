package org.carfactory.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.carfactory.domain.Car;
import org.carfactory.model.CarPlantModel;
import org.carfactory.model.warehouse.AuditableWarehouse;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class CarWarehouseController {

    private final CarPlantModel carPlantModel;

    @FXML private Label carWarehouseState;
    @FXML private Label carWarehouseTotal;

    public void initialize() {

        ProductWarehouse<Car> carWarehouse = carPlantModel.getCarWarehouse();

        carWarehouseState.textProperty().bind(
                carWarehouse.sizeBinding().asString()
                        .concat(" / ")
                        .concat(carWarehouse.capacityProperty().asString())
        );

        carWarehouseTotal.textProperty().bind(
                new SimpleStringProperty("Sold cars: ")
                        .concat(((AuditableWarehouse) carWarehouse).auditSizeBinding().asString())
        );
    }
}
