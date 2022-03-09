package Zadani_1;

public class Zadani_1 {

    public static void main(String[] args) {
        AbstractBird eagle = new Eagle(0,0,10,"eagle");
        AbstractBird pigeon = new Pigeon(0,0,0,"pigeon");
        AbstractMammal rat = new Rat (0,0,10,"rat");
        AbstractMammal sloth = new Sloth(0,0,10,"sloth");
        AbstractFish baraccuda = new Barracuda(0,0,3,"baraccuda");
        AbstractFish carp = new Carp(0,0,10,"carp");

        AbstractAnimal[] animals = {eagle,pigeon,rat,sloth,baraccuda,carp};
        IEggPlanty[] eggs = {eagle,pigeon,baraccuda,carp};
        IVoicy[] loud = {eagle, pigeon, rat,sloth};
        AbstractBird[] birds = {eagle,pigeon};

        for (int i = 0; i < eggs.length; i++) {
            eggs[i].layEggs();
        }

        for (int i = 0; i < animals.length; i++) {
            animals[i].eat(10);
            animals[i].moveTo(10+i,20+i);
        }

        for (int i = 0; i < loud.length; i++) {
            loud[i].cry();
        }

        for (int i = 0; i < birds.length; i++) {
            birds[i].cry();
        }
    }

}
