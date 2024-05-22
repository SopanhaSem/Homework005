package exception;

public class RequirementValidatorException extends Exception {
    public RequirementValidatorException() {
        super();
    }

    public RequirementValidatorException(String message) {
        super(message);
    }
}
