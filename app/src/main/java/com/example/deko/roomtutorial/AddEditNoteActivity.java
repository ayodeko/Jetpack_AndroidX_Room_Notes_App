package com.example.deko.roomtutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.deko.roomtutorial.Database.Note;
import com.example.deko.roomtutorial.Database.NoteRepository;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextDescription;
    NumberPicker numberPickerPriority;

    public static final String EXTRA_ID = "com.example.deko.roomtutorial.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.deko.roomtutorial.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.deko.roomtutorial.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY= "com.example.deko.roomtutorial.EXTRA_PRIORITY";
    public static final String EXTRA_MOTIVE= "com.example.deko.roomtutorial.EXTRA_MOTIVE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMaxValue(10);
        numberPickerPriority.setMinValue(1);

        Intent intent= getIntent();
        String titleText = intent.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
        String descriptionText = intent.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
        int priorityNumber = intent.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

        editTextTitle.setText(titleText);
        editTextDescription.setText(descriptionText);
        numberPickerPriority.setValue(priorityNumber);

    }

    public void saveNote(EditText editTitle, EditText editDescription, NumberPicker numPriority){

        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        int priority = numPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please fill up the empty spaces", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_PRIORITY, priority);
            data.putExtra(EXTRA_MOTIVE, "EDIT");

            setResult(RESULT_OK, data);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_save_note){
            saveNote(editTextTitle, editTextDescription, numberPickerPriority);
        }
        return super.onOptionsItemSelected(item);
    }
}
