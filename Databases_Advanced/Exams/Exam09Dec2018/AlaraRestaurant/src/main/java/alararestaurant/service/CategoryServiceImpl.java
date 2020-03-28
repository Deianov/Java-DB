package alararestaurant.service;

import alararestaurant.constant.Constants;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder result = new StringBuilder();

        List<Category> categories = repository.getCategoriesOrderByItems();

        if(categories == null || categories.size() == 0){
            return Constants.NOT_FOUND;
        }

        for (Category category : categories) {

            System.out.println("Category: " + category.getName());

            result
                    .append("Category: ")
                    .append(category.getName())
                    .append(System.lineSeparator());

            for (Item item: category.getItems()) {

                result
                        .append("--- Item Name: ")
                        .append(item.getName())
                        .append(System.lineSeparator())
                        .append("--- Item Price: ")
                        .append(item.getPrice().toString())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
            }
        }
        return result.toString();
    }
}
