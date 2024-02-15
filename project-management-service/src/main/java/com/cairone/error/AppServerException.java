package com.cairone.error;

public class AppServerException extends AppBaseException {

    private final String technicalMessage;

    private AppServerException(Throwable cause, String message, String technicalMessage) {
        super(cause, message);
        this.technicalMessage = technicalMessage;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

    public static class Builder {

        private String message;
        private String technicalMessage;
        private Throwable cause;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withTechnicalMessage(String technicalMessage) {
            this.technicalMessage = technicalMessage;
            return this;
        }

        public Builder withCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public AppServerException build() {
            return new AppServerException(cause, message, technicalMessage);
        }
    }
}
