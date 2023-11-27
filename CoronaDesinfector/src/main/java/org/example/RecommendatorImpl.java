package org.example;

public class RecommendatorImpl implements Recommendator {
    @InjectProperty
    private String alcohol;

    @Override
    public void recommend() {
        System.out.println("to protect from covid 109 drink " + alcohol);
    }
}
