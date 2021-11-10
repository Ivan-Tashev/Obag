package bg.obag.obag.service.impl;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.entity.LogEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.LogViewModel;
import bg.obag.obag.repo.LogRepo;
import bg.obag.obag.service.LogService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

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

        if (authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(a -> a.equals("ROLE_ANONYMOUS"))
        ) {
            UserEntity userEntity = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User email " + authentication.getName() + " not found."));

            LogEntity logEntity = new LogEntity()
                    .setProduct(modelMapper.map(productServiceModel, ProductEntity.class))
                    .setUser(userEntity)
                    .setAction(action);

            logRepo.save(logEntity);
        }
    }

    @Override
    public List<LogViewModel> findAllLogsByUser() {
        return logRepo.findAllByOrderByUser().stream().map(logEntity -> modelMapper.map(logEntity, LogViewModel.class)
                        .setUser(logEntity.getUser().getEmail())
                        .setProductName(logEntity.getProduct().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsLogCount> findAllLogsByProduct() {
        return logRepo.findAllByGroupByProduct();
    }

    @Scheduled(cron = "0 0 3 ? *  *") // every day at 3:00AM
    @Transactional
    @Override
    public void cleanLogs() {
        LocalDateTime dateOneMonthAgo = LocalDateTime.now().minusMinutes(1);
        logRepo.deleteOlderThan1Month(dateOneMonthAgo);
        LOGGER.info("Logs, older than " + dateOneMonthAgo + " cleared.");
    }
}
