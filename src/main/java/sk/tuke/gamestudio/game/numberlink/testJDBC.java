package sk.tuke.gamestudio.game.numberlink;


/*import sk.tuke.kpi.kp.numberlink.entity.Score;
import sk.tuke.kpi.kp.numberlink.service.ScoreService;
import sk.tuke.kpi.kp.numberlink.service.ScoreServiceJDBC;
*/


import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Date;

public class testJDBC {
    public static void main(String[] agrs) throws Exception {
        RatingService service = new RatingServiceJDBC();
        service.reset();
        service.setRating(new Rating("numberlink", "Jano", 5, new Date()));
        service.setRating(new Rating("numberlink", "Milan", 4, new Date()));
        service.setRating(new Rating("numberlink", "Janik", 3, new Date()));
        service.setRating(new Rating("numberlink", "James", 2, new Date()));

        var scores = service.getRating("numberlink", "Janik");
        System.out.println(scores);
        var average = service.getAverageRating("numberlink");
        System.out.println(average);
        /*ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("mines","Jano",112,new Date()));
        service.addScore(new Score("numberlink","Milan",116,new Date()));
        service.addScore(new Score("mines","Janik",120,new Date()));
        service.addScore(new Score("mines","James",100,new Date()));

        var scores = service.getTopScores("mines");
        System.out.println(scores);*/
    }
}
