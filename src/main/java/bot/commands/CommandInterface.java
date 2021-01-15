package bot.commands;

public interface CommandInterface {

    void handle(CommandContext c);

    String getName();

    //String getHelp();
}