package alararestaurant.service;

import alararestaurant.constant.Constants;
import alararestaurant.domain.dtos.OrderSeedDto;
import alararestaurant.domain.dtos.OrderSeedItemDto;
import alararestaurant.domain.dtos.OrderSeedRootDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.OrderRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final EmployeeService employeeService;
    private final ItemService itemService;
    private final PositionRepository positionRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, FileUtil fileUtil, EmployeeService employeeService, ItemService itemService, PositionRepository positionRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.employeeService = employeeService;
        this.itemService = itemService;
        this.positionRepository = positionRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        return repository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() {
        return fileUtil.readFile(Constants.ORDERS_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException, FileNotFoundException {
        StringBuilder result = new StringBuilder();

        OrderSeedRootDto rootDto = xmlParser
                .unmarshalFromFile(Constants.ORDERS_FILE_PATH, OrderSeedRootDto.class);

        if(rootDto == null){
            return Constants.NOT_FOUND;
        }

        Collection<OrderSeedDto> dtos = rootDto.getOrders();

        if(dtos == null || dtos.size() == 0){
            return (Constants.NOT_FOUND);
        }

        for (OrderSeedDto dto : dtos) {

            if (validationUtil.isValid(dto)){

                Employee employee = employeeService
                        .getByName(dto.getEmployee()).orElse(null);

                if(employee == null){
                    result
                            .append(Constants.INCORRECT_DATA_MESSAGE)
                            .append(System.lineSeparator());
                    continue;
                }

                Order order = repository
                        .findByCustomerAndDateTime(dto.getCustomer(), dto.getDateTime()).orElse(null);

                if (order != null){
                    result
                            .append(Constants.EXISTS)
                            .append(System.lineSeparator());
                    continue;
                }


                order = mapper.map(dto, Order.class);

                Collection<OrderItem> orderItems = new ArrayList<>();
                boolean isValidItems = false;

                for (OrderSeedItemDto itemDto: dto.getOrderItems().getItems()) {
                    Item item = itemService.getByName(itemDto.getName()).orElse(null);

                    if(item != null){
                        isValidItems = true;
                        orderItems.add(new OrderItem(order, item, itemDto.getQuantity()));


                    }else {
                        isValidItems = false;
                        break;
                    }
                }

                OrderType type = null;
                if( Arrays.stream(OrderType.values())
                        .anyMatch(value -> value.toString().equals(dto.getType()))){

                    type = OrderType.valueOf(dto.getType());
                }

                if(isValidItems && type != null){
                    order.setType(type);
                    order.setEmployee(employee);
                    order.setOrderItems(orderItems);
                    repository.saveAndFlush(order);

                    result.append(String.format("Order for %s on %s added",
                            order.getCustomer(),
                            order.getDateTime().toString())
                    );
                }

            }else {
                result.append(Constants.INCORRECT_DATA_MESSAGE);
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        String POSITION_NAME = "Burger Flipper";

        StringBuilder result = new StringBuilder();

        Position position = positionRepository.findByName(POSITION_NAME).orElse(null);
        List<Employee> employeesWithPosition =
                employeeService.getEmployeesByPositionOrderByNameAscOrdersIdAsc(position);

        if(employeesWithPosition == null){
            return Constants.NOT_FOUND;
        }

        for (Employee employee : employeesWithPosition) {
            result
                    .append("Name: ")
                    .append(employee.getName())
                    .append(System.lineSeparator())
                    .append("Orders:")
                    .append(System.lineSeparator());

            for (Order order : employee.getOrders()) {
                result
                        .append("\tCustomer: ")
                        .append(order.getCustomer())
                        .append(System.lineSeparator())
                        .append("\tItems: ")
                        .append(System.lineSeparator());

                for (OrderItem orderItem : order.getOrderItems()) {

                    Item item = orderItem.getItem();
                    result
                            .append("\t\tName: ")
                            .append(item.getName())
                            .append(System.lineSeparator())
                            .append("\t\tPrice: ")
                            .append(item.getPrice())
                            .append(System.lineSeparator())
                            .append("\t\tQuantity: ")
                            .append(orderItem.getQuantity())
                            .append(System.lineSeparator())
                            .append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }
}
