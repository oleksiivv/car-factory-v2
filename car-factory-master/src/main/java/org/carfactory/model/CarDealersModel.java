package org.carfactory.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.carfactory.domain.Car;
import org.carfactory.model.dealer.CarDealer;
import org.carfactory.model.transport.Pipeline;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CarDealersModel {

    @Value("${dealers.count}")
    @Getter private int dealersCount;

    @Getter private ProductWarehouse<Car> carWarehouse;
    @Getter private List<CarDealer> carDealers;

    private Pipeline pipeline;

    @Resource(name = "dealerPipeline")
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostConstruct
    private void initializeModel() {

        this.carDealers = IntStream.range(0, dealersCount)
                .mapToObj(value -> CarDealer.of(carWarehouse, pipeline))
                .collect(Collectors.toList());

        this.carDealers.forEach(CarDealer::startRequesting);
    }

    @Autowired
    public void setCarWarehouse(ProductWarehouse<Car> carWarehouse) {
        this.carWarehouse = carWarehouse;
    }
}
