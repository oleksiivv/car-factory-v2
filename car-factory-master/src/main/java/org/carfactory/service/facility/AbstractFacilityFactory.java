package org.carfactory.service.facility;

import org.carfactory.domain.CarComponent;
import org.carfactory.model.producer.CarComponentProducer;
import org.carfactory.model.transport.Pipeline;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.carfactory.service.component.CarComponentFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface AbstractFacilityFactory<T extends CarComponent> {

    CarComponentFactory<T> getCarComponentFactory();
    ProductWarehouse<T> getProductWarehouse();
    List<CarComponentProducer<T>> getCarComponentProducers();
    int getCarComponentProducersCount();

    Pipeline getPipeline();

    default List<CarComponentProducer<T>> createCarComponentProducers(int carComponentProducersCount) {
        return IntStream.range(0, carComponentProducersCount)
                .mapToObj(value -> new CarComponentProducer<>(getCarComponentFactory(), getProductWarehouse(), getPipeline()))
                .collect(Collectors.toList());
    }
}
