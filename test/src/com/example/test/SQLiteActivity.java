package com.example.test;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SQLiteActivity extends Activity implements OnClickListener {

	private TestSQLiteHelper mSql;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite);
		
		Button add = (Button) findViewById(R.id.add_student);
		add.setOnClickListener(this);
		
		mSql = new TestSQLiteHelper(this);
		refresh();
	}
	
	private void refresh() {
		LinearLayout studentsList = (LinearLayout) findViewById(R.id.students);
		
		studentsList.removeAllViews();
		List<Student> students = mSql.getAllStudents();
		
		for (Student student : students) {
			TextView tv = new TextView(this);
			tv.setText(student.getId() + " : " + student.getName() + " , " + student.getFaculty());
			tv.setOnClickListener(new StudentClickListener(student.getId()));
			studentsList.addView(tv);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.input_dialog);
		
		final EditText input = (EditText) dialog.findViewById(R.id.input_text);
		
		Button inputOk = (Button) dialog.findViewById(R.id.input_ok);
		inputOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = input.getText().toString();
				mSql.addStudent(name, name);
				refresh();
				
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	private class StudentClickListener implements OnClickListener {
		private final int mId;
		
		public StudentClickListener(int id) {
			mId = id;
		}
		
		@Override
		public void onClick(View v) {
			mSql.deleteStudent(mId);
			refresh();
		}
	}

}
