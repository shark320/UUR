package Zaadni_2;

import java.util.*;

public class Main {

    /**
     * random generator
     */
    private static final Random rd = new Random(2);

    /**
     * creating list of birds
     * moves all birds
     */
    private static void createBirdList1() {
        List<AbstractBird> birds = new LinkedList<>();

        birds.add(new Eagle(0, 0, 10, "eagle_0"));
        birds.add(new Pigeon(10, 0, 10, "pigeon_0"));
        birds.add(new Eagle(20, 80, 10, "eagle_1"));
        birds.add(new Pigeon(30, 40, 10, "pigeon_1"));
        birds.add(new Eagle(50, 60, 10, "eagle_2"));
        birds.add(new Eagle(20, 10, 10, "eagle_3"));

        for (AbstractBird bird : birds) {
            bird.moveTo(10, 10);
        }
    }

    /**
     * create list of animals
     * output class name of the first element in the list
     * output energy of the first barracuda in the list
     * check if first and second elements are Birds
     */
    private static void createAnimalList() {
        List<AbstractAnimal> animals = new LinkedList<>();
        animals.add(new Eagle(0, 0, 10, "eagle_0"));
        animals.add(new Eagle(10, 0, 20, "eagle_1"));
        animals.add(new Barracuda(20, 0, 30, "barracuda_0"));
        animals.add(new Barracuda(30, 0, 40, "barracuda_1"));
        animals.add(new Sloth(40, 0, 50, "sloth_0"));
        animals.add(new Sloth(50, 0, 60, "sloth_1"));

        for (AbstractAnimal animal : animals) {
            System.out.println(animal);
        }

        System.out.println("First: " + animals.get(0).getClass().getSimpleName());
        Iterator<AbstractAnimal> it1 = animals.iterator();
        AbstractAnimal animal;
        System.out.println("First Barracuda energy: "
                + animals.stream().
                filter(e -> (e instanceof Barracuda)
                ).findFirst().
                get().
                getEnergy());

        if (animals.get(0) instanceof AbstractBird && animals.get(1) instanceof AbstractBird) {
            System.out.println("First and second elements are birds");
        } else {
            System.out.println("First and second elements are not birds");
        }
    }

    /**
     * create list of mammals, someone has named only with letters A B C
     * output elements, that have names only with A B C
     * output elements, that have names only with A B C and has more than 3 energy
     */
    private static void createMammalList() {
        List<AbstractMammal> mammals = new LinkedList<>();
        mammals.add(new Sloth(0, 0, rd.nextInt(20), "sloth_0"));
        mammals.add(new Sloth(10, 0, rd.nextInt(20), "ABC"));
        mammals.add(new Rat(20, 0, rd.nextInt(20), "BAC"));
        mammals.add(new Sloth(30, 0, rd.nextInt(20), "sloth_0"));
        mammals.add(new Rat(40, 0, rd.nextInt(20), "CAB"));
        mammals.add(new Sloth(50, 0, rd.nextInt(20), "AAA"));

        for (AbstractMammal mammal : mammals) {
            System.out.println(mammal);
        }

        System.out.println("__________________________________");

        mammals.stream().
                filter(e -> {
                    return !(e.
                            getName().
                            chars().
                            anyMatch(ch -> (ch != 'A' && ch != 'B' && ch != 'C')));
                }).
                forEach(System.out::println);

        System.out.println("__________________________________");

        mammals.stream().
                filter(e -> {
                    return !(e.
                            getName().
                            chars().
                            anyMatch(ch -> (ch != 'A' && ch != 'B' && ch != 'C'))) && e.getEnergy() > 3;
                }).
                forEach(System.out::println);
    }

    /**
     * create list of birds
     * output average energy of all Birds
     * output average energy of all Eagles
     */
    private static void createBirdList2() {
        List<AbstractBird> birds = new LinkedList<>();
        birds.add(new Eagle(0, 0, 11 + rd.nextInt(10), "eagle_0"));
        birds.add(new Pigeon(10, 0, 11 + rd.nextInt(10), "pigeon_0"));
        birds.add(new Eagle(20, 80, 11 + rd.nextInt(10), "eagle_1"));
        birds.add(new Pigeon(30, 40, rd.nextInt(10), "pigeon_1"));
        birds.add(new Eagle(50, 60, rd.nextInt(10), "eagle_2"));
        birds.add(new Eagle(20, 10, rd.nextInt(10), "eagle_3"));

        birds.stream().
                filter(e -> e.getEnergy() > 10).
                forEach(System.out::println);

        System.out.println("__________________________________");

        System.out.println(birds.
                stream().
                mapToInt(e -> e.getEnergy()).
                average().
                getAsDouble());

        System.out.println("__________________________________");

        System.out.println(birds.stream().
                filter(e -> e instanceof Eagle).
                mapToInt(e -> e.getEnergy()).
                average().
                getAsDouble());
    }

    /**
     * create list of names
     * output all names
     * output names sorted by alphabet
     * shuffle all names
     * output all names sorted by length ascending
     * output all names sorted by length descending
     */
    private static void createNames() {

        List<String> names = new LinkedList<>();

        names.add("Jan");
        names.add("Adam");
        names.add("Martin");
        names.add("Jakub");
        names.add("Doubravka");
        names.add("Valentin");

        System.out.println(names);
        names.sort(null);
        System.out.println(names);
        Collections.shuffle(names);
        System.out.println(names);
        names.sort(Comparator.comparingInt(String::length));
        System.out.println(names);
        names.sort(Comparator.comparingInt(String::length).reversed());
        System.out.println(names);
    }

    /**
     * create list of Fish
     * output all elements
     * create small copy
     * check if it works
     * enter new element on position 4
     * remove all barracudas and output
     */
    private static void createFish() {
        List<AbstractFish> fishes = new LinkedList<>();

        fishes.add(new Barracuda(20, 0, 30, "barracuda_0"));
        fishes.add(new Barracuda(30, 0, 30, "barracuda_1"));
        fishes.add(new Barracuda(40, 0, 30, "barracuda_2"));
        fishes.add(new Carp(50, 0, 30, "carp_0"));
        fishes.add(new Carp(60, 0, 30, "carp_1"));
        fishes.add(new Carp(70, 0, 30, "carp_2"));

        for (AbstractFish fish : fishes) {
            System.out.println(fish);
        }

        System.out.println("__________________________________");

        List<AbstractFish> fishes2 = new LinkedList<>(fishes);
        AbstractFish tmp = fishes.get(0);
        tmp.energy = 0;
        fishes.remove(0);
        fishes.set(0, tmp);

        for (AbstractFish fish : fishes2) {
            System.out.println(fish);
        }

        System.out.println("__________________________________");

        fishes.add(4, new Carp(0, 0, 10, "newCarp"));

        fishes.removeIf(e -> e instanceof Barracuda);

        for (AbstractFish fish : fishes) {
            System.out.println(fish);
        }
    }

    /**
     * main class - program entry point
     * call all methods
     *
     * @param args
     */
    public static void main(String[] args) {
        createBirdList1();
        System.out.println("__________________________________");
        createAnimalList();
        System.out.println("__________________________________");
        createMammalList();
        System.out.println("__________________________________");
        createBirdList2();
        System.out.println("__________________________________");
        createNames();
        System.out.println("__________________________________");
        createFish();
    }
}
