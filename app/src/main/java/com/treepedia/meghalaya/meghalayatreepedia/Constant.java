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
    public static final String TREE_JSON_FILE_NAME = "https://firebasestorage.googleapis.com/v0/b/meghalayatreepedia.appspot.com/o/TreeImages.json?alt=media&token=68e42d60-75a2-4e5c-9b90-53003e37a93f";
    public static final String[] TREE_FIELDS = {"ID No.", "Botanical Name", "Synonym", "Common Names", "General Information",	"Known Hazards",	"Range",	"Habitat",	"Properties",	"Cultivation Details",	"Edible Uses",	"Medicinal	Uses", "Other Uses",	"Propagation"} ;
    public static final String[] TREE_IMAGES_URL = {"https://firebasestorage.googleapis.com/v0/b/meghalayatreepedia.appspot.com/o/Tree%20Images%2F","%2F", ".jpg?alt=media&token=5557a2e0-6afb-45dc-a938-7af2189b9d81"};
    public static final String TIMESTAMP = "April 14, 2018";
    public static final Integer[] IMAGES_COUNT = {6, 3, 4, 2, 6, 2, 5, 6, 7, 5, 4, 7, 3, 4, 6, 6, 6, 6, 7, 7,14, 9, 5, 3, 4, 6, 2, 6, 8, 4, 6, 9 , 10, 7, 4, 6, 1, 10, 6, 6,  4, 5, 4, 4, 4, 6, 7, 4, 4, 6};
}
