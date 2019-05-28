package com.tysiac.game.defs;

import com.tysiac.game.GameSingleForm;

public class GameSingleFormDescription {
    GameSingleForm form;
    Integer playerNr;
    Boolean isMaster;

    public GameSingleForm getForm() {
        return form;
    }

    public void setForm(GameSingleForm form) {
        this.form = form;
    }

    public Boolean getMaster() {
        return isMaster;
    }

    public void setMaster(Boolean master) {
        isMaster = master;
    }

    public Integer getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(Integer playerNr) {
        this.playerNr = playerNr;
    }
}
