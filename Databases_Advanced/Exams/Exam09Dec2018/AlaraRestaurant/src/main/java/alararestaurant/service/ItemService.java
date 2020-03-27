package alararestaurant.service;

import alararestaurant.domain.entities.Item;

import java.util.Optional;

public interface ItemService {

    Boolean itemsAreImported();

    String readItemsJsonFile();

    String importItems(String items);

    Optional<Item> getByName(String name);
}
