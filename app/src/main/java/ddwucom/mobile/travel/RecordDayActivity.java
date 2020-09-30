package ddwucom.mobile.travel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RecordDayActivity extends AppCompatActivity {
    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;
    List<String> items;

    EditText etRecordFolder;
    EditText etRecordDate;
    Calendar calendar;
    String dateFormat;
    SimpleDateFormat sdf;
    int todayY;
    int todayM;
    int todayD;

    DatePickerDialog.OnDateSetListener recordDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            etRecordDate.setText(sdf.format(calendar.getTime()));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_day);

        etRecordDate = findViewById(R.id.etRecordDate);


        items = new ArrayList<String>();
        items.add("폴더 선택하기");
        items.add("폴더 추가하기");

        Context context = getBaseContext();
        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.record_spinner_item, items);
        spinner = findViewById(R.id.spRecordFolder);
        spinner.setAdapter(spinnerAdapter);

        final LinearLayout addFolderLayout = (LinearLayout) View.inflate(this, R.layout.add_folder_layout, null);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                    AlertDialog alertDialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecordDayActivity.this, R.style.DialogTheme);

                    builder.setTitle("폴더 추가하기")
                            .setView(addFolderLayout)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etRecordFolder = addFolderLayout.findViewById(R.id.etRecordFolder);
                                    String addFolderName = etRecordFolder.getText().toString();

                                    if (items.contains(addFolderName)) {
                                        Toast.makeText(RecordDayActivity.this, "이미 존재하는 폴더입니다!", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        items.add(addFolderName);
                                        spinnerAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                            .setNegativeButton("CANCEL", null);
                    alertDialog = builder.create();
                    if (addFolderLayout.getParent() != null) {
                        ((ViewGroup)addFolderLayout.getParent()).removeView(addFolderLayout);
                    }
                    alertDialog.show();
                    alertDialog.getWindow().setLayout(1200, 750);

                    TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
                    Typeface face = Typeface.createFromAsset(getAssets(),"fonts/tmoney_regular.ttf");
                    textView.setTypeface(face);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        calendar = Calendar.getInstance();
        dateFormat = "yyyy-MM-dd";
        sdf = new SimpleDateFormat(dateFormat, Locale.KOREA);

        etRecordDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RecordDayActivity.this, R.style.DialogTheme, recordDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}