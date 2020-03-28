package softuni.exam.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.exam.util.*;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public JsonParser jsonParser(){
        return new JsonParserImpl();
    }

    @Bean
    public XmlParser xmlParser (){
        return new  XmlParserImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
