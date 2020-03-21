package com.productsshop.service;

import com.productsshop.model.dtos.UserDto;
import com.productsshop.model.dtos.query2.ProductBoughtModel;
import com.productsshop.model.dtos.query2.UserSoldModel;
import com.productsshop.model.dtos.query4.A_Users;
import com.productsshop.model.dtos.query4.BC_User;
import com.productsshop.model.dtos.query4.BD_User;
import com.productsshop.model.dtos.query4.C_Products;
import com.productsshop.model.entities.User;
import com.productsshop.repository.UserRepository;
import com.productsshop.util.RandomUtil;
import com.productsshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void seedAll(UserDto[] userDtos) {
        if(repository.count() > 0){
            return;
        }

        for (UserDto dto : userDtos) {
            if(!validationUtil.isValid(dto)){
                validationUtil.printViolations(dto);
                continue;
            }
            repository.saveAndFlush(mapper.map(dto, User.class));
        }
    }

    @Override
    public void setUsersFiends(){
        List<User> users = this.repository.findAll();
        for (User user : users) {

            if (user.getFriends().size() == 0){
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
    public List<UserSoldModel> findAllUsersWithSoldProducts() {

        List<UserSoldModel> dtos =
        this.repository.findAllUsersWithSoldProducts()
                .stream()
                .map(user -> mapper.map(user, UserSoldModel.class))
                .collect(Collectors.toList());

        // delete products without buyer

        for (UserSoldModel dto : dtos) {
            List<ProductBoughtModel> products = dto.getSold()
                    .stream()
                    .filter(p -> p.getBuyerLastName() != null)
                    .collect(Collectors.toList());

            dto.setSold(products);
        }
        return dtos;
    }

    @Override
    public A_Users findAllUsersWithSoldProductsAndCount() {
        List<BD_User> dtos =
                this.repository.findAllUsersWithSoldProducts()
                        .stream()
                        .map(user -> mapper.map(user, BD_User.class))
                        .collect(Collectors.toList());

        List<BC_User> dtosWithCount = dtos
                .stream()
                .map(BD ->
                    new BC_User(
                            BD.getFirstName(),
                            BD.getLastName(),
                            BD.getAge(),
                            new C_Products(
                                    BD.getSold().size(),
                                    BD.getSold())
                            )

                )
                .collect(Collectors.toList());

        return new A_Users(dtosWithCount.size(), dtosWithCount);
    }


    @Override
    public User getRandom() {
        Long randomId = randomUtil.randomId(this.repository.count()).orElse(null);
        return randomId == null ? null : this.repository.findById(randomId).orElse(null);
    }
}
