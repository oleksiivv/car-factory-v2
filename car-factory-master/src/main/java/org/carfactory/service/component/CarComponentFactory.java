package org.carfactory.service.component;

import org.carfactory.domain.CarComponent;

public interface CarComponentFactory<T extends CarComponent> {
     T createCarComponent();
}
