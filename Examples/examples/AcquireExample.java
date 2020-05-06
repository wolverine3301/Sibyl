package examples;

import dataframe.Column;
import dataframe.DataFrame;

public class AcquireExample {
    public static void main(String[] args) {
        String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        Column target = df.getColumn_byName("species");
        System.out.println("Aquiring a dataframe only consisting of species " + target.getParticle(0).getValue());
        String[] arg = {target.getName(), "==", (String) target.getParticle(0).getValue()};
        DataFrame acquired = df.acquire(arg);
        acquired.printDataCounts(false);
    }
}
