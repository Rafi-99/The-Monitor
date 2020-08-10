package bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Monitor {
	
     public static JDA jda;
     public static String prefix = "m!";
	
	public static void main (String [] args) throws LoginException {
		
		jda = JDABuilder.createDefault("NzExNzAzODUyOTc3NDg3OTAz.XsG33Q.sO36j1H_2pcuMNDfdYRINK6Zm0g").build();
        jda.getPresence().setActivity(Activity.watching("EVERYONE"));
          
        jda.addEventListener(new Commands());
	}

}
