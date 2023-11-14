package com.melwaresystems.checklists_backend.models.enums;

public enum Status {

    ACTIVE(1),
    ARCHIVED(2),
    TRASH(3),
    DELETED(4);

    private int code;

    private Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // função for-loop para comparar o valor recebido com os tipos enumerados
    // disponíveis
    public static Status valueOf(int code) {
        for (Status value : Status.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid ConsultationStatus code");
    }

}
