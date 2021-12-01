package org.carfactory.model.producer;

import javafx.beans.property.DoubleProperty;
import org.carfactory.domain.Product;

public interface ProductProducer<T extends Product> {
    void startProducing();
    DoubleProperty producingRateProperty();
}
