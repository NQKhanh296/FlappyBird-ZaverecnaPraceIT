import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    public void testPassed(){
        Game game = new Game();
        game.getPlacePipes().getPipes().add(new Pipe(Resources.topPipeImg));
        game.getPlacePipes().getPipes().get(0).setX(200);
        if(game.getBird().getX() > game.getPlacePipes().getPipes().get(0).getX() +  game.getPlacePipes().getPipes().get(0).getPipeWidth()){
            game.getBirdAndGroundTimer().stop();
        }
        if(!game.getBirdAndGroundTimer().isRunning()){
            assertTrue(game.getPlacePipes().getPipes().get(0).isPassed());
        }
    }
    @Test
    public void testScoreIncrementation(){
        Game game = new Game();
        game.getPlacePipes().getPipes().add(new Pipe(Resources.topPipeImg));
        game.getPlacePipes().getPipes().get(0).setX(200);
        if(game.getPlacePipes().getPipes().get(0).isPassed()){
            game.getBirdAndGroundTimer().stop();
        }
        if(!game.getBirdAndGroundTimer().isRunning()){
            assertEquals(0.5,game.getScore());
        }
    }
    @Test
    public void testBirdFlying(){
        Game game = new Game();
        game.setBirdFlying(false);
        game.addBirdVelocity();
        assertTrue(game.isBirdFlying());
    }
    @Test
    public void testGiveGoldMedal(){
        Game game = new Game();
        game.setHighScore(10);
        game.setScore(21);
        game.updateMedals();
        assertTrue(game.isGiveGoldMedal());
    }
    @Test
    public void testGiveSilverMedal(){
        Game game = new Game();
        game.setHighScore(10);
        game.setScore(15);
        game.updateMedals();
        assertTrue(game.isGiveSilverMedal());
    }

}