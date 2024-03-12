package sk.tuke.gamestudio.game.numberlink;


/*import sk.tuke.kpi.kp.numberlink.entity.Score;
import sk.tuke.kpi.kp.numberlink.service.ScoreService;
import sk.tuke.kpi.kp.numberlink.service.ScoreServiceJDBC;
*/


import java.sql.DriverManager;
import java.util.Date;

public class testJDBC {
    /*public static void main(String[] agrs) throws Exception {
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("jaro","mines",112,new Date()));
        service.addScore(new Score("jaro","mines",116,new Date()));
        service.addScore(new Score("jaro","numberlink",120,new Date()));
        service.addScore(new Score("jaro","mines",100,new Date()));

        var scores = service.getTopScores("mines");
        System.out.println(scores);
    }*/
}
