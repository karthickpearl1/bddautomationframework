package databojects;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.event.TreeWillExpandListener;

import wrappers.UIFactory;

public class UIDataFactory extends UIFactory {

	

	private static final ThreadLocal<String> quoteID = new ThreadLocal<String>();
}
