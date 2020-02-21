package com.web.hackaton.enumerated;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    OK("ok"), I_WANT_TO_TRY_MYSELF("I want to try myself"),
    I_WANT_TO_CHANGE_MY_POSITION("I want to change my position");

    public String caption;

    public String getCaption() {
        return caption;
    }
}
