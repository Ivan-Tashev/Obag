package bg.obag.obag.service;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.model.binding.CategoryBindModel;
import bg.obag.obag.model.service.CategoryServiceModel;

import java.security.Principal;
import java.util.List;

public interface CategoryService {

    void initializeCategories();

    CategoryServiceModel findByCategory(String category) throws CategoryNotFoundException;

    List<CategoryServiceModel> findAllOrderByPriorityAsc();

    CategoryServiceModel addCategory(CategoryBindModel categoryBindModel, Principal principal);

    CategoryServiceModel updateCategory(CategoryBindModel categoryBindModel, Principal principal) throws CategoryNotFoundException;

    boolean existsCategory(String category);

    boolean existsCategoryExceptId(String category, Long id);

    CategoryServiceModel findById(Long id) throws CategoryNotFoundException;


}
