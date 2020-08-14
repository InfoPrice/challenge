package com.fabricio.challenge.control.eventbus;

/**
 * Message codes used in this application to update the activities or services.
 * Each message code may have any number of arguments.
 * @author Fabricio Godoi
 */

public enum MessageCode {

    /**
     * Product selected in the list view
     * arg0 = model.Product
     */
    PRODUCT_SELECT_EVENT,

}
