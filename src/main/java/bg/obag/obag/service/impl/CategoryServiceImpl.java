package bg.obag.obag.service.impl;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.model.binding.CategoryBindModel;
import bg.obag.obag.model.entity.CategoryEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.CategoryServiceModel;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.repo.CategoryRepo;
import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepo categoryRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, UserService userService, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeCategories() {
        if (categoryRepo.count() == 0) {
            UserServiceModel userServiceModel = userService.findById(1L);
            UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
            categoryRepo.saveAll(List.of(
                    // bags
                    new CategoryEntity().setCategory("OBAG").setPriority(1).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OMINI").setPriority(2).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OURBAN").setPriority(3).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OREVERSE").setPriority(4).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("ODOC").setPriority(5).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OSHARM").setPriority(6).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OUNIQUE").setPriority(7).setDeleted(false).setCreatedBy(userEntity),
                    // shoulder
                    new CategoryEntity().setCategory("OPOCKET").setPriority(8).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OGLAM").setPriority(9).setDeleted(false).setCreatedBy(userEntity),
                    new CategoryEntity().setCategory("OCROSSY").setPriority(10).setDeleted(false).setCreatedBy(userEntity),
                    // handles
                    new CategoryEntity().setCategory("HANDLES").setPriority(11).setDeleted(false).setCreatedBy(userEntity),
                    // backpack
                    new CategoryEntity().setCategory("BACKPACK").setPriority(12).setDeleted(false).setCreatedBy(userEntity),
                    // wallet
                    new CategoryEntity().setCategory("WALLET").setPriority(13).setDeleted(false).setCreatedBy(userEntity),
                    // clocks
                    new CategoryEntity().setCategory("OCLOCK").setPriority(14).setDeleted(false).setCreatedBy(userEntity),
                    // accessories
                    new CategoryEntity().setCategory("OACCESSORIES").setPriority(15).setDeleted(false).setCreatedBy(userEntity),
                    // shoes
                    new CategoryEntity().setCategory("OSLIPPERS").setPriority(16).setDeleted(false).setCreatedBy(userEntity),
                    // consumables
                    new CategoryEntity().setCategory("PACKING").setPriority(99).setDeleted(false).setCreatedBy(userEntity)
            ));
        }
    }

    @Override
    public CategoryServiceModel findByCategory(String category) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryRepo.findByCategory(category)
                .orElseThrow(() -> new CategoryNotFoundException("Category name " + category + " not found in database."));
        return modelMapper.map(categoryEntity, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllOrderByPriorityAsc() {
        return categoryRepo.findAllByOrderByPriorityAsc().stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel addEditCategory(CategoryServiceModel categoryServiceModel, Principal principal) throws CategoryNotFoundException {
        CategoryEntity categoryEntity;
        if (categoryServiceModel.getId() == null) {
            categoryEntity = modelMapper.map(categoryServiceModel, CategoryEntity.class);
        } else {
            categoryEntity = categoryRepo.findById(categoryServiceModel.getId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category with id " + categoryServiceModel.getId() + " not found in database."));
            categoryEntity.setCategory(categoryServiceModel.getCategory())
                    .setPriority(categoryServiceModel.getPriority())
                    .setImage(categoryServiceModel.getImage())
                    .setDeleted(categoryServiceModel.isDeleted());
        }
        categoryEntity.setCreatedBy(userService.findByEmail(principal.getName()).get());

        CategoryEntity savedCategoryEntity = categoryRepo.save(categoryEntity);

        return modelMapper.map(savedCategoryEntity, CategoryServiceModel.class);
    }

    @Override
    public boolean existsCategory(String category) {
        return categoryRepo.existsByCategory(category);
    }

    @Override
    public boolean existsCategoryExceptId(String category, Long id) {
        return categoryRepo.findByCategoryExceptId(category, id).isPresent();
    }

    @Override
    public CategoryServiceModel findById(Long id) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryRepo.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found in database."));
        return modelMapper.map(categoryEntity, CategoryServiceModel.class)
                .setCreatedBy(categoryEntity.getCreatedBy().getEmail());
    }
}
