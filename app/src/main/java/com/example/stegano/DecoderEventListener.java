package com.example.stegano;

public interface DecoderEventListener {
    public void nextPage();
    public void previousPage();

    public void imageSelected(boolean isSelected);
}
