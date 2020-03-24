package com.productshop.service;

import com.productshop.constant.Messages;
import com.productshop.exception.NotFoundUsersException;
import com.productshop.model.dto.seed.UserDto;
import com.productshop.model.dto.view.query2.UserSoldProductsDto;
import com.productshop.model.dto.view.query4.ProductNamePriceDto;
import com.productshop.model.dto.view.query4.ProductNamePriceRootDto;
import com.productshop.model.dto.view.query4.UserSoldProductsAndCountDto;
import com.productshop.model.entity.User;
import com.productshop.repository.UserRepository;
import com.productshop.util.RandomUtil;
import com.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final RandomUtil randomUtil;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper, ValidationUtil validationUtil, RandomUtil randomUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.randomUtil = randomUtil;
    }

    @Override
    public boolean isFoundUsers() {
        return this.repository.count() > 0;
    }

    @Override
    public void seedAll(List<UserDto> dtos) {

        if (dtos == null){
            return;
        }

        for (UserDto dto : dtos) {
            if(!validationUtil.isValid(dto)){
                validationUtil.printViolations(dto);
                continue;
            }
            repository.saveAndFlush(mapper.map(dto, User.class));
        }
    }

    @Override
    public void setUsersFiends(){

        if(!isFoundUsers()){
            throw new NotFoundUsersException(Messages.NOT_FOUND_USERS);
        }

        List<User> users = this.repository.findAll();
        for (User user : repository.findAll()) {

            int friendsCount = randomUtil.randomInt(5);

            for (int i = 0; i < friendsCount ; i++) {
                User friend = this.getRandom();
                if (friend != null){
                    friend.addFriend(user);
                    user.addFriend(friend);
                }
            }
        }
        this.repository.saveAll(users);
    }

    @Override
    public User getRandom() {
        long randomId = randomUtil.randomId(this.repository.count());
        return this.repository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldProductsDto> query2SuccessfullySoldProducts() {

        List<UserSoldProductsDto> list =
                this.findAllUsersWithSoldProductsSorted()
                        .stream()
                        .map(obj -> mapper.map(obj, UserSoldProductsDto.class))
                        .collect(Collectors.toList());

        for (UserSoldProductsDto dto : list) {
            dto.setSoldProducts(
                    dto.getSoldProducts()
                            .stream()
                            .filter(product -> product.getBuyerLastName() != null)
                            .collect(Collectors.toSet())
            );
        }
        return list;
    }

    private List<User> findAllUsersWithSoldProductsSorted(){
        List<User> list =
                repository.findAll()
                        .stream()
                        .filter(user -> user.getSoldProducts()
                                .stream()
                                .anyMatch(product -> product.getBuyer() != null)
                        )
                        .sorted(Comparator.comparing(User::getLastName)
                                .thenComparing(user -> user.getFirstName() == null ? "" : user.getFirstName())
                        )
                        .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<UserSoldProductsAndCountDto> query4UsersAndProducts() {
        List<User> users = this.findAllUsersWithSoldProductsSorted();
        List<UserSoldProductsAndCountDto> dtos = new ArrayList<>();

        for (User user: users) {

            UserSoldProductsAndCountDto dto = mapper
                    .map(user, UserSoldProductsAndCountDto.class);

            List<ProductNamePriceDto> soldProducts =
                    user.getSoldProducts()
                            .stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(obj -> mapper.map(obj, ProductNamePriceDto.class))
                            .collect(Collectors.toList());

            dto.setSoldProducts(new ProductNamePriceRootDto(soldProducts.size(), soldProducts));
            dtos.add(dto);
        }
        return
                dtos.stream()
                        .sorted((e1, e2) -> Integer.compare(e2.getSoldProducts().getSoldProducts().size(), e1.getSoldProducts().getSoldProducts().size()))
                        .collect(Collectors.toList());
    }
}
