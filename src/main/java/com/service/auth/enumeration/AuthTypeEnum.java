package com.service.auth.enumeration;

public enum AuthTypeEnum {

	LDAP(false),
	UAEPASS(false),
	PASS(true);

    private final boolean requirepassword;

    AuthTypeEnum(boolean requirepassword) {
        this.requirepassword = requirepassword;
    }

    public boolean isRequirepassword() {
        return requirepassword;
    }
}
