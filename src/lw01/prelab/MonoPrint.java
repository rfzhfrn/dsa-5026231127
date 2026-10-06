package lw01.prelab;

public class MonoPrint extends PrintJob {
    private static final int RATE_PER_PAGE = 500;

    public MonoPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        return getPages() * RATE_PER_PAGE;
    }

    @Override
    public String label() {
        return "Mono";
    }
}