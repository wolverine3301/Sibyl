package gainExamples;

import java.util.ArrayList;

import dataframe.DataFrame;
import info_gain.Gain;
import info_gain.GainInformation;
import info_gain.GainRatio;
import info_gain.InformationGain;

public class InfoGain_Example {
    public static void main(String[] args) {
        DataFrame df = DataFrame.read_csv("testfiles/test_data.txt");
        df.setColumnType(3, 'T');
        Gain gain = new InformationGain(df);
        df.printAllData(false);
        ArrayList<GainInformation> vals = gain.gain(0);
        for (GainInformation g : vals) {
            System.out.println(g.toString());
        }
    }
}
