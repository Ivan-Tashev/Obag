package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.DeliveryEntity;
import bg.obag.obag.repo.DeliveryRepo;
import bg.obag.obag.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepo deliveryRepo;

    public DeliveryServiceImpl(DeliveryRepo deliveryRepo) {
        this.deliveryRepo = deliveryRepo;
    }

    @Override
    public void initializeDeliveries() {
        if (deliveryRepo.count() == 0) {
            deliveryRepo.saveAll(List.of(
                    new DeliveryEntity().setName("Speedy: Стандарт 24часа До АДРЕС (Безплатно)"),
                    new DeliveryEntity().setName("Speedy: Стандарт 24часа ДО ОФИС (Безплатно)")
            ));
        }
    }
}
