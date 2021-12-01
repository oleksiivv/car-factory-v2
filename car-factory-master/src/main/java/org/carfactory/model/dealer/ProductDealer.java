package org.carfactory.model.dealer;

import javafx.beans.property.DoubleProperty;
import org.carfactory.domain.Product;

public interface ProductDealer<T extends Product> {
    void startRequesting();
    DoubleProperty requestingRateProperty();
}
