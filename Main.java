import java.util.Scanner;
import java.util.ArrayList;

public class Main
{

    //CREATE INSTANCE VARIABLES HERE. Ensure they are static.
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";     
    //May want to create an array of goodbye answers.
    static String[] goodBye = {"Thank you for shopping with Chipotle!", "Have a good day!", "ChipotleBot out", "Come back soon!"};
    static String[] greetings = {"Hello! My name is ChipotleBot.", "Hi! I'm ChipotleBot.", "Hey, ChipotleBot here.", "wsg fam"};
    static String[] affirmative = {"yes", "yeah", "yea", "sure", "ok", "okay", "affirmative", "I would"};
    static String[] negative = {"no", "nah", "naw", "not", "negative", "nay", "never", "nope"};
   
   private static final String[] orderTypes = {"burrito", "bowl", "taco"};
    //creating ingredients
  private static final Food[] ingredients = {
        new Food("white rice", 200, 2.50, true),
        new Food("brown rice", 200, 2.50, true),
        new Food("tortilla", 125, 1.25, true),
        new Food("black beans", 250, 2.50, true),
        new Food("pinto beans", 250, 2.50, true),
        new Food("chicken", 300, 3.00, false),
        new Food("carne asada", 350, 3.50, false),
        new Food("steak", 400, 4.00, false),
        new Food("lettuce", 100, 1.50, true),
        new Food("corn", 125, 1.25, true)
    };


