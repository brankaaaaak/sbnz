package com.ftn.sbnz.model.stats;

/**
 * Singleton fact koji drži agregirane podatke o stanju sistema hitne pomoći.
 * Ubacuje se jednom u working memory i ažurira accumulate pravilima.
 */
public class SystemStats {

    /** Ukupan broj aktivnih poziva (status == ACTIVE) */
    private long activeCallCount;

    /** Ukupan broj neobrađenih poziva (status == PENDING ili assessment nije finaliziran) */
    private long unprocessedCallCount;

    /** Broj finaliziranih poziva sa finalLevel == RED (prvi stepen prioriteta) */
    private long redLevelCount;

    public SystemStats() {
        this.activeCallCount = 0;
        this.unprocessedCallCount = 0;
        this.redLevelCount = 0;
    }

    public long getActiveCallCount() {
        return activeCallCount;
    }

    public void setActiveCallCount(long activeCallCount) {
        this.activeCallCount = activeCallCount;
    }

    public long getUnprocessedCallCount() {
        return unprocessedCallCount;
    }

    public void setUnprocessedCallCount(long unprocessedCallCount) {
        this.unprocessedCallCount = unprocessedCallCount;
    }

    public long getRedLevelCount() {
        return redLevelCount;
    }

    public void setRedLevelCount(long redLevelCount) {
        this.redLevelCount = redLevelCount;
    }

    @Override
    public String toString() {
        return "SystemStats{" +
                "activeCallCount=" + activeCallCount +
                ", unprocessedCallCount=" + unprocessedCallCount +
                ", redLevelCount=" + redLevelCount +
                '}';
    }
}
