package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestSQLHelper extends SQLiteOpenHelper{
	
	private static final String[] COLUMNS = { "id", "name", "faculty" };

	public TestSQLHelper(Context context) {
		super(context, "StudentsDB", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_STUDENTS_TABLE = //
				"CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, faculty TEXT)";
		
		db.execSQL(CREATE_STUDENTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS students");
		
		onCreate(db);		
	}
	
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		
		SQLiteDatabase db = getWritableDatabase();
		
		String query = "SELECT * FROM students";
		
		Cursor cursor = db.rawQuery(query, null);
		
		Student student = null;
		if (cursor.moveToFirst()) {
			do {
				student = new Student();
				
				student.setId(Integer.parseInt(cursor.getString(0)));
				student.setName(cursor.getString(1));
				student.setFaculty(cursor.getString(2));
				
				students.add(student);
				
			} while (cursor.moveToNext());
		}
		
		return students;
	}
	
	public Student getStudent(int id) {
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor cursor = db.query("students", COLUMNS, "id = ?", new String[] { String.valueOf(id) }, 
				null, null, null, null);
		
		if (cursor == null) return null;

		Student student = new Student();
		student.setId(Integer.parseInt(cursor.getString(0)));
		student.setName(cursor.getString(1));
		student.setFaculty(cursor.getString(2));
		
		return student;
	}
	
	public void addStudent(String name, String faculty) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("faculty", faculty);
		
		db.insert("students", null, cv);
		
		db.close();
	}
	
	public void deleteStudent(int id) {
		SQLiteDatabase db = getWritableDatabase();
		
		db.delete("students", "id = ?", new String[] { String.valueOf(id) });
		
		db.close();
	}

}
