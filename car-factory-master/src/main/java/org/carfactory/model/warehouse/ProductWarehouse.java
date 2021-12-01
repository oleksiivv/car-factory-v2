package org.carfactory.model.warehouse;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import org.carfactory.domain.Product;
import org.carfactory.model.dealer.ProductDealer;
import org.carfactory.model.producer.ProductProducer;

public interface ProductWarehouse<T extends Product> {

    IntegerProperty capacityProperty();
    IntegerBinding sizeBinding();

    T consumeProduct() throws InterruptedException;
    T consumeProduct(ProductDealer<T> productDealer) throws InterruptedException;
    void supplyProduct(T product) throws InterruptedException;
    void supplyProduct(T product, ProductProducer<T> productProducer) throws InterruptedException;
}
