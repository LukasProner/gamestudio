package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;


@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating = (Double) entityManager.createNamedQuery("Rating.getAverageRating")
                .setParameter("game", game)
                .getSingleResult();
        return averageRating != null ? averageRating.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Rating rating = entityManager.createNamedQuery("Rating.getRatingByPlayer", Rating.class)
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();

            return rating != null ? rating.getRating() : 0;
        } catch (NoResultException e) {
            return 0;
        } catch (Exception e) {
            throw new RatingException("Problem getting rating by player", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}