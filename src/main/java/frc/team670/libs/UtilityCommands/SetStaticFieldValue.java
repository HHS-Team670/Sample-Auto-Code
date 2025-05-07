package frc.team670.libs.UtilityCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team670.libs.Utilities.TypeUtils;

public class SetStaticFieldValue extends InstantCommand {

    Class<?> classStorage;
    String fieldName;
    Object value;

    public SetStaticFieldValue(Class<?> classStorage, String fieldName, Object value) {
        this.classStorage = classStorage;
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public void initialize() {
        TypeUtils.setStaticField(classStorage, fieldName, value);
    }
}