       public static void main(String[] args)
       {
        Scanner in = new Scanner (System.in);//Creates scanner object.
        Scanner in2 = new Scanner (System.in);//Creates a second scanner object.
        String phase = "start";
        boolean active = true;
        int calories = 0;
        double price = 0.0;
        boolean isVegetarian = false;
        while (active)
        {
                switch (phase) {
                        case "start": {
                                System.out.println(randMessage(greetings));
                                System.out.println("Note that you can type 'exit' at any time to close the program.");
                                System.out.println("Would you like to order something?");
                                boolean loop = true;
                                while (loop == true) {
                                        String userResp = in.nextLine(); //in.nextLine() uses the scanner object to get the user's responnse as a String
                                        switch (interpretResponse(userResp.toLowerCase(), "yesOrNo")) {
                                                case "y":
                                                        loop = false;
                                                        System.out.println("Great!");
                                                        phase = "veg";
                                                        break;
                                                case "n":
                                                        loop = false;
                                                        System.out.println(ANSI_RED + "how dare you" + ANSI_RESET);
                                                        phase = "end";
                                                        break;
                                                case "exit":
                                                        loop = false;
                                                        phase = "end";
                                                        break;
                                                default: 
                                                        System.out.println("I'm sorry, I think there's been a miscommunication. Would you like to order something?");
                                                        break;
                                        }
                                }
                        break;
                        }
                        case "veg": {
                                System.out.println("Are you a vegetarian?");
                                String resp = in.nextLine();
                                switch(interpretResponse(resp, "yesOrNo")) {
                                        case "y": {
                                                System.out.println("Don't worry, we have vegetarian options!");
                                                isVegetarian = true;
                                                phase = "order";
                                                break;
                                        }
                                        case "n": {
                                                System.out.println("Gotcha.");
                                                isVegetarian = false;
                                                phase = "order";
                                                break;
                                        }
                                        case "exit": {
                                                phase = "end";
                                                break;
                                        }
                                        default: {
                                                System.out.println("I'm sorry, but I don't understand.");
                                        } 
                                }
                                break;
                        }
                        
                        case "order": {
                                ArrayList<Food> orderIngredients = new ArrayList<>();
                                Scanner scanr1 = new Scanner (System.in);
                                Scanner scanr2 = new Scanner (System.in);
                                System.out.println("Your options are: ");
                                for(int i = 0; i < orderTypes.length; i++){
                                        System.out.println(orderTypes[i]);
                                }
                                System.out.println("What would you like to order?");
                                String orderType = scanr1.nextLine();
                                if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("ERROR")) {
                                        System.out.println("I'm sorry, but that's not on the menu. Please order something on the menu.");
                                }
                                else if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("ERROR: MULTIPLE")) {
                                        System.out.println("Order one item at a time, please.");
                                }
                                else if (interpretResponse(orderType.toLowerCase(), "menuItems").equals("exit")) {
                                        phase = "end";
                                        break;
                                } 
                                else {
                                        boolean ingredientCheck = true;
                                        while(ingredientCheck){
                                                System.out.println("These are your ingredient choices");
                                                System.out.println("Type 'done' when you have finished selecting ingredients.");
                                                for(Food ingredient : ingredients){
                                                        if (isVegetarian) {
                                                                if (ingredient.getIsVeg() == true) System.out.println(ingredient.getName());
                                                        }
                                                        else System.out.println(ingredient.getName());
                                                }
                                                
                                                System.out.println("What ingredients would you like?");
                                                String userIngredient = scanr2.nextLine();
                                                if(userIngredient.toLowerCase().indexOf("done") != - 1){
                                                        ingredientCheck = false;
                                                        break;
                                                }
                                                
                                                if(userIngredient.toLowerCase().indexOf("exit") != -1) {
                                                        phase = "end";
                                                        break;
                                                }

                                                Boolean isInMenu = false;
                                                for(Food ingredient : ingredients){
                                                        if(userIngredient.toLowerCase().indexOf(ingredient.getName()) != -1){
                                                                isInMenu = true;
                                                                orderIngredients.add(ingredient);
                                                                break;
                                                        }
                                                }
                                                 
                                                if(!isInMenu){
                                                        System.out.println("That is not a valid ingredient");
                                                }
                                        }

                                        
                                        for(int i = 0; i < orderIngredients.size(); i++){
                                                calories += orderIngredients.get(i).getCalories();
                                        }

                                        for(int i = 0; i < orderIngredients.size(); i++){
                                                price += orderIngredients.get(i).getPrice();
                                        }

                                        Scanner scanr3 = new Scanner (System.in);
                                        boolean loop = true;
                                        if (phase == "end") loop = false;
                                        while (loop) {
                                                System.out.println("Would you like to know the 'price', the 'calories', 'both', or 'neither' of your order?");
                                                String finalResponse = scanr3.nextLine().toLowerCase();
                                                loop = false;
                                                switch (finalResponse) {
                                                case "price":
                                                        System.out.println("Total Price: $" + price);
                                                        break;
                                                case "calories":
                                                        System.out.println("Total Calories: " + calories);
                                                        break;
                                                case "both":
                                                        System.out.println("Total Price: $" + price);
                                                        System.out.println("Total Calories: " + calories);
                                                        break;
                                                case "neither":
                                                        phase = "end";
                                                        scanr1.close();
                                                        scanr2.close();
                                                        scanr3.close();
                                                        break;
                                                case "exit":
                                                        phase = "end";
                                                        scanr1.close();
                                                        scanr2.close();
                                                        scanr3.close();
                                                        break;
                                                default:
                                                        System.out.println("Invalid option. Please type 'price', 'calories', 'both', or 'neither'."); loop = true; break;
                                                }
                                        }
                                        if (phase != "end") {
                                                boolean loopEnd = true;
                                                while (loopEnd) {
                                                System.out.println("Would you like to order something else?");
                                                String resp = scanr1.nextLine();
                                                loopEnd = false;
                                                switch (interpretResponse(resp.toLowerCase(), "yesOrNo")) {
                                                        case "n": phase = "end"; break;
                                                        case "y": System.out.println("Got it."); break;
                                                        default: System.out.println("how did you get this far and"); System.out.println("still fail to enter a valid response? "); loopEnd = true; break;
                                                }
                                                }
                                        }



                                        
                                }
                        break;
                        }
                        case "end": {
                                System.out.println(randMessage(goodBye));
                                active = false;
                                in.close();
                                in2.close();
                        break;
                        }
                }
        } 
        

        //You will need to make sure your chatbot continues looping until it sees certain keywords from the user such as "bye","goodbye"...
        // do you need a while loop or a for loop?
          
        //Consider: How do I check what the user says and compare it to my keywords? What methods do I have?
             
       }

        public static String randMessage(String[] msg) {
                int max = msg.length;
                int i = (int) (Math.random() * max);
                return msg[i];
        }

        public static String interpretResponse(String resp, String type) {
                String item = "";
                boolean hasY = false;
                int yCount = 0;
                boolean hasN = false;
                int nCount = 0;
                int orderCount = 0;
                if (resp.equals("exit")) return "exit";
                else switch(type) {
                        case "yesOrNo": {
                                for (String y : affirmative) { //does it contain an affirmative?
                                        int index = resp.indexOf(y.toLowerCase());
                                        if (index != -1){ hasY = true; yCount++; }
                                        else if (resp.equals("y")) hasY = true;
                                }
                                for (String n : negative) { //does it contain a negative?
                                        int index = resp.indexOf(n.toLowerCase());
                                        if (index != -1){ hasN = true; nCount++; }
                                        else if (resp.equals("n")) hasN = true;
                                }
                                if (hasY == hasN) {
                                        if (yCount > nCount) return "y";
                                        else if (nCount > yCount) return "n";
                                        if(resp.toLowerCase().equals("I would not".toLowerCase())) return "n";
                                        else return "ERROR";
                                }
                                else {
                                        if (hasY) return "y";
                                        else return "n";
                                }
                        }
                        case "menuItems": {
                                for (String order : orderTypes) {
                                        int index = resp.indexOf(order);
                                        if (index != -1) orderCount++; item = order;
                                }
                                if (orderCount > 1) item = "ERROR: MULTIPLE";
                                else if (orderCount == 0) item = "ERROR";
                                return item;
                        }
                }
               

                return "ERROR";
        }

      

}