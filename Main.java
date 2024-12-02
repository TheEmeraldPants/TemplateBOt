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

        while (active)
        {
                switch (phase) {
                        case "start": {
                                System.out.println(randMessage(greetings));
                                System.out.println("Would you like to order something?");
                                boolean loop = true;
                                while (loop == true) {
                                        String userResp = in.nextLine(); //in.nextLine() uses the scanner object to get the user's responnse as a String
                                        switch (userResp.toLowerCase()) {
                                                case "y":
                                                        loop = false;
                                                        System.out.println("Great! What would you like to order?");
                                                        phase = "order";
                                                        break;
                                                case "yes":
                                                        loop = false;
                                                        System.out.println("Great! What would you like to order?");
                                                        phase = "order";
                                                        break;
                                                case "sure":
                                                        loop = false;
                                                        System.out.println("Great! What would you like to order?");
                                                        phase = "order";
                                                        break;
                                                case "no":
                                                        loop = false;
                                                        System.out.println(ANSI_RED + "you will regret that decision." + ANSI_RESET);
                                                        phase = "end";
                                                        break;
                                                case "n":
                                                        loop = false;
                                                        System.out.println(ANSI_RED + "how dare you" + ANSI_RESET);
                                                        phase = "end";
                                                        break;
                                                case "nah":
                                                        loop = false;
                                                        System.out.println(ANSI_RED + "wrong answer" + ANSI_RESET);
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
                        case "order": {
                                ArrayList<Food> orderIngredients = new ArrayList<>();
                                Scanner scanr1 = new Scanner (System.in);
                                Scanner scanr2 = new Scanner (System.in);
                                System.out.println("Your options are: ");
                                for(int i = 0; i < orderTypes.length; i++){
                                        System.out.println(orderTypes[i]);
                                }
                                String orderType = scanr1.nextLine();
                                Boolean isIn = false;
                                for(int i = 0; i < orderTypes.length; i++){
                                        if(orderType.equals(orderTypes[i])){
                                                isIn = true;
                                                break;
                                        }
                                }

                                if(!isIn){
                                        System.out.println("Thats not an order type, try entering a valid order type");
                                } else if(isIn){
                                        boolean ingredientCheck = true;
                                        while(ingredientCheck){
                                                System.out.println("These are your ingredient choices");
                                                System.out.println("Type 'done' when you have finished selecting ingredients.");
                                                for(Food ingredient : ingredients){
                                                        System.out.println(ingredient.getName());
                                                }
                                                
                                                System.out.println("What ingredients would you like?");
                                                String userIngredient = scanr2.nextLine();
                                                if(userIngredient.equals("done")){
                                                        ingredientCheck = false;
                                                        break;
                                                }

                                                Boolean isInMenu = false;
                                                for(Food ingredient : ingredients){
                                                        if(userIngredient.equals(ingredient.getName())){
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
                                                switch (resp.toLowerCase()) {
                                                        case "n": phase = "end"; break;
                                                        case "nah": phase = "end"; break;
                                                        case "no": phase = "end"; break;
                                                        case "exit": phase = "end"; break;
                                                        case "stop": phase = "end"; break;
                                                        case "yes": System.out.println("Great!"); break;
                                                        case "y": System.out.println("Affirmative."); break;
                                                        case "yeah": System.out.println("cool"); break;
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

       //Create other methods that might be helpful down here. 
       //For example a method called checkWord where the user traverses through an array to check if a word matches.
       
       //One method you might need is getRandomResponse()

       // It'll generate a random response when the chatbot doesn't understand what to say

       //Here is an example of a method you might use.
         public static String getResponse(String statement)
         {
                 String response = "";
                 if (statement.indexOf("no") >= 0)
                 {
                         response = "Why so negative?";
                 }
                 else if (statement.indexOf("mother") >= 0
                                 || statement.indexOf("father") >= 0
                                 || statement.indexOf("sister") >= 0
                                 || statement.indexOf("brother") >= 0)
                 {
                         response = "Tell me more about your family.";
                 }
                 else
                 {
                         //respoonse = getRandomResponse() <--- yo uwill need to create this methodd.
                 }
                 return response;
         }

}