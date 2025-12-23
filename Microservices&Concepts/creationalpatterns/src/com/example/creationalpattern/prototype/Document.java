package com.example.creationalpattern.prototype;



/**
 * A simple Prototype example where Documents can be cloned.
 */
public class Document implements Cloneable {
    private String content;

    public Document(String content) {
        this.content = content;
    }

    // The "clone" method returns a copy of this object
    @Override
    public Document clone() {
        try {
            // default Object.clone() does a field-by-field copy
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            // Shouldn't happen, since we implement Cloneable
            throw new AssertionError("Cloning not supported", e);
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
 