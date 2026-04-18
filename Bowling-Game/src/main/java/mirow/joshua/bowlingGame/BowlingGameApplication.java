package mirow.joshua.bowlingGame;

import mirow.joshua.bowlingGame.display.ConsoleInput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BowlingGameApplication {

	public static void main(String[] args) {
		ApplicationContext context =
				SpringApplication.run(BowlingGameApplication.class, args);
		ConsoleInput consoleInput = context.getBean(ConsoleInput.class);
		consoleInput.starteSpiel();
	}

}
