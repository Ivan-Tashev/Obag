package bg.obag.obag.service.impl;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.entity.LogEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.repo.LogRepo;
import bg.obag.obag.service.LogService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import org.aspectj.lang.Signature;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepo logRepo;
    private final ProductsService productsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepo logRepo, ProductsService productsService, UserService userService, ModelMapper modelMapper) {
        this.logRepo = logRepo;
        this.productsService = productsService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addLog(Long productId, String action) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productsService.findProductById(productId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User email " + authentication.getName() + " not found."));

        LogEntity logEntity = new LogEntity()
                .setProduct(modelMapper.map(productServiceModel, ProductEntity.class))
                .setUser(userEntity)
                .setAction(action);

        logRepo.save(logEntity);
    }
}
