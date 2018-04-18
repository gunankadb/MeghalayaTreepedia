package com.treepedia.meghalaya.meghalayatreepedia;

import java.sql.Array;

public final class Constant {

    private Constant() {
        // restrict instantiation
    }
    public static final int TREE_COUNT = 50;
    public static final int TREE_FIELDS_COUNT = 14;
    public static final String TEXT_FONT_COLOR = "#006400" ;
    public static final String TREE_FILE_NAME = "MeghalayaTreepedia.csv";
    public static final String TREE_JSON_FILE_NAME = "https://firebasestorage.googleapis.com/v0/b/meghalayatreepedia.appspot.com/o/TreeImages.json?alt=media&token=";
    public static final String[] TREE_FIELDS = {"ID No.", "Botanical Name", "Synonym", "Common Names", "General Information",	"Known Hazards",	"Range",	"Habitat",	"Properties",	"Cultivation Details",	"Edible Uses",	"Medicinal	Uses", "Other Uses",	"Propagation"} ;
    public static final String[] TREE_IMAGES_URL = {"https://firebasestorage.googleapis.com/v0/b/meghalayatreepedia.appspot.com/o/Tree%20Images%2F","%2F", ".jpg?alt=media&token="};
    public static final String TIMESTAMP = "April 14, 2018";
    public static final Integer[] IMAGES_COUNT = {8, 4, 4, 6, 6, 4, 6, 6, 8, 6, 6, 8, 8, 6, 8, 6, 6, 6, 8, 8, 14, 10, 6, 2, 6, 6, 2, 6, 8, 6, 8, 10 , 10, 8, 4, 6, 1, 10, 6, 8, 6, 6, 4, 4, 4, 8, 8, 4, 4, 8};
}
