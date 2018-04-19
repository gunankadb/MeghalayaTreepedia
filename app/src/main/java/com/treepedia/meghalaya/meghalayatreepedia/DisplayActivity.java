package com.treepedia.meghalaya.meghalayatreepedia;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_FIELDS_COUNT;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TEXT_FONT_COLOR;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_FIELDS;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_IMAGES_URL;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TIMESTAMP;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_JSON_FILE_NAME;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.IMAGES_COUNT;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.treepedia.meghalaya.meghalayatreepedia.R;
import com.treepedia.meghalaya.meghalayatreepedia.GalleryAdapter;
import com.treepedia.meghalaya.meghalayatreepedia.AppController;
import com.treepedia.meghalaya.meghalayatreepedia.Image;

public class DisplayActivity extends AppCompatActivity {
    private ImageView mImageView;
    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);
        String firebase_access_token = FirebaseInstanceId.getInstance().getToken();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        final ArrayList<String> selected_tree = getIntent().getStringArrayListExtra("selected_tree");

        try{
            final ListView lv_display = (ListView) findViewById(R.id.lv_display);
//            mImageView = (ImageView) findViewById(R.id.imageViewId);
//            final String imageID = "i"+ (String)selected_tree.toArray()[0] ;
//            mImageView.setImageResource(getResources().getIdentifier(imageID, "drawable", getPackageName()));
            fetchImages((String)selected_tree.toArray()[0], (String)selected_tree.toArray()[1], (String)selected_tree.toArray()[2], (String)selected_tree.toArray()[3], firebase_access_token );
            final String[] headings = TREE_FIELDS ;
            //headings[TREE_FIELDS_COUNT];
            try{
                final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, selected_tree)
                {
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                        // Get the current item from ListView
                        View view = super.getView(position, convertView, parent);
                        // Initialize a TextView for ListView each Item
                        TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                        TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
                    if (position < TREE_FIELDS_COUNT ) {
                        tv1.setText(headings[position]);
                        tv2.setText((String) selected_tree.toArray()[position]);

                    }
                    else
                    {
                        tv1.setText("");
                        tv2.setText("");
                    }
                    // Generate ListView Item using TextView
                    tv1.setTextColor(Color.parseColor(TEXT_FONT_COLOR));
                    tv2.setTextColor(Color.BLACK);
                    return view;
                };
                };
                // DataBind ListView with items from ArrayAdapter
                lv_display.setAdapter(arrayAdapter);
            }catch(Exception e){
                System.out.println(e);
                // Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            System.out.println(e);
            // Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void fetchImages(final String ImageID, final String BN,final String Synonym, final String CN, final String firebase_access_token ) {
        final String endpoint = TREE_JSON_FILE_NAME + firebase_access_token;

        pDialog.setMessage("Downloading images...");
        pDialog.show();



        String json = null;
        try {

            InputStream is = this.getAssets().open("TreeImages.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Log.i("tag1",json);

        JSONArray jsonArr = new JSONArray(json);

        for (int i = 0; i <  jsonArr.length(); i++) {
            final String Imageurl = TREE_IMAGES_URL[0] + ImageID + TREE_IMAGES_URL[1] + "i" + (i + 1) + TREE_IMAGES_URL[2] + firebase_access_token;
            JSONObject object = jsonArr.getJSONObject(i);

            Image image = new Image();
            if (Synonym.equals("NA")) {
                image.setName(BN);
            } else {
                image.setName(BN + " or " + Synonym);
            }

            JSONObject url = object.getJSONObject("url");
            image.setSmall(Imageurl);
            image.setMedium(Imageurl);
            image.setLarge(Imageurl);
            image.setTimestamp(CN);

            images.add(image);

        }
                 } catch (Exception e) {
                e.printStackTrace();
            }

        mAdapter.notifyDataSetChanged();



        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < IMAGES_COUNT[Integer.parseInt(ImageID)-1]; i++) {
                            final String Imageurl = TREE_IMAGES_URL[0]+ImageID+TREE_IMAGES_URL[1]+"i"+(i+1)+TREE_IMAGES_URL[2] + firebase_access_token ;
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                if (Synonym.equals("NA")) {
                                    image.setName(BN);
                                }else{
                                    image.setName(BN + " or " + Synonym);
                                }

                                JSONObject url = object.getJSONObject("url");
                                image.setSmall(Imageurl);
                                image.setMedium(Imageurl);
                                image.setLarge(Imageurl);
                                image.setTimestamp(CN);

                                images.add(image);

                            } catch (Exception e) {
                                Log.e(TAG, "Tree Image error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                pDialog.hide();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }
}


