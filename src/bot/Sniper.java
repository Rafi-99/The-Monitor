package bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Sniper {
	
     public static JDA jda;
     public static String prefix = "s!";
	
	public static void main (String [] args) throws LoginException {
		
		jda = JDABuilder.createDefault("NzExNzAzODUyOTc3NDg3OTAz.XwbJhw.ad7qc8GQm8E7RcPKzKW8cS68sPQ").build();
        jda.getPresence().setActivity(Activity.watching("EVERYONE"));
          
        jda.addEventListener(new Commands());
	}

}
