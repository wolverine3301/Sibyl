package info_gain;

import java.util.HashMap;
import java.util.Map.Entry;

public class JointProbabilityState {
    public final HashMap<Pair<Integer,Integer>,Double> jointProbMap;
    public final HashMap<Integer,Double> firstProbMap;
    public final HashMap<Integer,Double> secondProbMap;

    public final int jointMaxVal;
    public final int firstMaxVal;
    public final int secondMaxVal;

    /**
     * Constructor for the JointProbabilityState class. Takes two data vectors and calculates
     * the joint and marginal probabilities, before storing them in HashMaps.
     *
     * @param  firstVector  Input vector. It is discretised to the floor of each value.
     * @param  secondVector  Input vector. It is discretised to the floor of each value.
     */
    public JointProbabilityState(double[] firstVector, double[] secondVector)
    {
      jointProbMap = new HashMap<Pair<Integer,Integer>,Double>();
      firstProbMap = new HashMap<Integer,Double>();
      secondProbMap = new HashMap<Integer,Double>();

      int firstVal, secondVal;
      Pair<Integer,Integer> jointVal;
      // tmpKey is used to reduce the amount of autoboxing, and is probably premature optimisation
      Integer tmpKey, count;

      int vectorLength = firstVector.length;
      double doubleLength = firstVector.length;

      //round input to integers
      int[] firstNormalisedVector = new int[vectorLength];
      int[] secondNormalisedVector = new int[vectorLength];
      firstMaxVal = ProbabilityState.normaliseArray(firstVector,firstNormalisedVector);
      secondMaxVal = ProbabilityState.normaliseArray(secondVector,secondNormalisedVector);
     
      jointMaxVal = firstMaxVal * secondMaxVal;

      HashMap<Pair<Integer,Integer>,Integer> jointCountMap = new HashMap<Pair<Integer,Integer>,Integer>();
      HashMap<Integer,Integer> firstCountMap = new HashMap<Integer,Integer>();
      HashMap<Integer,Integer> secondCountMap = new HashMap<Integer,Integer>();

      for (int i = 0; i < vectorLength; i++)
      {
          firstVal = firstNormalisedVector[i];
          secondVal = secondNormalisedVector[i];
          jointVal = new Pair<Integer,Integer>(firstVal,secondVal);

          count = jointCountMap.get(jointVal);
          if (count == null)
          {
              jointCountMap.put(jointVal,1);
          }
          else
          {
              jointCountMap.put(jointVal,count + 1);
          }

          tmpKey = firstVal;
          count = firstCountMap.remove(tmpKey);
          if (count == null)
          {
              firstCountMap.put(tmpKey,1);
          }
          else
          {
              firstCountMap.put(tmpKey,count + 1);
          }

          tmpKey = secondVal;
          count = secondCountMap.remove(tmpKey);
          if (count == null)
          {
              secondCountMap.put(tmpKey,1);
          }
          else
          {
              secondCountMap.put(tmpKey,count + 1);
          }
      }

      for (Entry<Pair<Integer,Integer>,Integer> e : jointCountMap.entrySet())
      {
          jointProbMap.put(e.getKey(),e.getValue() / doubleLength);
      }

      for (Entry<Integer,Integer> e : firstCountMap.entrySet())
      {
          firstProbMap.put(e.getKey(),e.getValue() / doubleLength);
      }

      for (Entry<Integer,Integer> e : secondCountMap.entrySet())
      {
          secondProbMap.put(e.getKey(),e.getValue() / doubleLength);
      }
    }//constructor(double[],double[])
  //class JointProbabilityState
}
