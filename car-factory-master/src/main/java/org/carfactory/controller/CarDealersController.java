package org.carfactory.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.carfactory.model.CarDealersModel;
import org.springframework.stereotype.Component;

@Component
@FxmlView
@RequiredArgsConstructor
public class CarDealersController {

    private final CarDealersModel carDealersModel;

    @FXML private Label carDealersCount;
    @FXML private Slider carRequestingRate;

    public void initialize() {
        carDealersCount.setText(String.format("Car Dealers | %d", carDealersModel.getDealersCount()));
        carDealersModel.getCarDealers().forEach(carDealer -> carDealer.requestingRateProperty().bind(carRequestingRate.valueProperty().multiply(1000)));
    }
}
