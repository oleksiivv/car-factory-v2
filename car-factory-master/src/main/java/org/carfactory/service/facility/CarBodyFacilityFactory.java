package org.carfactory.service.facility;

import lombok.Getter;
import org.carfactory.domain.CarBody;
import org.carfactory.model.producer.CarComponentProducer;
import org.carfactory.model.transport.Pipeline;
import org.carfactory.model.warehouse.ProductWarehouse;
import org.carfactory.model.warehouse.ScheduledProductWarehouse;
import org.carfactory.service.component.CarComponentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CarBodyFacilityFactory implements AbstractFacilityFactory<CarBody> {

    @Value("${warehouse.body.capacity}")
    private int carComponentWarehouseCapacity;

    @Value("${producers.body.count}")
    @Getter private int carComponentProducersCount;

    @Getter private CarComponentFactory<CarBody> carComponentFactory;
    @Getter private ProductWarehouse<CarBody> productWarehouse;
    @Getter private List<CarComponentProducer<CarBody>> carComponentProducers;
    @Getter private Pipeline pipeline;

    @Resource(name = "bodyPipeline")
    public void setEnginePipeline(Pipeline enginePipeline) {
        this.pipeline = enginePipeline;
    }

    @PostConstruct
    private void initializeService() {
        productWarehouse = new ScheduledProductWarehouse<>(carComponentWarehouseCapacity);
        carComponentProducers = createCarComponentProducers(carComponentProducersCount);
    }

    @Autowired
    public void setCarComponentFactory(CarComponentFactory<CarBody> carComponentFactory) {
        this.carComponentFactory = carComponentFactory;
    }
}
