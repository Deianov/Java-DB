package com.softuni.springautomappingdemo.services;

import com.softuni.springautomappingdemo.domain.dtos.UserLoggedDto;
import com.softuni.springautomappingdemo.domain.dtos.UserOwnedDto;
import com.softuni.springautomappingdemo.domain.dtos.UserRegisterDto;
import com.softuni.springautomappingdemo.domain.entities.Game;
import com.softuni.springautomappingdemo.domain.entities.Role;
import com.softuni.springautomappingdemo.domain.entities.User;
import com.softuni.springautomappingdemo.repositories.UserRepository;
import com.softuni.springautomappingdemo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static com.softuni.springautomappingdemo.constants.Messages.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {

        // validate
        if (!validationUtil.isValid(userRegisterDto)){
            return
                    validationUtil
                            .getViolations(userRegisterDto)
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(System.lineSeparator()));
        }

        // already exists
        if (userRepository.existsUserByEmail(userRegisterDto.getEmail())){
            return String.format(USER_ALREADY_REGISTERED, userRegisterDto.getEmail());
        }

        // create user
        User user = modelMapper.map(userRegisterDto, User.class);

        user.setRole(userRepository.count() == 0 ? Role.ADMIN : Role.USER);

        userRepository.save(user);
        return String.format(USER_REGISTERED, user.getFullName());
    }

    @Override
    public UserLoggedDto loginUser(String email, String password) {

        if(email == null || password == null){
            return null;
        }

        User user =  userRepository.findUserByEmailAndPassword(email, password).orElse(null);

        if(user != null){
            return modelMapper.map(user, UserLoggedDto.class);
        }
        return null;
    }

    @Override
    public String addItemToCart(UserLoggedDto userLoggedDto, Game game) {

        User user = userRepository
                .findUserByEmailAndPassword(userLoggedDto.getEmail(), userLoggedDto.getPassword()).orElse(null);

        if(user == null){
            return String.format(USER_NOT_FOUND, userLoggedDto.getFullName());
        }

        boolean isAlreadyInCart = user
                .getShoppingCart()
                .stream()
                .anyMatch(g -> g.getId() == game.getId());

        if (isAlreadyInCart){
            return GAME_EXIST_IN_CART;
        }

        boolean isAlreadyOwned = user
                .getGames()
                .stream()
                .anyMatch(g -> g.getId() == game.getId());

        if (isAlreadyOwned){
            return GAME_ALREADY_OWNED;
        }

        user.getShoppingCart().add(game);
        userRepository.save(user);

        return String.format(GAME_ADDED_TO_CARD, game.getTitle());
    }

    @Override
    public String removeItemFromCart(UserLoggedDto userLoggedDto, Game game) {
        User user = userRepository
                .findUserByEmailAndPassword(userLoggedDto.getEmail(), userLoggedDto.getPassword()).orElse(null);

        if(user == null){
            return String.format(USER_NOT_FOUND, userLoggedDto.getFullName());
        }

        boolean isInCart = user
                .getShoppingCart()
                .stream()
                .anyMatch(g -> g.getId() == game.getId());

        if (isInCart){
            user.getShoppingCart().remove(game);
            userRepository.save(user);

            return String.format(GAME_REMOVED_FROM_CARD, game.getTitle());

        } else {
            return String.format(GAME_NOT_FOUND_IN_CARD, game.getTitle());
        }
    }

    @Override
    public String buyItemFromCart(UserLoggedDto userLoggedDto) {
        User user = userRepository
                .findUserByEmailAndPassword(userLoggedDto.getEmail(), userLoggedDto.getPassword()).orElse(null);

        if(user == null){
            return String.format(USER_NOT_FOUND, userLoggedDto.getFullName());
        }

        String boughtGames = user
                .getShoppingCart()
                .stream()
                .map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()+" -"));

        user.getShoppingCart().forEach(user.getGames()::add);
        user.setShoppingCart(new LinkedHashSet<>());
        userRepository.save(user);

        return String.format(USER_BOUGHT_GAMES, boughtGames);
    }

    @Override
    public UserOwnedDto getOwnedGames(UserLoggedDto userLoggedDto) {
        User user = userRepository
                .findUserByEmailAndPassword(userLoggedDto.getEmail(), userLoggedDto.getPassword()).orElse(null);

        if(user != null){
            UserOwnedDto ownedDto = new UserOwnedDto();
            ownedDto.setGames(
                    user.getGames()
                    .stream()
                    .map(Game::getTitle)
                    .toArray(String[]::new)
            );
            return ownedDto;
        }
        return null;
    }
}
