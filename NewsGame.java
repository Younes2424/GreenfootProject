import javax.swing.JOptionPane;

public class NewsGame {
    private static int [] randIndex;
    private static String [] randNewsItem;
    private static boolean [] solution;
    private static int [] intAnswer;
    private static boolean [] answer;
    private static int userCount, realNewsCount, itemCount, gameChoice, gameMode, userMark, spaceRequired;
    final private static int MIN = 0, MAX = 9, defVar = -101, maxItemSize = 66, spaceSize = 8;
    final private static String [] newsItem = {"\"John F. Kennedy Runs for President in 2020\"",
                                               "\"North Dakota hit by 6-Magnitude Quake\"",
                                               "\"Conor McGregor Defeats Floyd Mayweather Jr.\"",
                                               "\"Cristiano Ronaldo Leaves Juventus for Real Madrid\"",
                                               "\"Tottenham Hotspur Won 2019 Champions League Finale\"",
                                               "\"Priceless Relics Saved from Flames as Notre Dame Ravaged by Fire\"",
                                               "\"Novak Djokovic Beats Roger Federer in Five Sets to Win Wimbledon\"",
                                               "\"Scott Morrison Pledges Addition $3 Million in Aid to Lebanon\"",
                                               "\"Mike Tyson to Return to Boxing Ring to Fight Roy Jones Jr.\"",
                                               "\"UK Medics Protest for Pay Rise After Pandemic\""};
    public static void main(final String[] args) {
        selectGame();
        selectMode();
        setMode();     
        generateRndItems();  
        solution = new boolean [itemCount];
        findSolutions(randIndex, solution);
        realNewsCount = countTrue(solution);
        if (gameChoice == 0)
            playFakeCountGame();
        else
            playNewsQuizGame();
    }

    /**
     * isOutOfRange - returns true if a variable is out of range.
     */
    public static boolean isOutOfRange(int variable, int min, int max) {
        if (variable > max || variable < min)
            return true;
        else
            return false;
    }

    /**
     * exitGame - exits game upon integer arguments passed.
     */
    public static void exitGame(int exitVariable, int exitCondition) {
        if (exitVariable == exitCondition)
            System.exit(1);
    }

    /**
     * exitGame - exits game upon string argument passed.
     */
    public static void exitGame(String exitVariable, String exitCondition) {
        if (exitVariable == exitCondition)
            System.exit(1);
    }

    /**
     * selectGame - allows user to select a game.
     */
    public static void selectGame() {
        Object [] options = {"Fake Count Game", "News Quiz Game", "I'm not in the mood!"};
        gameChoice = JOptionPane.showOptionDialog(null, 
                                                "Which game would you like to play?", 
                                                "Game Choice",
                                                JOptionPane.YES_NO_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[2]);
        exitGame(gameChoice, 2);
    }

    /**
     * selectMode - allows user to select the mode of the game.
     */
    public static void selectMode() {
        Object [] options = {"Custom", "Default", "Cancel"};
        gameMode = JOptionPane.showOptionDialog(null, 
                                                "Click Custom to set the number of news items.\nClick Default for three news items.",
                                                "Item Setup",
                                                JOptionPane.YES_NO_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[2]);
        exitGame(gameMode, 2);
    }

    /**
     * setItemNum - allows user to set the number of news items.
     */
    public static void setItemNum() {
        do {
            itemCount = inputInteger("How many news items do you want? (Range: 1-10)", defVar, (MIN+1), (MAX+1));
        } while(isOutOfRange(itemCount, (MIN+1), (MAX+1)));
    }

    /**
     * setMode - sets the mode that is previously selected by the user.
     */
    public static void setMode() {
        if (gameMode == 0)
            setItemNum();
        else if (gameMode == 1)
            itemCount = 3;
    }

    /**
     * inputInteger - prompts user for an integer within a range using JOptionPane Input Dialog.
     */
    public static int inputInteger(String statement, int defvar, int min, int max) {
        int value;
        try {
            String str = JOptionPane.showInputDialog(statement);
            exitGame(str, null);
            value = Integer.parseInt(str);
            if (isOutOfRange(value, min, max))
                promptError();
        } catch (Exception incompatibleException) {
            promptError();
            value = defvar; // A default value to reexecute the loop after an incompatible input.
        }
        return value;
    }

    /**
     * promptError - when called, prompts user an error message.
     */
    public static void promptError() {
        int randInt = getRandInt(1, 4);
        if (randInt == 1)
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        else if (randInt == 2)
            JOptionPane.showMessageDialog(null, "Try Again!");
        else if (randInt == 3)
            JOptionPane.showMessageDialog(null, "Read the instructions carefully!");
        else
            JOptionPane.showMessageDialog(null, "Input is either out of range or not compatible!");
    }

    /**
     * congratulate - when called, congratulates the user.
     */
    public static void congratulate() {
        int randInt = getRandInt(1, 4);
        if (randInt == 1)
            JOptionPane.showMessageDialog(null, "Well done!");
        else if (randInt == 2)
            JOptionPane.showMessageDialog(null, "Correct!");
        else if (randInt == 3)
            JOptionPane.showMessageDialog(null, "You have won!");
        else
            JOptionPane.showMessageDialog(null, "You're a winner!");
    }

    /**
     * getRandInt - returns random integers within a range.
     */
    public static int getRandInt(int min, int max) {
        int randomInt = (int)(Math.random() * (max - min + 1) + min);
        return randomInt;
    }

