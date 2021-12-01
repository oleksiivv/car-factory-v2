package org.carfactory.model.warehouse;

import javafx.beans.binding.IntegerBinding;

public interface AuditableWarehouse {
    IntegerBinding auditSizeBinding();
}
