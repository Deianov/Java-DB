package alararestaurant.service;

import alararestaurant.constant.Constants;
import alararestaurant.domain.dtos.ItemSeedDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.JsonParser;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final JsonParser jsonParser;
    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository repository, ModelMapper mapper, ValidationUtil validationUtil, JsonParser jsonParser, FileUtil fileUtil, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.jsonParser = jsonParser;
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean itemsAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() {
        return fileUtil.readFile(Constants.ITEMS_FILE_PATH);
    }

    @Override
    public String importItems(String items) {
        StringBuilder result = new StringBuilder();

        ItemSeedDto[] dtos = jsonParser
                .objectFromFile(Constants.ITEMS_FILE_PATH, ItemSeedDto[].class);

        if(dtos == null || dtos.length == 0){
            return (Constants.NOT_FOUND);
        }

        for (ItemSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                if(repository.findByName(dto.getName())
                        .isEmpty())
                {
                    Item item = mapper.map(dto, Item.class);

                    Category category = categoryRepository.findByName(dto.getCategory())
                            .orElse(new Category(dto.getCategory()));
                    item.setCategory(category);

                    repository.saveAndFlush(item);

                    result.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                            item.getName())
                    );

                } else {
                    result.append(Constants.EXISTS);
                }

            }else {
                result.append(Constants.INCORRECT_DATA_MESSAGE);
            }

            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public Optional<Item> getByName(String name) {
        return repository.findByName(name);
    }
}
