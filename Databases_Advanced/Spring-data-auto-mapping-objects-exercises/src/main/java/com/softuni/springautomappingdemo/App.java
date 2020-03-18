package com.softuni.springautomappingdemo;

import com.softuni.springautomappingdemo.commands.CommandFactory;
import com.softuni.springautomappingdemo.commands.Executable;
import com.softuni.springautomappingdemo.utils.UserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

import static com.softuni.springautomappingdemo.constants.CommandConstants.*;

@Component
public class App implements CommandLineRunner {

    private final CommandFactory commandFactory;
    private final BufferedReader reader;
    private final UserOutput userOutput;


    @Autowired
    public App(CommandFactory commandFactory, BufferedReader reader, UserOutput userOutput) {
        this.commandFactory = commandFactory;
        this.reader = reader;
        this.userOutput = userOutput;
    }

    @Override
    public void run(String... args) throws Exception {

        userOutput.print("Enter input:");

        while (true){

            String line = reader.readLine();
            if (line == null || line.isBlank()){
                continue;
            }

            String[] input = line.split(INPUT_SEPARATOR);
            String commandName = input[0];

            if(COMMAND_END.equals(commandName)){
                break;
            }

            try {
                Executable command = commandFactory.createInstance(commandName);

                if (command == null){
                    userOutput.print("Bad command name.");
                } else {
                    userOutput.print(command.execute(input));
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
