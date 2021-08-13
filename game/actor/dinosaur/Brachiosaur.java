package game.actor.dinosaur;

import edu.monash.fit2099.engine.*;
import game.*;
import game.actor.corpse.BrachiosaurCorpse;
import game.actor.baby.BabyBrachiosaur;
import game.vendingmachine.egg.BrachiosaurEgg;
import game.vegetation.Tree;
import game.vendingmachine.Fruit;

import java.util.List;

/**
 * A herbivorous dinosaur with a long neck but poor digestion
 */
public class Brachiosaur extends BabyBrachiosaur {
    private Behaviour behaviour;
    private final String sex;
    private final Chance sexChance = new Chance(50);
    private int pregnancyTime = 0;

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'b' and have 160 hit points.
     *
     * @param name the name of the Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'B');
        if(sexChance.isChance()){
            this.sex = "F";
        }else{
            this.sex = "M";
        }
        behaviour = new WanderBehaviour();
    }

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'b' and have 160 hit points.
     *
     * @param name the name of the Brachiosaur
     */
    public Brachiosaur(String name, String sex) {
        super(name, 'B');
        this.sex = sex;
        behaviour = new WanderBehaviour();
    }

    /**
     * Accessor
     * @return sex of the dinosaur
     */
    public String getSex() {
        return sex;
    }

    /**
     * Checks if partner is of the opposite sex so that they can breed
     * @param partner propective mate
     * @return whether they can breed
     */
    private boolean isCompatible(Brachiosaur partner){
        return this.getSex().equals("F") && partner.getSex().equals("M") && this.hitPoints>=70 && partner.hitPoints>=70;
    }

    /**
     * Allows to Brachiosaur to breed
     * @param partner male Brachiosaur to breed with
     * @param map Gamemap of the world
     * @return An egg the pair laid
     */
    private BrachiosaurEgg breed(Brachiosaur partner, GameMap map){
        pregnancyTime++;

        if(this.getSex().equals("F") && isCompatible(partner) && pregnancyTime==10){
            return new BrachiosaurEgg("Brachiosaur Egg", map.locationOf(this));
        } else
            return null;
    }

    /**
     * Kills the dinosaur & replaces it with a corpse
     * @param map The Game map the dinosaurs are located on
     */

    private void death(GameMap map){
        Location location = map.locationOf(this);
        map.removeActor(this);
        BrachiosaurCorpse corpse = new BrachiosaurCorpse("Brachiosaur Corpse");
        map.addActor(corpse, location);
    }

    /**
     * represents the dinosaur eating
     * @param map
     */

    private void eat(GameMap map){
       try {
            eatTreeFruit(map);
        } catch (Exception ignored){

        }
    }

    /**
     * Eats fruit from any nearby trees
     * @param map The Game map the dinosaurs are located on
     */

    private void eatTreeFruit(GameMap map){
        List items = null;
        Location brachiosaurLocation = map.locationOf(this);
        int x =  brachiosaurLocation.x()-1;
        int y = brachiosaurLocation.y()-1;
        for(int i=x; i<=x+2; i++){
            for(int j=y; j<=2; j++){
                if(i==x+1 && j==y+1){
                    i++;
                    j++;
                }else{
                    Location treeLocation = new Location(map, i, j);
                    items.add(treeLocation.getItems());

                    Tree treeClass = new Tree();
                    for (Object each: items) {
                        if(each.getClass()==treeClass.getClass()){
                            Tree tree = (Tree) each;
                            if((tree).getFruit()!=null){
                                Fruit fruit = (tree).getFruit();
                                heal(5);
                                tree.removeFruit();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        this.hurt(1);
        if(this.hitPoints<=140) {
            display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting  hungry!");
            this.eat(map);
        }
        if(this.hitPoints<=0){
            this.fallUnconscious();
        }
        if(this.unconsciousness==15){
            this.death(map);
        }
        if(this.hitPoints>=10){
            this.unconsciousness=0;
            this.isUnconscious = true;
        }

        this.dehydrate(1);
        if(this.waterPoints<=40){
            display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting thirsty!");
            this.moveToLake(map);
            this.hydrate(80);
        }
        return new DoNothingAction();
    }
}
