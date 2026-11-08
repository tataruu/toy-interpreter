package interpreter.view;

import interpreter.controller.Controller;

import java.text.CompactNumberFormat;

public class RunExampleCommand extends Command {
    private final Controller controller;
    private boolean executed = false;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        if(executed){
            System.out.println("This command has already been executed");
            return;
        }
        executed = true;
        try {
            controller.allStep();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
