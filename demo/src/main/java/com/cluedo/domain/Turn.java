package com.cluedo.domain;

import com.cluedo.domain.dto.ActionResult;
import com.cluedo.domain.dto.DoActionResult;
import com.cluedo.domain.dto.RollResult;

public class Turn {
    private RollResult rollResult;
    private ActionResult actionResult;
    private DoActionResult doActionResult;

    public Turn() {
        this.rollResult = null;
        this.actionResult = null;
        this.doActionResult = null;
    }

    public void reset() {
        this.rollResult = null;
        this.actionResult = null;
        this.doActionResult = null;
    }

    public RollResult getRollResult() {
        return rollResult;
    }

    public void setRollResult(RollResult rollResult) {
        this.rollResult = rollResult;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }

    public DoActionResult getDoActionResult() {
        return doActionResult;
    }

    public void setDoActionResult(DoActionResult doActionResult) {
        this.doActionResult = doActionResult;
    }

    
}
