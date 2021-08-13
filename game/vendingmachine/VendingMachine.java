package game.vendingmachine;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A vending machine that sells Eggs, mealkits & weapons!
 */
public class VendingMachine {

    private final VendItem Item;
    /**
     * enum class for products which are sold from vending machine
     */
    enum VendItem{
        /**
         * items can be bought
         */
        fruit,
        vegMealKit,
        carnMealKit,
        stegoEgg,
        brachioEgg,
        allosaurEgg,
        pterodactylEgg,
        laserGun,
        waterBottle,
        /**
         * methods to get eco point
         */
        prodcFruit,
        harvestFruit,
        fedFruit,
        stegohatches,
        brachiohatches,
        allohatches,
        pterodactylhatches

    }



    /**
     * Item which can be get from vending machine
     */
    public VendingMachine(VendItem Item) {
        this.Item = Item;

    }

    int ecoCost;
    /**
     * @param  Item  that player can buy
     * @return the cost of the item
     */
    public int getEcoCost(VendItem Item) {

        System.out.println(
                "fruit(-30 points)" +
                "vegMealKit(-100 points)" +
                "carnMealKit(-500 points)" +
                "stegoEgg(-200 points)" +
                "brachioEgg(-500 points)" +
                "allosaurEgg(-1000 points)" +
                "pterodactylEgg(-300 points)" +
                "laserGun(-500 points)" +
                "waterBottle(-200 points)" +
                "Please type the name of item you want :");//
        Scanner sc= new Scanner(System.in);                //   ask a option which player want to buy
        String str= sc.nextLine();                         //
/**
 * different result after player buy different items
 */
        switch (Item){
            case fruit:
                System.out.println("You spend 30 points on a fruit");
                return  -30;
            case vegMealKit:
                System.out.println("You spend 100 points on a vegMealKit");
                return  -100;
            case carnMealKit:
                System.out.println("You spend 500 points on a carnMealKit");
                return  -500;
            case stegoEgg:
                System.out.println("You spend 200 points on a Stegosaur Egg");
                return  -200;
            case brachioEgg:
                System.out.println("You spend 500 points on a Brachiosaur Egg");
                return  -500;
            case allosaurEgg:
                System.out.println("You spend 1000 points on a Allosaur Egg");
                return  -1000;
            case pterodactylEgg:
                System.out.println("You spend 300 points on a Pterodactyl Egg");
                return  -300;
            case laserGun:
                System.out.println("You spend 500 points on a laserGun");
                return  -500;
            case waterBottle:
                System.out.println("You will spend 200 points on a waterBottle");
                return  -200;
        }
        return 0;

    }

    int ecoGet;
    /**
     * methods that player can get eco points,and the different value of the methods
     */
    public int getEcoGet() {
        switch (Item){
            case prodcFruit:
            return 1;
            case harvestFruit:
                return 10;
            case fedFruit:
                return 10;
            case stegohatches:
                return 100;
            case brachiohatches:
                return 1000;
            case allohatches:
                return 1000;
            case pterodactylhatches:
                return 1000;
        }
        return 0;
    }


}
