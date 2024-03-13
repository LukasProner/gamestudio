package sk.tuke.gamestudio.game.numberlink;


/*import sk.tuke.kpi.kp.numberlink.entity.Score;
import sk.tuke.kpi.kp.numberlink.service.ScoreService;
import sk.tuke.kpi.kp.numberlink.service.ScoreServiceJDBC;
*/


import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.sql.DriverManager;
import java.util.Date;

public class testJDBC {
    public static void main(String[] agrs) throws Exception {
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("mines","Jano",112,new Date()));
        service.addScore(new Score("numberlink","Milan",116,new Date()));
        service.addScore(new Score("mines","Janik",120,new Date()));
        service.addScore(new Score("mines","James",100,new Date()));

        var scores = service.getTopScores("mines");
        System.out.println(scores);
    }
}
