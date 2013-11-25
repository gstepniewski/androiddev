package com.example.test;

public class Student {

	private int mId;
	private String mName;
	private String mFaculty;

	public Student() {
	}
	
	public Student(String name, String faculty) {
		super();
		mName = name;
		mFaculty = faculty;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getFaculty() {
		return mFaculty;
	}

	public void setFaculty(String faculty) {
		mFaculty = faculty;
	}
}
