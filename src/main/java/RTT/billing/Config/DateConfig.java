package RTT.billing.Config;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DateConfig {
    private int q1StartDay;
    private int q1StartMonth;
    private int q1EndDay;
    private int q1EndMonth;

    private int q2StartDay;
    private int q2StartMonth;
    private int q2EndDay;
    private int q2EndMonth;

    private int q3StartDay;
    private int q3StartMonth;
    private int q3EndDay;
    private int q3EndMonth;

    private int q4StartDay;
    private int q4StartMonth;
    private int q4EndDay;
    private int q4EndMonth;
}
