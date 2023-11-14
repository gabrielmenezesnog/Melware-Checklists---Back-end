package com.melwaresystems.checklists_backend.models.enums;

public enum UserStatus {

    ACTIVE(1),
    DELETED(3);

    private int code;

    private UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // função for-loop para comparar o valor recebido com os tipos enumerados
    // disponíveis
    public static UserStatus valueOf(int code) {
        for (UserStatus value : UserStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid ConsultationStatus code");
    }

}
