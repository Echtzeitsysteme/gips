package org.emoflon.gips.core.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * File utilities for loading and saving files.
 *
 * @author Maximilian Kratz (maximilian.kratz@es.tu-darmstadt.de)
 */
public class FileUtils {

	/**
	 * Private constructor to forbid instantiation of objects.
	 */
	private FileUtils() {
	}

	/**
	 * Reads a file from a given path to a JSON object.
	 *
	 * @param path Path for the file to read.
	 * @return JSON object read from file.
	 */
	public static JsonObject readFileToJson(final String path) {
		if (path == null || path.isBlank()) {
			throw new IllegalArgumentException("Given path was null or blank.");
		}
		return new Gson().fromJson(FileUtils.readFile(path), JsonObject.class);
	}

	/**
	 * Reads a file from a given path and returns its content as string.
	 *
	 * @param path Path to read file from.
	 * @return File content as string.
	 */
	public static String readFile(final String path) {
		if (path == null || path.isBlank()) {
			throw new IllegalArgumentException("Given path was null or blank.");
		}

		// Check if file exists
		final File f = new File(path);
		if (!f.exists() || f.isDirectory()) {
			throw new UnsupportedOperationException("File <" + path + "> does not exist.");
		}

		String read = "";
		try {
			read = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new IllegalArgumentException();
		}
		return read;
	}

	/**
	 * Checks if a file exists at the given path.
	 * 
	 * @param path Path to check for file existence.
	 * @return True if file exists, false otherwise.
	 */
	public static boolean checkIfFileExists(final String path) {
		Objects.requireNonNull(path);
		final File f = new File(path);
		return f.exists() && !f.isDirectory();
	}

}