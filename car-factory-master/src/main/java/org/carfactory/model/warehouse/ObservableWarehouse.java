package org.carfactory.model.warehouse;

import org.carfactory.model.observer.WarehouseObserver;

public interface ObservableWarehouse {
    void subscribeObserver(WarehouseObserver warehouseObserver);
    void unsubscribeObserver(WarehouseObserver warehouseObserver);
    void notifyObservers();
}
