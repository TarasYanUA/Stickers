import org.testng.asserts.SoftAssert;


public class CollectAssertMessages {

    private static final ThreadLocal<SoftAssert> threadLocal = new ThreadLocal<>();

    public static SoftAssert getSoftAssertions() {
        return threadLocal.get();
    }

    public static void setSoftAssertions(SoftAssert softAssertions) {
        threadLocal.set(softAssertions);
    }
}