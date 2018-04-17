package com.treepedia.meghalaya.meghalayatreepedia;

//import android.Manifest;
//import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
//import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
//import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FileDownloadTask;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;


//import java.io.File;
//import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.lang.reflect.Array;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_COUNT;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_FIELDS_COUNT;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TEXT_FONT_COLOR;
import static com.treepedia.meghalaya.meghalayatreepedia.Constant.TREE_FILE_NAME;
import au.com.bytecode.opencsv.CSVReader;


public class MainActivity extends AppCompatActivity

    implements NavigationView.OnNavigationItemSelectedListener {

    //private StorageReference mStorageRef;
    // Search EditText
    EditText inputSearch;
    // Listview Adapter
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AssetManager assetManager = getAssets();
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 44);

        //mStorageRef = FirebaseStorage.getInstance().getReference().child(TREE_FILE_NAME);

        //final File localFile = new File(Environment.getExternalStorageDirectory(), TREE_FILE_NAME);
        //StorageTask<FileDownloadTask.TaskSnapshot> taskSnapshotStorageTask = mStorageRef.getFile(localFile)
         //       .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    //@Override
                    //public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
        try{
            final ListView lv = (ListView) findViewById(R.id.lv);
            final String[] tree_names = new String[TREE_COUNT] ;
            final String[] tree_names_for_search = new String[TREE_COUNT] ;
            final HashMap<String, String[]> trees_hash = new HashMap<String, String[]>() ;
            int trees_index = 0;
            //CSVReader reader = new CSVReader(new FileReader(localFile.getAbsolutePath()));
            InputStream csvStream = assetManager.open(TREE_FILE_NAME);
            InputStreamReader csvStreamReader = new        InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String [] nextLine;
            final String [][] trees_temp = new String[TREE_COUNT][TREE_FIELDS_COUNT ];
            while ((nextLine = csvReader.readNext()) != null) {
                //nextLine[] is an array of values from the line
                if (trees_index > 0) {
                    tree_names[trees_index - 1] = nextLine[1];
                    tree_names_for_search[trees_index - 1] = nextLine[1] + ", " + nextLine[2] + ", " + nextLine[3];
                    //trees_temp[trees_index - 1] = nextLine;
                    System.arraycopy(nextLine, 0 , trees_temp[trees_index - 1], 0,TREE_FIELDS_COUNT );
                    trees_hash.put(tree_names[trees_index - 1], trees_temp[trees_index - 1]);
                    trees_index++;
                }
                else
                    trees_index++;
            }
            // Create a List from String Array elements

            //final List<String> trees_list = new ArrayList<String>(Arrays.asList(tree_names));
            final String [][] trees = new String[trees_index - 1][TREE_FIELDS_COUNT ];
            System.arraycopy(trees_temp, 0 , trees, 0,trees_index - 1);
            // Create an ArrayAdapter from List

            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, tree_names_for_search) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView tv2 = (TextView) view.findViewById(android.R.id.text2);

                    String mytree = (String ) getItem(position);
                    String[] mytree_array = new String[2] ;
                    mytree_array = mytree.split(",") ;
                    if (trees_hash.get(mytree_array[0])[2].equals("NA")) {
                        tv1.setText(trees_hash.get(mytree_array[0])[1]);
                    }else {
                        tv1.setText(trees_hash.get(mytree_array[0])[1] + " or " + trees_hash.get(mytree_array[0])[2]);
                    }
                    tv2.setText(trees_hash.get(mytree_array[0])[3]);
                    tv1.setTextColor(Color.parseColor(TEXT_FONT_COLOR));
                    tv2.setTextColor(Color.BLACK);

                    return view;
                }
            };



            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    //List<String> selected_tree = new ArrayList<String>(Arrays.asList(trees[position]));
                    String mytree = (String ) MainActivity.this.arrayAdapter.getItem(position);
                    String[] mytree_array = new String[2] ;
                    mytree_array = mytree.split(",") ;
                    ArrayList<String> selected_tree = new ArrayList<>(Arrays.asList(trees_hash.get(mytree_array[0])));
                    //List<String> selected_tree = new ArrayList<String>();
                    // Display the selected item text on TextView
                    Intent intent = new Intent(MainActivity.this , DisplayActivity.class);
                    intent.putStringArrayListExtra("selected_tree", (ArrayList<String>) selected_tree);
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            System.out.println(e);
            // Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }
                    //}
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivity.this.arrayAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
