package com.troytd.helpers;

/**
 * A Loadable is an object that has assets that have to be loaded.
 * It is called in the LoadScreen Screen after everything is loaded.
 */
public interface Loadable {
    void afterLoad();
}
