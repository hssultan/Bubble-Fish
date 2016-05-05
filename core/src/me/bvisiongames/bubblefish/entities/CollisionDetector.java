package me.bvisiongames.bubblefish.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import me.bvisiongames.bubblefish.controllers.ControllerManager;
import me.bvisiongames.bubblefish.effects.SoundEffect;
import me.bvisiongames.bubblefish.settings.GameInfo;

/**
 * Created by ahzji_000 on 2/22/2016.
 */
public class CollisionDetector {


    //checkers
    /**
     * check whether a bubble has hit the main fish.
     * and act accordingly.
     */
    public static synchronized void checkBubbleMainFishCollision(Contact contact){

        //first get bodies' user data
        Body bodyA = contact.getFixtureA().getBody(),
                bodyB = contact.getFixtureB().getBody();

        //check whether the bodies's user data are not null
        if(bodyA.getUserData() != null && bodyB.getUserData() != null){

            //check which is the bubble and which is the main fish
            if(bodyA.getUserData() instanceof MainFish && bodyB.getUserData() instanceof BubblesManager.Bubble){
                MainFish mainFish = (MainFish)bodyA.getUserData();
                BubblesManager.Bubble bubble = (BubblesManager.Bubble)bodyB.getUserData();
                //if bubble is alive then enable harm
                if(bubble.alive && !bubble.bursted){
                    //then burst the bubble
                    bubble.burst();
                    //kill the main fish
                    mainFish.kill();
                }
            }else
            if(bodyB.getUserData() instanceof MainFish && bodyA.getUserData() instanceof BubblesManager.Bubble){
                MainFish mainFish = (MainFish)bodyB.getUserData();
                BubblesManager.Bubble bubble = (BubblesManager.Bubble)bodyA.getUserData();
                //if bubble is alive then enable harm
                if(bubble.alive && !bubble.bursted){
                    //then burst the bubble
                    bubble.burst();
                    //kill the main fish
                    mainFish.kill();
                }
            }

        }

    }
    /**
     * check whether a bubble has passed the main fish
     * and add the score
     */
    public static synchronized void checkBubblePass(Contact contact, SoundEffect soundEffect){

        //first get bodies' user data
        Fixture fixtureA = contact.getFixtureA(),
                fixtureB = contact.getFixtureB();

        //check whether the bodies's user data are not null
        if(fixtureA.getUserData() != null && fixtureB.getUserData() != null){

            //check which is the bubble and which is the main fish
            if(fixtureA.getUserData() instanceof MainFish.FISHSENSORS
                    && fixtureB.getUserData() instanceof BubblesManager.BUBBLEIDS){

                //check whether the bubble id is the center and the fish sensor is the scoring
                if( fixtureA.getUserData() == MainFish.FISHSENSORS.SCORING
                    && fixtureB.getUserData() == BubblesManager.BUBBLEIDS.CENTER){
                    //increment current score
                    //GameInfo.CURRENT_GAME_SCORE++;
                    //soundEffect.playCoinSound();
                }

            }else
            if(fixtureB.getUserData() instanceof MainFish.FISHSENSORS
                    && fixtureA.getUserData() instanceof BubblesManager.Bubble){

                //check whether the bubble id is the center and the fish sensor is the scoring
                if(fixtureB.getUserData() == MainFish.FISHSENSORS.SCORING
                        && fixtureA.getUserData() == BubblesManager.BUBBLEIDS.CENTER){
                    //increment current score
                    //GameInfo.CURRENT_GAME_SCORE++;
                    //soundEffect.playCoinSound();
                }

            }

        }

    }
    /**
     * check whether a bubble has finished its round
     */
    public static synchronized void checkBubblesRound(Contact contact){

        //first get bodies' user data
        Fixture fixtureA = contact.getFixtureA(),
                fixtureB = contact.getFixtureB();

        //check whether the bodies's user data are not null
        if(fixtureA.getUserData() != null && fixtureB.getUserData() != null){

            //check which is the bubble and which is the main fish
            if(fixtureA.getUserData() instanceof Sensors
                    && fixtureB.getUserData() instanceof BubblesManager.BUBBLEIDS){

                //check whether the bubble id is the center and the fish sensor is the scoring
                if( fixtureA.getUserData() == Sensors.MAP_BOUNDERIES
                        && fixtureB.getUserData() == BubblesManager.BUBBLEIDS.CENTER){
                    //reset the round of this bubble
                    ((BubblesManager.Bubble)fixtureB.getBody().getUserData()).resetRound();
                }

            }else
            if(fixtureB.getUserData() instanceof Sensors
                    && fixtureA.getUserData() instanceof BubblesManager.BUBBLEIDS){

                //check whether the bubble id is the center and the fish sensor is the scoring
                if(fixtureB.getUserData() == Sensors.MAP_BOUNDERIES
                        && fixtureA.getUserData() == BubblesManager.BUBBLEIDS.CENTER){
                    //reset the round of this bubble
                    ((BubblesManager.Bubble)fixtureA.getBody().getUserData()).resetRound();
                }

            }

        }

    }
    /**
     * check whether a bullet has hit another bubble
     */
    public static synchronized void checkBulletBubble(Contact contact,
                                                      SoundEffect soundEffect,
                                                      ControllerManager controllerManager){

        Body bodyA = contact.getFixtureA().getBody(),
                bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() != null && bodyB.getUserData() != null){

            //if body A is a bullet
            if(bodyA.getUserData() instanceof BulletManager.Bullet
                    && ((BulletManager.Bullet)bodyA.getUserData()).isAlive
                    && bodyB.getUserData() instanceof BubblesManager.Bubble){
                BubblesManager.Bubble bubble = (BubblesManager.Bubble)bodyB.getUserData();
                if(!bubble.bursted && bubble.alive){
                    bubble.burst();
                    ((BulletManager.Bullet)bodyA.getUserData()).kill();
                    GameInfo.CURRENT_GAME_SCORE++;
                    controllerManager.score.setText("Score: "+GameInfo.CURRENT_GAME_SCORE);
                    soundEffect.playCoinSound();
                }
            }else
            //if body B is a bullet
            if(bodyB.getUserData() instanceof BulletManager.Bullet
                    && ((BulletManager.Bullet)bodyB.getUserData()).isAlive
                    && bodyA.getUserData() instanceof BubblesManager.Bubble){
                BubblesManager.Bubble bubble = (BubblesManager.Bubble)bodyA.getUserData();
                if(!bubble.bursted && bubble.alive){
                    bubble.burst();
                    ((BulletManager.Bullet)bodyB.getUserData()).kill();
                    GameInfo.CURRENT_GAME_SCORE++;
                    controllerManager.score.setText("Score: "+GameInfo.CURRENT_GAME_SCORE);
                    soundEffect.playCoinSound();
                }
            }

        }

