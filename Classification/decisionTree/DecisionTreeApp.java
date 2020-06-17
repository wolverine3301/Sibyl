package decisionTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class DecisionTreeApp {

    /* ------------------------------- */
    /*                                 */
    /*              FIELDS             */
    /*                                 */
    /* ------------------------------- */

    static BufferedReader keyboardInput = new
                           BufferedReader(new InputStreamReader(System.in));
    static DecisionTree newTree;

    /* --------------------------------- */
    /*                                   */
    /*               METHODS             */
    /*                                   */
    /* --------------------------------- */

    /* MAIN */

    public static void main(String[] args) throws IOException {

        // Create instance of class DecisionTree

        newTree = new DecisionTree();

        // Generate tree

        generateTree();

        // Output tree

        System.out.println("\nOUTPUT DECISION TREE");
        System.out.println("====================");
        newTree.outputBinTree();

        // Query tree

        queryTree();
        }

    /* GENERATE TREE */

    static void generateTree() {
        System.out.println("\nGENERATE DECISION TREE");
        System.out.println("======================");
        newTree.createRoot(1,"Does animal eat meat?");
        newTree.addYesNode(1,2,"Does animal have stripes?");
        newTree.addNoNode(1,3,"Does animal have stripes?");
        newTree.addYesNode(2,4,"Animal is a Tiger");
        newTree.addNoNode(2,5,"Animal is a Leopard");
        newTree.addYesNode(3,6,"Animal is a Zebra");
        newTree.addNoNode(3,7,"Animal is a Horse");
        }

    /* QUERY TREE */
	
    static void queryTree() throws IOException {
        System.out.println("\nQUERY DECISION TREE");
        System.out.println("===================");
        newTree.queryBinTree();

        // Option to exit

        optionToExit();
        }

    /* OPTION TO EXIT PROGRAM */

    static void optionToExit() throws IOException {
        System.out.println("Exit? (enter \"Yes\" or \"No\")");
        String answer = keyboardInput.readLine();
        if (answer.equals("Yes")) return;
        else {
            if (answer.equals("No")) queryTree();
            else {
                System.out.println("ERROR: Must answer \"Yes\" or \"No\"");
                optionToExit();
                }
            }
        }
    }