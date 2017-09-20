package com.application.innove.obex.Other;

import com.application.innove.obex.ObexExpandableModel.Artist;
import com.application.innove.obex.ObexExpandableModel.Genre;

import java.util.Arrays;
import java.util.List;

public class GenreDataFactory {

    public static List<Genre> makeGenres() {
        return Arrays.asList(makeRockGenre(),
                makeJazzGenre(),
                makeClassicGenre(),
                makeSalsaGenre());
    }


    public static Genre makeRockGenre() {
        return new Genre("AGE PROOF", makeRockArtists());
    }



    public static List<Artist> makeRockArtists() {
        Artist queen = new Artist("Pan Card", true);
        Artist styx = new Artist("Adhaar Card", false);
        Artist reoSpeedwagon = new Artist("Driving License", false);


        return Arrays.asList(queen, styx, reoSpeedwagon);
    }

    public static Genre makeJazzGenre() {
        return new Genre("ID PROOF", makeJazzArtists());
    }



    public static List<Artist> makeJazzArtists() {
        Artist milesDavis = new Artist("PAN CARD", true);
        Artist ellaFitzgerald = new Artist("Adhaar Card", true);
        Artist billieHoliday = new Artist("Driving License", false);

        return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
    }



    public static Genre makeClassicGenre() {
        return new Genre("ADDRESS PROOF", makeClassicArtists());
    }

    public static List<Artist> makeClassicArtists() {
        Artist beethoven = new Artist("Adhaar Card", false);
        Artist bach = new Artist("House Paper", true);
        Artist brahms = new Artist("Light Bill", false);


        return Arrays.asList(beethoven, bach, brahms);
    }



    public static Genre makeSalsaGenre() {
        return new Genre("ADDITIONAL INFORMATION", makeSalsaArtists());
    }


    public static List<Artist> makeSalsaArtists() {
        Artist hectorLavoe = new Artist("Cheque Book", true);
        Artist celiaCruz = new Artist("Adhaar Card", false);
        Artist willieColon = new Artist("Pan Card", false);


        return Arrays.asList(hectorLavoe, celiaCruz, willieColon);
    }

}

