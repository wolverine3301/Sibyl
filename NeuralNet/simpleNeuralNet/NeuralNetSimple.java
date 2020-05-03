package simpleNeuralNet;

import static simpleNeuralNet.NNMath.*;
import static simpleNeuralNet.MatrixUtil.apply;

public class NeuralNetSimple {

    private final NeuronLayer layer1;
    private double[][] outputLayer1;

    public NeuralNetSimple(NeuronLayer layer1) {
        this.layer1 = layer1;
    }

    public void think(double[][] inputs) {
        outputLayer1 = apply(matrixMultiply(inputs, layer1.weights), layer1.activationFunction);
    }

    public void train(double[][] inputs, double[][] outputs, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; ++i) {

            // pass the training set through the network
            think(inputs);

            // adjust weights by error * input * output * (1 - output)

            double[][] errorLayer1 = matrixSubtract(outputs, outputLayer1);
            double[][] deltaLayer1 = scalarMultiply(errorLayer1, apply(outputLayer1, layer1.activationFunctionDerivative));

            // Calculate how much to adjust the weights by
            // Since we’re dealing with matrices, we handle the division by multiplying the delta output sum with the inputs' transpose!

            double[][] adjustmentLayer1 = matrixMultiply(matrixTranspose(inputs), deltaLayer1);

            // adjust the weights
            this.layer1.adjustWeights(adjustmentLayer1);
        }
    }

    public double[][] getOutput() {
        return outputLayer1;
    }
}

