package edu.rosehulman.krystal.rhitclub.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KrystalYang on 1/20/17.
 */

public class Event {
    private String name;
    private Club holder;

    public Event(String name, Club club) {
        this.name = name;
        this.holder = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getHolder() {
        return holder;
    }

    public void setHolder(Club holder) {
        this.holder = holder;
    }

    public static List<Event> initializeEvents() {
        // https://commons.wikimedia.org/wiki/Leonardo_da_Vinci#/media/File:Leonardo_da_Vinci_-_Mona_Lisa.jpg
        // By Leonardo da Vinci - (Upload Sept. 19, 2010) Au plus près des oeuvres ! - http://musee.louvre.fr/zoom/index.html (Microsoft Silverlight required), Public Domain, https://commons.wikimedia.org/w/index.php?curid=51499

        // https://upload.wikimedia.org/wikipedia/commons/d/d8/Hands_of_God_and_Adam.jpg

        // By Vincent van Gogh - bgEuwDxel93-Pg at Google Cultural Institute, zoom level maximum, Public Domain, https://commons.wikimedia.org/w/index.php?curid=25498286

        // https://upload.wikimedia.org/wikipedia/commons/1/14/Claude_Monet_-_Water_Lilies_-_Google_Art_Project.jpg

        //https://commons.wikimedia.org/wiki/File%3ARembrandt_Harmensz_van_Rijn_-_Return_of_the_Prodigal_Son_-_Google_Art_Project.jpg
        //Rembrandt [Public domain], via Wikimedia Commons
        // Alternate: http://www.everypainterpaintshimself.com/article/rembrandts_raising_of_the_cross

        // https://upload.wikimedia.org/wikipedia/commons/d/d7/Meisje_met_de_parel.jpg

        // https://upload.wikimedia.org/wikipedia/en/d/d1/Picasso_three_musicians_moma_2006.jpg

        List<Event> events = new ArrayList<>();
        events.add(new Event("Chinese New Year",new Club("ISA")));
        events.add(new Event("Picnic",new Club("SASE")));
        return events;
    }
}