    /**
     * getRandInt - returns unique set of random integers.
     */
    public static int [] getRandInt(int min, int max, int arraySize) {
        int [] randNum = new int [arraySize];
        for (int i=0; i<arraySize; i++) {
            randNum[i] = (int)(Math.random() * (max - min + 1) + min);
            for (int k=0; k<i; k++) {
                if (randNum[i] == randNum[k])
                    i--;
            }
        }
        return randNum;
    }

    /**
     * shuffle - shuffles the elements of a string array.
     */
    public static void shuffle(int [] randIndex, String [] mainArray, String [] mixedArray) {
        for (int m=0; m<randIndex.length; m++) {
            mixedArray[m] = mainArray[randIndex[m]];
        }
    }

    /**
     * generateRndItems - generates random news items.
     */
    public static void generateRndItems() {
        randIndex = getRandInt(MIN, MAX, itemCount);
        randNewsItem = new String [itemCount];
        shuffle(randIndex, newsItem, randNewsItem);
    }

    /**
     * printArrayElements - prints string array elements.
     */
    public static void printArrayElements(String [] array) {
        System.out.println();
        for (int n=0; n<array.length; n++)
            System.out.println(array[n]);
        System.out.println();
    }

    /**
     * inputStatus - stores user's input of the status of a news item using input dialog boxes in an array.
     */
    public static void inputStatus(String [] statement, int [] intAnswer) {

        Object [] options = {"False", "True"};
        for (int d=0; d<statement.length; d++) {
            intAnswer[d] = JOptionPane.showOptionDialog(null, 
                                                        statement[d], 
                                                        "Item Status",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE,
                                                        null,
                                                        options,
                                                        options[0]);
        }
    }

    /**
     * findSolutions - imports the answers into a boolean array
     * that contains the correct status of each news items.
     */
    public static void findSolutions(int [] randIndex, boolean [] solution) {
        for (int i=0; i<itemCount; i++) {
            if (randIndex[i]<5)
                solution[i] = false;
            else if (randIndex[i]>=5)
                solution[i] = true;
        }
    }

    /**
     * countTrue - counts true elements inside an array.
     */
    public static int countTrue(boolean [] array) {
        int trueCount = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i] == true)
                trueCount++;
        }
        return trueCount;
    }

    /**
     * calcPercentCorrect - calculates the percentage of the correct answers out of requested items.
     */
    public static void calcPercentCorrect() {
        userMark = 0;
        for (int item = 0; item<itemCount; item++) {
            if (solution[item] == answer[item])
                userMark++;
        }
        int percentageCorrect = ((userMark*100)/itemCount);
        System.out.println("You are " + percentageCorrect + "% correct.");
    }

    /**
     * convertIntToBool - converts integer values to boolean values and returns them in a new array.
     */
    public static boolean[] convertIntToBool(int [] intArray) {
        boolean [] boolArray = new boolean[intArray.length];
        for (int i=0; i<intArray.length; i++) {
            if (intArray[i] == 0)
                boolArray[i] = false;
            else if (intArray[i] == 1)
                boolArray[i] = true;
        }
        return boolArray;
    }

    /**
     * countItems - asks the user to count the news items. 
     * The validation is for only the data type as it counts
     * towards the answer of the game.
     */
    public static void countItems() {
        do {
            userCount = inputInteger("Enter the number of correct news:", defVar, -100, 100);
        } while (isOutOfRange(userCount, -100, 100));
    }

    /**
     * printStatus - prints the status of each items.
     */
    public static void printStatus() {
        System.out.println();
        for (int k=0; k<itemCount; k++) {
            System.out.print(randNewsItem[k] + ":");
            spaceRequired = (maxItemSize+spaceSize)-randNewsItem[k].length();
            for (int space=0; space<spaceRequired; space++) {
                System.out.print(" ");
            }
            System.out.println(solution[k]);
        }
    }

    /**
     * printQuizCard - prints the quiz card.
     */
    public static void printQuizCard() {
        System.out.println();
        System.out.println("Quiz Report Card:");
        System.out.println();
        System.out.println("                            News Item                                   True/False        User Choice");
        System.out.println();
        for (int m=0; m<itemCount; m++) {
            System.out.print(randNewsItem[m]);
            spaceRequired = (maxItemSize+spaceSize)-randNewsItem[m].length();
            if (randNewsItem[m].length() < maxItemSize) {
                for (int space=0; space<spaceRequired; space++) {
                    System.out.print(" ");
                }
                if (solution[m] == true)
                    System.out.println(solution[m] + "              " + answer[m]);
                else
                    System.out.println(solution[m] + "             " + answer[m]);
            }
            else {
                System.out.println("        " + solution[m] + "              " + answer[m]);
            }
        }
    }

    /**
     * displayResults - displays the results depending on the game played.
     */
    public static void displayResults() {
        if (gameChoice == 0) {
            if (userCount == realNewsCount) {
                congratulate();
            }
            else {
                printStatus();
            }
        }
        else if (gameChoice == 1) {
            calcPercentCorrect();
            printQuizCard();
        }
    }

    /**
     * playFakeCountGame - plays the Fake Count Game.
     */
    public static void playFakeCountGame() {
        printArrayElements(randNewsItem);
        countItems();
        displayResults();
    }

    /**
     * playNewsQuizGame - plays the News Quiz Game.
     */
    public static void playNewsQuizGame() {
        JOptionPane.showMessageDialog(null, "Guess your answer as True or False. Press OK to proceed.");
        intAnswer = new int [itemCount];
        inputStatus(randNewsItem, intAnswer);
        answer = convertIntToBool(intAnswer);
        displayResults();
    }
}
