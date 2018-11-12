package ntnu.snorre.hangman2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Stack;

class Dictionary {
    private static final List<String> E_SPORTS = new ArrayList<>(Arrays.asList("Tennis", "Lacrosse", "Basketball", "Golf", "Boxing", "Football"));
    private static final List<String> E_FOODS = new ArrayList<>(Arrays.asList("Milk", "Bread", "Honey", "Egg and Bacon"));
    private static final List<String> E_MOVIES = new ArrayList<>(Arrays.asList("The Godfather", "Pulp Fiction", "Forrest Gump", "The Matrix"));
    private static final List<String> E_GAMES = new ArrayList<>(Arrays.asList("Minecraft", "Fallout", "Overwatch", "Read Dead", "Doom", "Battlefield"));

    private static final List<String> N_SPORTS = new ArrayList<>(Arrays.asList("Fotball", "Basketball", "Håndball", "Bandy", "Volleyball", "Svømming"));
    private static final List<String> N_FOODS = new ArrayList<>(Arrays.asList("Brød", "Melk", "Fisk", "Kylling", "Pizza", "Taco"));
    private static final List<String> N_MOVIES = new ArrayList<>(Arrays.asList("Flåklypa", "Max Manus", "Trolljegeren", "Lange flate ballær"));
    private static final List<String> N_GAMES = new ArrayList<>(Arrays.asList("Maxi Yatzy", "Femkamp", "Ludo", "Poker"));
    Stack<String> wordList = new Stack<>();
    private ArrayList<String> tempList = new ArrayList<>();

    Dictionary(String language, Set<String> categories) {
        if (language.equals("en")) initEnglishList(categories);
        else initNorwegianList(categories);
        setWordList();
    }

    private void initEnglishList(Set<String> categories) {
        for (String p : categories) {
            switch (p) {
                case "Sports":
                    tempList.addAll(E_SPORTS);
                    break;
                case "Foods":
                    tempList.addAll(E_FOODS);
                    break;
                case "Movies":
                    tempList.addAll(E_MOVIES);
                    break;
                case "Games":
                    tempList.addAll(E_GAMES);
                    break;
            }
        }
    }

    private void initNorwegianList(Set<String> categories) {
        for (String p : categories) {
            switch (p) {
                case "Sports":
                    tempList.addAll(N_SPORTS);
                    break;
                case "Foods":
                    tempList.addAll(N_FOODS);
                    break;
                case "Movies":
                    tempList.addAll(N_MOVIES);
                    break;
                case "Games":
                    tempList.addAll(N_GAMES);
                    break;
            }
        }
    }

    private void setWordList() {
        Collections.shuffle(tempList);
        for (String word : tempList) {
            wordList.push(word);
        }
    }
}
