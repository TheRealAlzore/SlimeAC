package ac.slime.check;

import ac.slime.check.Check;
import ac.slime.player.SlimePlayer;

public class CheckDispatcher {

    public void handle(SlimePlayer slimePlayer) {
        for (Check check : slimePlayer.getChecks()) {
            if (check.isEnabled()) {
                check.handle();
            }
        }
    }
}