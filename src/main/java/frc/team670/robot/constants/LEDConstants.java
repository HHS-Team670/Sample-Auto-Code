package frc.team670.robot.constants;

import frc.team670.libs.constantBases.LEDConstantsBase;

public class LEDConstants extends LEDConstantsBase {

    @Override
    public int ledStartIndex() {
        return 0;
    }

    @Override
    public int ledEndIndex() {
        return 31;
    }

    @Override
    public int port() {
        return 0;
    }
    
}
