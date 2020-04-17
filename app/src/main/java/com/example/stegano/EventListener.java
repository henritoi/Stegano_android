package com.example.stegano;

public interface EventListener {
    public void nextPage();
    public void previousPage();

    public void imageSelected(boolean isSelected);
    public void messageSet(boolean isSet);
}
