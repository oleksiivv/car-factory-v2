package org.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.carfactory.domain.CarComponent;
import org.carfactory.model.transport.Pipeline;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.carfactory.model.warehouse.ScheduledWarehouse;
import org.carfactory.service.component.CarComponentFactory;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class CarComponentProducer<T extends CarComponent> implements ProductProducer<T> {

    @NonNull
    private final CarComponentFactory<T> carComponentFactory;
    @NonNull
    private final ProductWarehouse<T> carComponentWarehouse;
    @NonNull
    private final Pipeline pipeline;

    private final DoubleProperty producingRate = new SimpleDoubleProperty(1.0);

    @Override
    public void startProducing() {

        final ScheduledWarehouse scheduledWarehouse = (ScheduledWarehouse) CarComponentProducer.this.carComponentWarehouse;

        Runnable producingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    T component = carComponentFactory.createCarComponent();
                    pipeline.addProduct(component);
                    carComponentWarehouse.supplyProduct(component, CarComponentProducer.this);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                } finally {
                    scheduledWarehouse.scheduleWarehouseTask(this, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
                }
            }
        };

        scheduledWarehouse.scheduleWarehouseTask(producingTask, producingRate.multiply(1000).longValue(), TimeUnit.MILLISECONDS);
    }

    @Override
    public DoubleProperty producingRateProperty() {
        return producingRate;
    }
}
