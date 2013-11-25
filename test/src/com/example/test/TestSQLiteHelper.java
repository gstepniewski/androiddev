package com.example.test;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestSQLiteHelper extends SQLiteOpenHelper {

	private static final int DB_VESRION = 1;
	private static final String DB_NAME = "StudensDB";
	
	private static final String TABLE_STUDENTS = "students"; 

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_FACULTY = "faculty";
	
	private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_FACULTY };
	
	public TestSQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VESRION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_STUDENTS_TABLE = 
		"CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, faculty TEXT)";
		
		db.execSQL(CREATE_STUDENTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS students");
		this.onCreate(db);
	}
	
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		
		String query = "SELECT * FROM " + TABLE_STUDENTS;
		
		SQLiteDatabase db = getWritableDatabase();
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
		
		Cursor cursor = db.query(TABLE_STUDENTS, COLUMNS, "id = ?", new String[] { String.valueOf(id) }, 
				null, null, null, null);
		
		if (cursor == null)
			return null;
		
		cursor.moveToFirst();
		Student student = new Student();
		student.setId(Integer.parseInt(cursor.getString(0)));
		student.setName(cursor.getString(1));
		student.setFaculty(cursor.getString(2));
		
		return student;
	}
	
	public void addStudent(String name, String faculty) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_FACULTY, faculty);
		
		db.insert(TABLE_STUDENTS, null, cv);
		
		db.close();
	}

	public void deleteStudent(int id) {
		SQLiteDatabase db = getWritableDatabase();
		
		db.delete(TABLE_STUDENTS, "id = ?", new String[] { String.valueOf(id) });
		
		db.close();
	}

}
