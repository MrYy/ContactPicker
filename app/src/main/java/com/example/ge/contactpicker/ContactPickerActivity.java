package com.example.ge.contactpicker;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ContactPickerActivity extends Activity {
private static final String TAG="ContactPickerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int num=cursor.getColumnCount();
        Log.d(TAG,Integer.toString(num));
        String[] from = new String[]{ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
        int[] to = new int[]{R.id.item_textView};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item_layout, cursor, from, to
        );
        ListView listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                int rowId=cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Uri outURI= ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, rowId);
                Intent outData=new Intent();
                outData.setData(outURI);
                setResult(Activity.RESULT_OK,outData);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
