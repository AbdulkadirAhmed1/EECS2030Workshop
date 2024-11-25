package model;

public abstract class BinarySeqOperation implements SeqOperation {
    protected int[] seq1; // First sequence
    protected int[] seq2; // Second sequence

    public BinarySeqOperation(int[] seq1, int[] seq2) {
        this.seq1 = seq1;
        this.seq2 = seq2;
    }
}
