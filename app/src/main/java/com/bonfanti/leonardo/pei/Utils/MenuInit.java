package com.bonfanti.leonardo.pei.Utils;

/**
 * Created by Usuário on 6/2/2017.
 */

public class MenuInit
{
    private String description;
    private int thumbnail;

    public MenuInit()
    {

    }

    public MenuInit(String description, int thumbnail)
    {
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
