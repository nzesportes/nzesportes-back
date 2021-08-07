package br.com.nzesportes.api.nzapi.dtos;

import lombok.Data;

@Data
public class BetterSendTokenStatusTO {
    private Status status;
    public enum Status {
        VALID("VALID"),
        EXPIRED("EXPIRED"),
        INVALID("INVALID"),
        UNCREATED("UNCREATED");


        private final String text;

        Status(final String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

    }

}


