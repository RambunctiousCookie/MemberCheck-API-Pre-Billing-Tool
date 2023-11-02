package RTT.billing.Config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SystemParameters {
    //Example Configuration
    private UseCase0 useCase0;
    private UseCase1 useCase1;
    private DateConfig dateConfig;
}