        //kill the bullet if it was either a or b
        if(bodyA.getUserData() != null
                && bodyA.getUserData() instanceof BulletManager.Bullet
                && (bodyB.getUserData() == null || (bodyB.getUserData() != null && !(bodyB.getUserData() instanceof MainFish
                                                                                    || bodyB.getUserData() instanceof BulletManager.Bullet
                                                                                    || bodyB.getUserData() instanceof BulletSupplyManager.BulletSupplyEntity ) ))
                 ){
            if(bodyB.getUserData() == null ||
                    (bodyB.getUserData() != null && !(bodyB.getUserData() instanceof BubblesManager.Bubble) ) ){
                ((BulletManager.Bullet) bodyA.getUserData()).kill();
            }

        }else
        if(bodyB.getUserData() != null
                && bodyB.getUserData() instanceof BulletManager.Bullet
                && (bodyA.getUserData() == null || (bodyA.getUserData() != null && !(bodyA.getUserData() instanceof MainFish
                                                                                    || bodyA.getUserData() instanceof BulletManager.Bullet
                                                                                    || bodyA.getUserData() instanceof BulletSupplyManager.BulletSupplyEntity) ))
                ){
            if(bodyA.getUserData() == null ||
                    (bodyA.getUserData() != null && !(bodyA.getUserData() instanceof BubblesManager.Bubble) ) ){
                ((BulletManager.Bullet) bodyB.getUserData()).kill();
            }

        }

    }
    /**
     * check whether a bullet supply has hit the main fish.
     * and act accordingly.
     */
    public static synchronized void checkBulletSupplyEntityMainFishCollision(Contact contact, Label scoreDisplay){

        //first get bodies' user data
        Body bodyA = contact.getFixtureA().getBody(),
                bodyB = contact.getFixtureB().getBody();

        //check whether the bodies's user data are not null
        if(bodyA.getUserData() != null && bodyB.getUserData() != null){

            //check which is the bullet supply and which is the main fish
            if(bodyA.getUserData() instanceof MainFish && bodyB.getUserData() instanceof BulletSupplyManager.BulletSupplyEntity){
                MainFish mainFish = (MainFish)bodyA.getUserData();
                BulletSupplyManager.BulletSupplyEntity bulletSupplyEntity = (BulletSupplyManager.BulletSupplyEntity)bodyB.getUserData();
                //if bullet supply entity is alive then deactivate
                if(bulletSupplyEntity.isActive){
                    bulletSupplyEntity.deactivate();
                    GameInfo.TOTAL_BULLETS+=10;
                    scoreDisplay.setText("Bullets: " + GameInfo.TOTAL_BULLETS);
                }
            }else
            if(bodyB.getUserData() instanceof MainFish && bodyA.getUserData() instanceof BubblesManager.Bubble){
                MainFish mainFish = (MainFish)bodyB.getUserData();
                BulletSupplyManager.BulletSupplyEntity bulletSupplyEntity = (BulletSupplyManager.BulletSupplyEntity)bodyA.getUserData();
                //if bullet supply entity is alive then deactivate
                if(bulletSupplyEntity.isActive){
                    bulletSupplyEntity.deactivate();
                    GameInfo.TOTAL_BULLETS+=10;
                    scoreDisplay.setText("Bullets: " + GameInfo.TOTAL_BULLETS);
                }
            }

        }

    }
    /**
     * check whether a bullet supply has hit the ending of the screen.
     * and act accordingly.
     */
    public static synchronized void checkBulletSupplyEntityWallEndingCollision(Contact contact){

        //first get bodies' user data and the fixture
        Fixture fixtureA = contact.getFixtureA(),
                fixtureB = contact.getFixtureB();

        //check whether the bodies's user data are not null
        if(fixtureA.getUserData() != null && fixtureB.getBody().getUserData() != null){

            //check which is the bullet supply and which is the main fish
            if(fixtureA.getUserData() instanceof Sensors
                    && fixtureA.getUserData() == Sensors.MAP_BOUNDERIES
                    && fixtureB.getBody().getUserData() instanceof BulletSupplyManager.BulletSupplyEntity){
                BulletSupplyManager.BulletSupplyEntity bulletSupplyEntity = (BulletSupplyManager.BulletSupplyEntity)fixtureB.getBody().getUserData();
                //if bullet supply entity is alive then deactivate
                if(bulletSupplyEntity.isActive){
                    bulletSupplyEntity.deactivate();
                }
            }else
            if(fixtureB.getUserData() instanceof Sensors
                    && fixtureB.getUserData() == Sensors.MAP_BOUNDERIES
                    && fixtureA.getBody().getUserData() instanceof BubblesManager.Bubble){
                BulletSupplyManager.BulletSupplyEntity bulletSupplyEntity = (BulletSupplyManager.BulletSupplyEntity)fixtureA.getBody().getUserData();
                //if bullet supply entity is alive then deactivate
                if(bulletSupplyEntity.isActive){
                    bulletSupplyEntity.deactivate();
                }
            }

        }

    }
    //end of checkers

}
