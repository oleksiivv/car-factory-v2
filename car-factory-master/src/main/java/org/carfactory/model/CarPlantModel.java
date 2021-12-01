package org.carfactory.model;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.carfactory.domain.Car;
import org.carfactory.model.transport.Pipeline;
import org.carfactory.model.warehouse.ObservableWarehouse;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.carfactory.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@RequiredArgsConstructor
public class CarPlantModel {

    private final CarComponentProducersModel carComponentProducersModel;

    @Getter private final ProductWarehouse<Car> carWarehouse;

    @Getter private final LongProperty waitingTaskCount = new SimpleLongProperty();
    @Getter private final LongProperty completedTaskCount = new SimpleLongProperty();

    @Value("${constructors.construction-time}")
    private int carConstructionTime;


    private Pipeline pipeline;

    @Value("${constructors.count}")
    @Getter private int constructorsCount;

    private ThreadPoolExecutor executor;

    @PostConstruct
    private void initializeModel() {

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(constructorsCount,  new DaemonThreadFactory());

        ObservableWarehouse observableWarehouse = (ObservableWarehouse) carWarehouse;
        observableWarehouse.notifyObservers();
    }

    public void submitCarConstructionTask() {
        executor.submit(new CarConstructionTask());
        Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() + 1));
    }

    @Resource(name = "carPipeline")
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    private class CarConstructionTask implements Runnable {
        @Override
        public void run() {

            Platform.runLater(() -> waitingTaskCount.set(waitingTaskCount.get() - 1));

            try {
                Car car = Car.builder()
                        .carEngine(carComponentProducersModel.getCarEngineFacilityFactory().getProductWarehouse().consumeProduct())
                        .carBody(carComponentProducersModel.getCarBodyFacilityFactory().getProductWarehouse().consumeProduct())
                        .carAccessory(carComponentProducersModel.getCarAccessoryFacilityFactory().getProductWarehouse().consumeProduct())
                        .build();
                Thread.sleep(carConstructionTime);
                pipeline.addProduct(car);
                carWarehouse.supplyProduct(car);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            Platform.runLater(() -> completedTaskCount.set(completedTaskCount.get() + 1));
        }
    }
}
