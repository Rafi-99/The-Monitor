package bot.commands.admin;

import bot.commands.CommandContext;
import bot.commands.CommandInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test implements CommandInterface {
    boolean stop = false;

    @Override
    public void handle(CommandContext c) {
        if(c.getCommandParameters().size() == 1 && c.getMember().getId().equals("398215411998654466")) {

            if(c.getCommandParameters().get(0).equals("-s")) {
                ScheduledExecutorService test = Executors.newSingleThreadScheduledExecutor();
                test.scheduleAtFixedRate(() -> {
                    c.getChannel().sendMessage("Poggers!").queue();
                    if(stop) {
                        c.getChannel().sendTyping().queue();
                        c.getChannel().sendMessage("Ending spam now...").queue();
                        c.getChannel().sendMessage("Ended.").queue();
                        test.shutdownNow();
                        stop = false;
                    }
                }, 0, 1, TimeUnit.SECONDS);

            }
            else if(c.getCommandParameters().get(0).equals("-t")) {
                stop = true;
            }
        }
    }

    @Override
    public String getName() {
        return "test";
    }
}